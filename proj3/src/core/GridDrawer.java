package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.Arrays;

public class GridDrawer {

    public static void init(TETile[][] grid) {
        for (TETile[] teTiles : grid) {
            Arrays.fill(teTiles, Tileset.NOTHING);
        }
    }
    /**
     * Draws a horizontal line of MOUNTAIN tiles on row 'y'
     * from x = startX (inclusive) to x = endX (exclusive).
     */
    public static void drawHorizontalLine(TETile[][] grid, int startX, int endX, int y) {
        for (int x = startX; x < endX; x++) {
            grid[y][x] = Tileset.MOUNTAIN;
        }
    }
    /**
     * Draws a vertical line of MOUNTAIN tiles on column 'x'
     * from y = startY (inclusive) to y = endY (exclusive).
     */
    public static void drawVerticalLine(TETile[][] grid, int startY, int endY, int x) {
        for (int y = startY; y < endY; y++) {
            grid[y][x] = Tileset.MOUNTAIN;
        }
    }
    public static void drawPartitionBorder(TETile[][] grid, int x, int y, int width, int height, int[][] cells,int roomNo) {
        // left edge
        drawLeftBorder(grid,x,y,width,cells,roomNo);
        // Right edge
        drawRightBorder(grid,x,y,width,height,cells,roomNo);
        // Bottom edge
        drawBottomBorder(grid,x,y,height,cells,roomNo);
        // Top edge
        drawTopBorder(grid, x, y, width, height,cells,roomNo);
    }
    private static void drawLeftBorder(TETile[][] grid, int x, int y, int width,int[][] cells,int roomNo) {

        for (int col = x-1; col < x + width+1; col++) {
            grid[y-1][col] = Tileset.WALL;
        }
        for (int col = x; col < x + width; col++) {
            cells[y-1][col] = roomNo;
        }
    }
    private static void drawRightBorder(TETile[][] grid, int x, int y, int width, int height,int[][] cells,int roomNo) {
        for (int col = x-1; col < x + width+1; col++) {
            grid[y+height][col] = Tileset.WALL;
        }
        for (int col = x; col < x + width; col++) {
            cells[y+height][col] = roomNo;
        }
    }
    private static void drawBottomBorder(TETile[][] grid, int x, int y, int height,int[][] cells,int roomNo) {
        for (int row = y; row < y + height; row++) {
            grid[row][x-1] = Tileset.WALL;
        }
        for (int row = y; row < y + height; row++) {
            cells[row][x-1] = roomNo;
        }
    }
    private static void drawTopBorder(TETile[][] grid, int x, int y, int width, int height,int[][] cells,int roomNo) {
        for (int row = y; row < y + height; row++) {
            grid[row][x + width] = Tileset.WALL;
        }
        for (int row = y; row < y + height; row++) {
            cells[row][x + width] = roomNo;
        }
    }
    private static void cellFloorDrawer(TETile[][] grid, int x, int y) {
        grid[x][y] = Tileset.FLOOR;
    }
    private static void cellWallDrawer(TETile[][] grid, int x, int y) {
        grid[x][y] = Tileset.WALL;
    }
}
