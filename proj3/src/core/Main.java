package core;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import utils.RandomUtils;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // build your own world!
        // for seed based worlds, use World.giveOutput(long seed) then pass the output to World.DisplayOutput(TETile[][] tiles)
        // for pure randomly generated world, use World.start()
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the seed: ");
        String str = sc.nextLine();
        TETile[][] tiles = AutograderBuddy.getWorldFromInput(str);
        World.DisplayOutput(tiles);
    }
}
