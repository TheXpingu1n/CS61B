package core;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import utils.RandomUtils;

import java.awt.*;
import java.util.Random;

public class Main {
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    public static final int ITER = RandomUtils.uniform(new Random(),2,7);
    public static void main(String[] args) {

        // build your own world!
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        BSPNode node = new BSPNode(0,0,WIDTH,HEIGHT);
        TETile[][] tiles = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
        Dungeon dungeon = new Dungeon();
        node = dungeon.splitter(node.getX(),node.getY(),node.getWidth(),node.getHeight(),ITER,tiles);
        dungeon.leafExplorer(node,tiles);
        ter.renderFrame(tiles);
    }
}
