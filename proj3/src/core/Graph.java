package core;

import tileengine.TETile;
import tileengine.Tileset;
import utils.RandomUtils;

import java.util.*;

public class Graph {
    private ArrayList<ArrayList<Point>> allBorderPoints;
    private ArrayList<Point> candidateBorderPoints;
    private TETile[][] grid;
    private int[] rows = { -1, 0, 1, 0 };
    private int[] cols = { 0, 1, 0, -1 };
    private boolean[][] visited;
    private Random rand;
    public Graph(TETile[][] grid) {
        this.grid = grid;
        allBorderPoints = new ArrayList<>();
        candidateBorderPoints = new ArrayList<>();
        visited = new boolean[grid.length][grid[0].length];
        rand = new Random();
        rand.setSeed(System.currentTimeMillis());
    }
    private boolean isValid(int row, int col) {

        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length && !visited[row][col];
    }
    private boolean isNothing(int row, int col) {
        return grid[row][col] == Tileset.NOTHING;
    }
    private boolean isFloor(int row, int col) {
        return grid[row][col] == Tileset.FLOOR;
    }
    public void getBorderPoints(int[][] grid, int NumOfRooms)
    {
        for (int row = 0; row <= NumOfRooms; row++) {
            if(row == 0)
                allBorderPoints.add(null);
            allBorderPoints.add(new ArrayList<>());
        }
        System.out.println("Number of rooms: " + NumOfRooms);
        for (int i = grid.length - 1; i >= 0; i--) {
            for (int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] == 0 || i == grid.length - 1 || j == grid[i].length - 1 || i == 0 || j == 0)
                    continue;
                int roomNo = grid[i][j];
                if(i >= 4 && i <= 46 && j >= 4 && j <= 46)
                    allBorderPoints.get(roomNo).add(new Point(i,j));
            }
        }
    }
    public void candidateBorderPointsInit(int NumOfRooms)
    {
        for (int row = 0; row <= NumOfRooms; row++) {
            candidateBorderPoints.add(null);
        }
        for(int i = 1; i < NumOfRooms; i++)
        {
            ArrayList<Point> borderPoints = allBorderPoints.get(i);
            int size = borderPoints.size();
            if(size == 0)
                continue;
            int randomPoint = RandomUtils.uniform(rand,size);
            candidateBorderPoints.add(i, borderPoints.get(randomPoint));
            allBorderPoints.get(i).remove(borderPoints.get(randomPoint));
            //System.out.println(allBorderPoints.get(i+1));
        }

    }
    public void printer()
    {
        for (int i = 0; i < candidateBorderPoints.size(); i++) {
            System.out.println(i + " " + candidateBorderPoints.get(i));
            Point p = candidateBorderPoints.get(i);
             //grid[p.getX()][p.getY()] = Tileset.NOTHING;
        }
    }
    public void clearCandidates()
    {
        candidateBorderPoints.clear();
    }
    private Point chooseRandomStart()
    {
        int total = 0;
        if(candidateBorderPoints.isEmpty())
        {
            return null;
        }
        for(int i = 0; i < candidateBorderPoints.size(); i++)
        {
            if(candidateBorderPoints.get(i) != null)
                total++;
        }
        if(total == 0)
            return null;
        int r = RandomUtils.uniform(rand,1,total+1);
        Point p = candidateBorderPoints.get(r);
        if(p == null)
            return null;
        grid[p.getX()][p.getY()] = Tileset.NOTHING;
        candidateBorderPoints.remove(r);
        System.out.println("Chosen Start: " + p);
        return p;
    }
    private List<Point> chooseRandomEnd()
    {
        int total = 0;
        if(allBorderPoints.isEmpty())
        {
            return null;
        }
        for (ArrayList<Point> allBorderPoint : allBorderPoints) {
            if (allBorderPoint != null)
                total++;
        }
        if(total == 0)
            return null;
        int r = RandomUtils.uniform(rand,1,total+1);
        ArrayList<Point> endPoints = allBorderPoints.get(r);
        System.out.println("Chosen End Points: " + endPoints);
        allBorderPoints.remove(r);
        return endPoints;
    }
    private void BFSTraversal(Point start, List<Point> endPoints) {
        System.out.println("Starting BFS from " + start);
        Queue<Point> queue = new LinkedList<>();
        Map<Point, Point> parentMap = new HashMap<>();
        queue.add(start);
        visited[start.getX()][start.getY()] = true;
        parentMap.put(start, null);
        Point neighbor;
        Point endPoint = null;

        while (!queue.isEmpty()) {
            Point p = queue.remove();
            System.out.println("Dequeued: " + p);

            // Check if we've reached a target
            if (endPoints.contains(p)) {
                System.out.println("Reached a target: " + p);
                endPoint = p;
                break;
            }

            // Collect valid neighbors
            List<Point> neighbors = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                int x = p.getX() + rows[i];
                int y = p.getY() + cols[i];
                if (isValid(x, y)) {
                    neighbor = new Point(x, y,grid[x][y]);
                    neighbors.add(neighbor);
                    visited[x][y] = true;
                    parentMap.put(neighbor, p);
                    System.out.println("  Adding neighbor: " + neighbor);
                }
            }

            // Randomize neighbors if desired
           Collections.sort(neighbors);
            queue.addAll(neighbors);
        }

        // Reconstruct path if we found an endpoint
        if (endPoint != null) {
            System.out.println("Reconstructing path...");
            Point current = endPoint;
            while (current != null) {
                System.out.println("  Path cell: " + current);
                grid[current.getX()][current.getY()] = Tileset.FLOOR;
                current = parentMap.get(current);
            }
        } else {
            System.out.println("No path found!");
        }

        // If you have a drawing method, call it here:
        // drawGrid(grid);
        // StdDraw.show();
    }
    /**
     * Finds the minimum-cost path from start to one of the endPoints.
     * Cost is defined as:
     *   - 0 for stepping on a NOTHING tile.
     *   - 1 for stepping on a WALL tile.
     *
     * Once the target is reached, the method reconstructs the path by marking it
     * on the grid (setting cells to TileType.FLOOR).
     *
     * @param start     The starting point.
     * @param endPoints A list of target points.
     */
    private void dijkstraTraversal(Point start, List<Point> endPoints)
    {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Map<Point, Point> parentMap = new HashMap<>();    // For path reconstruction.
        Map<Point, Integer> distMap = new HashMap<>();      // Best known cost to each cell.

        // Initialize with the start point.
        pq.add(new Node(start, 0));
        parentMap.put(start, null);
        distMap.put(start, 0);

        Point endPoint = null;

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            Point p = current.point;
            int cost = current.cost;

            // If we have found a better route before, skip this one.
            if (cost != distMap.getOrDefault(p, Integer.MAX_VALUE)) {
                continue;
            }

            // If we've reached one of the target points, finish.
            if (endPoints.contains(p)) {
                endPoint = p;
                break;
            }

            // Explore all 4 neighboring cells.
            for (int i = 0; i < 4; i++) {
                int nx = p.getX() + rows[i];
                int ny = p.getY() + cols[i];
                if (isValid(nx, ny)) {
                    // Determine additional cost: 0 if NOTHING, 1 if WALL.
                    // (You may adjust this if you have more tile types.)
                    int additionalCost = (isNothing(nx, ny) || isFloor(nx,ny)) ? 1: 3;
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

        // Reconstruct the path from endPoint back to start if found.
        if (endPoint != null) {
            Point current = endPoint;
            while (current != null) {
                grid[current.getX()][current.getY()] = Tileset.FLOOR; // Mark the path.
                current = parentMap.get(current);
            }
        } else {
            System.out.println("No path found from " + start + " to any target.");
        }
    }
    public void craveCorridors()
    {

        while(true) {
            Point start = chooseRandomStart();
            List<Point> endPoints = chooseRandomEnd();
            if(endPoints == null || start == null)
                break;
            printer();
            for (ArrayList<Point> borderPoints : allBorderPoints)
                System.out.println(borderPoints);
            dijkstraTraversal(start, endPoints);
        }


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
}
