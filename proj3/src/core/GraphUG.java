package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.*;

public class GraphUG {
    private int[] rows = {-1, 0, 1, 0}; // Directions for moving in 4 directions.
    private int[] cols = {0, 1, 0, -1};

    private TETile[][] grid;
    private Map<Integer, List<Point>> rooms; // Room ID → List of points
    private Set<Integer> connectedRooms = new HashSet<>();
    public GraphUG(TETile[][] grid) {
        this.grid = grid;
        rooms = new HashMap<>();
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length;
    }

    private boolean isNothing(int x, int y) {
        return grid[x][y] == Tileset.NOTHING;
    }
    private boolean isFloor(int x, int y) {
        return grid[x][y] == Tileset.NOTHING;
    }

    private static class Node implements Comparable<Node> {
        Point point;
        int cost;

        Node(Point point, int cost) {
            this.point = point;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.cost, other.cost);
        }
    }
    private boolean isCloseToBoundaries(int x, int y) {
        return x == 0 || x == grid.length-1 || y == 0 || y == grid[0].length-1;
    }
    public void connectRooms() {
        // Pick a random room to start
        List<Integer> roomIds = new ArrayList<>(rooms.keySet());
        Collections.shuffle(roomIds);
        int startRoom = roomIds.getFirst();

        // Mark the starting room as connected
        connectedRooms.add(startRoom);

        PriorityQueue<Node> pq = new PriorityQueue<>();
        Map<Point, Integer> distMap = new HashMap<>();
        Map<Point, Point> parentMap = new HashMap<>();

        // Add all points in the start room to the queue
        for (Point start : rooms.get(startRoom)) {
            pq.add(new Node(start, 0));
            distMap.put(start, 0);
            parentMap.put(start, null);
        }

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            Point p = current.point;
            int cost = current.cost;

            if (cost != distMap.getOrDefault(p, Integer.MAX_VALUE)) continue;

            // Identify the room of this point
            int roomId = getRoomId(p);
            if (roomId != -1 && !connectedRooms.contains(roomId)) {
                // Found a new room → mark it as connected and finalize the path
                connectedRooms.add(roomId);
                reconstructPath(p, parentMap);
            }

            // If all rooms are connected, stop
            if (connectedRooms.size() == rooms.size()) break;

            // Explore 4 neighbors
            for (int i = 0; i < 4; i++) {
                int nx = p.getX() + rows[i];
                int ny = p.getY() + cols[i];

                if (isValid(nx, ny) && !isCloseToBoundaries(nx, ny)) {
                    int additionalCost = (isNothing(nx, ny) || isFloor(nx,ny)) ? 1 : 2;
                    int newCost = cost + additionalCost;
                    Point neighbor = new Point(nx, ny);

                    if (newCost < distMap.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                        distMap.put(neighbor, newCost);
                        parentMap.put(neighbor, p);
                        pq.add(new Node(neighbor, newCost));
                    }
                }
            }
        }

        // If there are still unconnected rooms, manually connect them
        forceConnectRemainingRooms();
    }

    private void reconstructPath(Point end, Map<Point, Point> parentMap) {
        Point current = end;
        while (current != null) {
            grid[current.getX()][current.getY()] = Tileset.FLOOR;
            current = parentMap.get(current);
        }
    }

    private void forceConnectRemainingRooms() {
        for (Integer roomId : rooms.keySet()) {
            if (!connectedRooms.contains(roomId)) {
                // Find a nearby connected point within the unconnected room.
                Point connectionPoint = findNearestConnectedPoint(rooms.get(roomId));
                if (connectionPoint != null) {
                    // Use createPassage to connect from a connected point to one cell in this room.
                    createPassage(connectionPoint, rooms.get(roomId).getFirst());
                    connectedRooms.add(roomId);
                }
            }
        }
    }
    // grid is a 2D array of TileType.
    public void addBordersToFloors() {
        int rows = grid.length;
        int cols = grid[0].length;

        // For safety, we can record the cells to change in a separate list to avoid modifying the grid while iterating.
        List<Point> toChange = new ArrayList<>();

        // Loop through every cell
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // If the cell is a FLOOR...
                if (grid[i][j] == Tileset.FLOOR) {
                    // Check all 8 adjacent neighbors.
                    for (int di = -1; di <= 1; di++) {
                        for (int dj = -1; dj <= 1; dj++) {
                            // Skip the center (current cell)
                            if (di == 0 && dj == 0) continue;
                            int ni = i + di;
                            int nj = j + dj;
                            // Check if neighbor is within bounds and is NOTHING.
                            if (ni >= 0 && ni < rows && nj >= 0 && nj < cols && grid[ni][nj] == Tileset.NOTHING) {
                                toChange.add(new Point(ni, nj));
                            }
                        }
                    }
                }
            }
        }

        // Change recorded NOTHING cells to WALL.
        for (Point p : toChange) {
            grid[p.getX()][p.getY()] = Tileset.WALL;
        }
    }



    private Point findNearestConnectedPoint(List<Point> roomPoints) {
        for (Point p : roomPoints) {
            for (int i = 0; i < 4; i++) {
                int nx = p.getX() + rows[i];
                int ny = p.getX() + cols[i];
                if (isValid(nx, ny) && grid[nx][ny] == Tileset.FLOOR) {
                    return new Point(nx, ny);
                }
            }
        }
        return null;
    }

    private void createPassage(Point from, Point to) {
        // Create a 1-tile wide Manhattan corridor from "from" to "to"
        int x = from.getX(), y = from.getY();
        int targetX = to.getX(), targetY = to.getY();

        // First, move horizontally until aligned with the target column.
        while (y != targetY) {
            grid[x][y] = Tileset.FLOOR;  // Mark only this cell.
            // Move one step toward targetY.
            y += Integer.compare(targetY, y);
        }
        // Then, move vertically until you reach the target row.
        while (x != targetX) {
            grid[x][y] = Tileset.FLOOR;
            x += Integer.compare(targetX, x);
        }
        // Mark the target cell.
        grid[x][y] = Tileset.FLOOR;
    }


    private int getRoomId(Point p) {
        for (Integer roomId : rooms.keySet()) {
            if (rooms.get(roomId).contains(p)) {
                return roomId;
            }
        }
        return -1;
    }
    public void getBorderPoints(int[][] grid)
    {
        for (int i = grid.length - 1; i >= 0; i--) {
            for (int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] == 0 || i == grid.length - 1 || j == grid[i].length - 1 || i == 0 || j == 0)
                    continue;
                int roomNo = grid[i][j];
                rooms.computeIfAbsent(roomNo, k -> new ArrayList<>());
                if(i >= 4 && i <= 46 && j >= 4 && j <= 46)
                    rooms.get(roomNo).add(new Point(i,j));
            }
        }
    }
}
