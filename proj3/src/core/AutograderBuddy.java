package core;

import tileengine.TETile;
import tileengine.Tileset;

public class AutograderBuddy {

    /**
     * Simulates a game, but doesn't render anything or call any StdDraw
     * methods. Instead, returns the world that would result if the input string
     * had been typed on the keyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quit and
     * save. To "quit" in this method, save the game to a file, then just return
     * the TETile[][]. Do not call System.exit(0) in this method.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public static TETile[][] getWorldFromInput(String input) {
        StringBuilder st = new StringBuilder();
        TETile[][] tiles = new TETile[50][50];
        if(input.charAt(0) == 'n' || input.charAt(0) == 'N' &&
                (input.charAt(input.length()-1) == 's' || input.charAt(input.length()-1) == 'S')) {
            for(int i = 1; i < input.length()-1; i++) {
                if(String.valueOf(input.charAt(i)).matches("[0-9]")) {
                    st.append(input.charAt(i));
                }
            }
        }
        try {
            long s = Long.parseLong(st.toString());
            tiles = World.giveOutput(s);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return tiles;
    }


    /**
     * Used to tell the autograder which tiles are the floor/ground (including
     * any lights/items resting on the ground). Change this
     * method if you add additional tiles.
     */
    public static boolean isGroundTile(TETile t) {
        return t.character() == Tileset.FLOOR.character()
                || t.character() == Tileset.AVATAR.character()
                || t.character() == Tileset.FLOWER.character();
    }

    /**
     * Used to tell the autograder while tiles are the walls/boundaries. Change
     * this method if you add additional tiles.
     */
    public static boolean isBoundaryTile(TETile t) {
        return t.character() == Tileset.WALL.character()
                || t.character() == Tileset.LOCKED_DOOR.character()
                || t.character() == Tileset.UNLOCKED_DOOR.character();
    }
}
