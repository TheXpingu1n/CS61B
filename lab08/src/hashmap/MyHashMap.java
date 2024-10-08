package hashmap;

import java.security.Key;
import java.util.*;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

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
        actualLF = (double) size / buckets.length;
        if(actualLF > loadFactor)
        {
            resize();
        }
        puting(key,value);
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        int idx = reduction(key);
        if(buckets[idx] == null)
            return null;
        for(Node i : buckets[idx])
        {
            if(i.key.equals(key))
            {
                return i.value;
            }
        }
        return null;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        int idx = reduction(key);
        if(buckets[idx] == null)
            return false;
        return checkExisting(buckets[idx],key);
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = null;
        }
        size = 0;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for this lab.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        int idx = reduction(key);
        V val = null;
        if(buckets[idx] == null)
            return null;

        for(Node i : buckets[idx])
        {
            if(i.key.equals(key))
            {
                buckets[idx].remove(i);
                size--;
                val = i.value;
            }
        }
        return val;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    private int items; // N
    private int size; // actual size
    private double loadFactor;
    private double actualLF;
    /** Constructors */
    public MyHashMap() {
        buckets = new Collection[16];
        loadFactor = 0.75;
    }

    public MyHashMap(int initialCapacity) {
        buckets = new Collection[initialCapacity];
        loadFactor = 0.75;
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        buckets = new Collection[initialCapacity];
        this.loadFactor = loadFactor;
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        // TODO: Fill in this method.
        return new LinkedList<>();
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    private int reduction(K key)
    {
        return Math.floorMod(key.hashCode(),buckets.length);
    }
    private void checkAndAdd(Collection<Node> bucket, Node node)
    {
        boolean updated = false;
        for(Node i : bucket)
        {
            if(node.key.equals(i.key))
            {
               i.value = node.value;
               updated = true;
               break;
            }
        }
        if(!updated)
        {
            bucket.add(node);
            size++;
        }
       //return bucket;
    }
    private boolean checkExisting(Collection<Node> bucket, K key)
    {
        for(Node i : bucket)
        {
            if(key.equals(i.key))
            {
                return true;
            }
        }
        return false;
    }
    private void resize()
    {
        Collection<Node>[] newHash = new Collection[this.buckets.length*2];
        Collection<Node>[] temp = this.buckets;
        size = 0;
        this.buckets = newHash;
        for (int i = 0; i < temp.length; i++) {
            if(temp[i] == null)
                continue;
            for(Node j : temp[i])
            {
                puting(j.key, j.value);
            }
        }
        temp = null;
    }
    private void puting(K key, V value)
    {
        Node node = new Node(key,value);
        int idx = reduction(key);
        if(buckets[idx] == null)
        {
            buckets[idx] = createBucket();
            size++;
            buckets[idx].add(node);
        }
        else
        {
            checkAndAdd(buckets[idx],node);
        }
    }
}
