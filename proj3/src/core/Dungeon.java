package core;

import tileengine.TETile;
import tileengine.Tileset;
import utils.RandomUtils;

import java.util.Arrays;
import java.util.Random;

public class Dungeon {
    private Random random;
    private BSPNode root;
    private int[][] tiles;
    private int roomNo;
    public Dungeon() {
        random = new Random();
        random.setSeed(System.currentTimeMillis());
        tiles = new int[50][50];
    }
    public Dungeon(int x, int y, int width, int height) {
        this.root = new BSPNode(x, y, width, height);
        random = new Random();
        random.setSeed(System.currentTimeMillis());
        tiles = new int[50][50];
    }
    public Dungeon(long seed) {
        random = new Random();
        random.setSeed(seed);
        tiles = new int[50][50];
    }
    public BSPNode getRoot() {
        return root;
    }
    public void setRoot(BSPNode root) {
        this.root = root;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public int[][] getTiles() {
        return tiles;
    }
    public void setTiles(int[][] tiles) {
        this.tiles = tiles;
    }

    public int getRoomNo() {
        return roomNo;
    }

    // Minimum required width/height for a child partition
private static final int MIN_DIMENSION = 8;

    public BSPNode splitter(int x, int y, int width, int height, int numOfIterations, TETile[][] grid) {
        // Create a node representing this partition
        BSPNode node = new BSPNode(x, y, width, height);

        // Stop splitting if we have reached recursion limit
        if (numOfIterations <= 0) {
            return node;
        }

        // If this partition can't satisfy width >= 5 and height >= 5, skip splitting
        if (width < MIN_DIMENSION || height < MIN_DIMENSION) {
            return node;
        }

        // Determine if a horizontal or vertical split is possible:
        // - Horizontal split requires height >= 2*MIN_DIMENSION
        // - Vertical split requires width >= 2*MIN_DIMENSION
        boolean canSplitHorizontally = (height >= 2 * MIN_DIMENSION);
        boolean canSplitVertically   = (width  >= 2 * MIN_DIMENSION);

        // If neither direction is possible, skip splitting
        if (!canSplitHorizontally && !canSplitVertically) {
            return node;
        }

        // Randomly choose which way to split (if both are possible)
        boolean splitHorizontally;
        if (canSplitHorizontally && canSplitVertically) {
            splitHorizontally = random.nextBoolean();
        } else if (canSplitHorizontally) {
            splitHorizontally = true;
        } else {
            splitHorizontally = false;
        }

        int nextIterations = numOfIterations - 1;

        if (splitHorizontally) {
            // ------------------------------------------
            // Horizontal Split (cut along Y)
            // ------------------------------------------
            // We need a split offset so that BOTH children have height >= 5
            // So offset must be in [y+5, y+height-5]
            int splitY = RandomUtils.uniform(random, y + MIN_DIMENSION, (y + height +1)- MIN_DIMENSION);

            // Draw a horizontal line across the entire width at splitY
           // GridDrawer.drawHorizontalLine(grid, x, x + width, splitY);

            // Bottom child: from (x, y) with height = splitY - y
            node.left = splitter(x, y, width, splitY - y, nextIterations, grid);

            // Top child: from (x, splitY) with height = (y+height) - splitY
            node.right = splitter(x, splitY, width, (y + height) - splitY, nextIterations, grid);

        } else {
            // ------------------------------------------
            // Vertical Split (cut along X)
            // ------------------------------------------
            // We need a split offset so that BOTH children have width >= 5
            // So offset must be in [x+5, x+width-5]
            int splitX = RandomUtils.uniform(random, x + MIN_DIMENSION, (x + width+1) - MIN_DIMENSION);

            // Draw a vertical line across the entire height at splitX
           //GridDrawer.drawVerticalLine(grid, y, y + height, splitX);

            // Left child: from (x, y) with width = splitX - x
            node.left = splitter(x, y, splitX - x, height, nextIterations, grid);

            // Right child: from (splitX, y) with width = (x+width) - splitX
            node.right = splitter(splitX, y, (x + width) - splitX, height, nextIterations, grid);
        }

        return node;
    }
    // Constants for room minimum dimensions
    private static final int ROOM_MIN_WIDTH = 3;
    private static final int ROOM_MIN_HEIGHT = 3;

    private Room generateRoom(BSPNode node, TETile[][] grid) {
        // Ensure the partition (node) is large enough.
        // The BSP partition is assumed to be at least 5x5.
        roomNo++;
        // Calculate maximum possible room dimensions within the partition
        // Leaving a margin of 1 tile on each side.
        int maxRoomWidth = node.getWidth() - 8;  // partition width minus left/right margins
        int maxRoomHeight = node.getHeight() - 8;  // partition height minus top/bottom margins

        // Clamp maximum room sizes to be at least the minimum room dimensions.
        // (This is normally unnecessary if node.width and node.height are large,
        //  but is a safety check.)
        if (maxRoomWidth < ROOM_MIN_WIDTH) {
            maxRoomWidth = ROOM_MIN_WIDTH;
        }
        if (maxRoomHeight < ROOM_MIN_HEIGHT) {
            maxRoomHeight = ROOM_MIN_HEIGHT;
        }

        // Generate random room dimensions within allowed bounds.
        // RandomUtils.uniform(random, a, b) returns a value in [a, b).
        int roomWidth = RandomUtils.uniform(random, ROOM_MIN_WIDTH, maxRoomWidth + 1);
        int roomHeight = RandomUtils.uniform(random, ROOM_MIN_HEIGHT, maxRoomHeight + 1);

        // Determine the room's position within the partition.
        // The room should be offset by at least 1 tile from the partition's border.
        int roomX = RandomUtils.uniform(random, node.getX() + 2, (node.getX() + node.getWidth()) - roomWidth);
        int roomY = RandomUtils.uniform(random, node.getY() + 2, (node.getY() + node.getHeight()) - roomHeight);

        // Carve out the room in the grid by setting the floor tiles.
        for (int yy = roomY; yy < roomY + roomHeight; yy++) {
            for (int xx = roomX; xx < roomX + roomWidth; xx++) {
                grid[yy][xx] = Tileset.FLOOR;
            }
        }
        GridDrawer.drawPartitionBorder(grid, roomX, roomY, roomWidth, roomHeight,tiles, roomNo);

        // Return a new Room object representing the generated room.
        return new Room(roomX, roomY, roomWidth, roomHeight);
    }
    public void leafExplorer(BSPNode node, TETile[][] grid) {
        if(node.isLeaf()) {
            node.setRoom(generateRoom(node, grid));
            return ;
        }

        leafExplorer(node.left,grid);
        leafExplorer(node.right,grid);
    }
}
