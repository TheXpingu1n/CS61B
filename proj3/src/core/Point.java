package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.Objects;

public class Point implements Comparable<Point> {
    private int x;
    private int y;
    private TETile tileset;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Point(int x, int y, TETile tileset) {
        this.x = x;
        this.y = y;
        this.tileset = tileset;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public TETile getTileset() {
        return tileset;
    }
    public void setTileset(TETile tileset) {
        this.tileset = tileset;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point other)) return false;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public int compareTo(Point o) {
        if(tileset.equals(o.tileset)) {
            return 0;
        }
        else if(tileset.equals(Tileset.NOTHING) && o.tileset.equals(Tileset.WALL)) {
            return -1;
        }
        else
            return 1;
    }
}
