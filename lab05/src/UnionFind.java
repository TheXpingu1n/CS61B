public class UnionFind {
    // TODO: Instance variables
    public int[] disjointSets;
    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        // TODO: YOUR CODE HERE
        disjointSets = new int[N];
        for (int i = 0; i < disjointSets.length; i++) {
            disjointSets[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        // TODO: YOUR CODE HERE
        int root = find(v);
        int size = disjointSets[root]*-1;
        return size;
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        // TODO: YOUR CODE HERE
        return disjointSets[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO: YOUR CODE HERE
        int root1 = find(v1);
        int root2 = find(v2);
        return root1 == root2;
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        // TODO: YOUR CODE HERE
        if(v >= disjointSets.length)
            throw new IllegalArgumentException("not working nigga");
        int root = v;
        int leaf = v;
        while(parent(root) >= 0)
        {
            root = parent(root);
        }
        while(parent(leaf) >= 0)
        {
            int pid = parent(leaf);
            disjointSets[leaf] = root;
            leaf = pid;
        }
        return root;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        // TODO: YOUR CODE HERE
        if(connected(v1,v2))
            return;
        int size1 = sizeOf(v1);
        int size2 = sizeOf(v2);
        int root1 = find(v1);
        int root2 = find(v2);
        if(size1 > size2)
        {
            disjointSets[root1] += disjointSets[root2];
            disjointSets[root2] = root1;
        }
        else
        {
            disjointSets[root2] += disjointSets[root1];
            disjointSets[root1] = root2;
        }
    }

}
