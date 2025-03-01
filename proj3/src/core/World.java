package core;

import tileengine.TERenderer;
import tileengine.TETile;
import utils.RandomUtils;

import java.util.Random;

public class World {

    // build your own world!
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    public static final int ITER = RandomUtils.uniform(new Random(),2,9);
    public static void start() {

        // build your own world!
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        BSPNode node = new BSPNode(0,0,WIDTH,HEIGHT);
        TETile[][] tiles = new TETile[WIDTH][HEIGHT];
        GridDrawer.init(tiles);
        Dungeon dungeon = new Dungeon();
        node = dungeon.splitter(node.getX(),node.getY(),node.getWidth(),node.getHeight(),ITER,tiles);
        dungeon.leafExplorer(node,tiles);
        int[][] cell = dungeon.getTiles();
        GraphUG g = new GraphUG(tiles);
        g.getBorderPoints(cell);
        g.connectRooms();
        g.addBordersToFloors();
        ter.renderFrame(tiles);
    }
}
