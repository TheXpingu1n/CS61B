import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    // TODO: Add any necessary instance variables.
    private WeightedQuickUnionUF unionUF;
    private WeightedQuickUnionUF virtual;
    private int[][] map;
    private int numberOfOS;
    private int ocean;
    private int ground;

    public Percolation(int N) {
        // TODO: Fill in this constructor.
        if(N <= 0)
            throw new IllegalArgumentException();
        unionUF = new WeightedQuickUnionUF((N*N)+2);
        virtual = new WeightedQuickUnionUF((N*N)+2);
        map = new int[N][N];
        ocean = (N*N);
        ground = (N*N) +1;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = 0;
                if(i == 0) {
                    unionUF.union(ocean, xyTo1D(i, j));
                    virtual.union(ocean, xyTo1D(i, j));
                }
                if(i == map.length-1)
                    unionUF.union(ground,xyTo1D(i,j));
            }
        }
    }
    // row+1,col...row-1,col...row,col+1...row,col-1
    public void open(int row, int col) {
        // TODO: Fill in this method.
        numberOfOS++;
        int point, me = xyTo1D(row,col);
        map[row][col] = 1;

        if(available(row+1,col))
        {
            point = xyTo1D(row+1,col);
            unionUF.union(me,point);
            virtual.union(me,point);
        }
        if(available(row-1,col))
        {
            point = xyTo1D(row-1,col);
            unionUF.union(me,point);
            virtual.union(me,point);
        }
        if(available(row,col+1))
        {
            point = xyTo1D(row,col+1);
            unionUF.union(me,point);
            virtual.union(me,point);
        }
        if(available(row,col-1))
        {
            point = xyTo1D(row,col-1);
            unionUF.union(me,point);
            virtual.union(me,point);
        }
    }

    public boolean isOpen(int row, int col) {
        // TODO: Fill in this method.
        return map[row][col] == 1;
    }

    public boolean isFull(int row, int col) {
        // TODO: Fill in this method.
        return virtual.connected(xyTo1D(row,col),ocean) && isOpen(row,col);
    }

    public int numberOfOpenSites() {
        // TODO: Fill in this method.
        return numberOfOS;
    }

    public boolean percolates() {
        // TODO: Fill in this method.
        return unionUF.connected(ground,ocean);
    }

    // TODO: Add any useful helper methods (we highly recommend this!).
    // TODO: Remove all TODO comments before submitting.
    private int xyTo1D(int r, int c)
    {
        int number = (c % map.length) + (r*map.length);
        return number;
    }
    private boolean bounds(int row , int col)
    {
        return row < map.length && row >= 0 && col < map.length && col >= 0;
    }
    private boolean available(int row, int col)
    {
        return bounds(row, col) && isOpen(row, col);
    }
}
