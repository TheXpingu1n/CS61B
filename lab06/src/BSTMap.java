import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K,V>{
    private BSTNode node;
    private int size;
    public BSTMap()
    {
        node = null;
    }
    private class BSTNode{
        K key;
        V value;
        BSTNode left;
        BSTNode right;

        public BSTNode(K key, V value, BSTNode left, BSTNode right)
        {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }
    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        this.node = insert(this.node, key, value);
    }
    private BSTNode insert(BSTNode node, K key, V value) {
        if (node == null) {
            // Create and return the new node
            node = new BSTNode(key, value, null, null);
            size++;
            return node;
        }

        // If the key is greater, go to the right subtree
        if (key.compareTo(node.key) > 0) {
            node.right = insert(node.right, key, value);
        }
        // If the key is less, go to the left subtree
        else if (key.compareTo(node.key) < 0) {
            node.left = insert(node.left, key, value);
        }
        // If key already exists, update the value
        else {
            node.value = value;
        }

        return node;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        V result = getRec(this.node,key);
        return result;
    }
    public V getRec(BSTNode node, K key)
    {
        if(node == null)
            return null;
        if(node.key.equals(key))
            return node.value;
        else if (node.key.compareTo(key) < 0) {
            return getRec(node.right,key);
        }
        else
            return getRec(node.left,key);
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        BSTNode res = containsKeyRec(this.node,key);
        if(res != null)
            return true;
        return false;
    }
    private BSTNode containsKeyRec(BSTNode node, K key)
    {
       if(node == null)
           return null;
       if(node.key.equals(key))
           return node;
       else if(node.key.compareTo(key) < 0)
       {
           return containsKeyRec(node.right,key);
       }
       else
           return containsKeyRec(node.left,key);
    }
    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        node = null;
        size = 0;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        Set<K> set = new TreeSet<>();
        SetOfKeys(this.node,set);
        return set;
    }
    private Set<K> SetOfKeys(BSTNode node, Set<K> Keys)
    {
        if(node == null)
            return Keys;

        SetOfKeys(node.left,Keys);
        Keys.add(node.key);
        SetOfKeys(node.right,Keys);
        return Keys;
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
       throw new UnsupportedOperationException();
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        return new BSTMapIter(keySet());
    }
    private class BSTMapIter implements Iterator<K>
    {
        Set<K> set;
        public BSTMapIter(Set<K> set)
        {
            this.set = set;
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {

            return set.iterator().hasNext();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public K next() {
            if(!hasNext())
                return null;
            return set.iterator().next();
        }
    }
}
