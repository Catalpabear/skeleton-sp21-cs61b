package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

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
    private HashSet<K> keySet;
    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    private int number;
    private int size;
    private final double maxLoad;

    /** Constructors */
    public MyHashMap() {
        this(16,0.75);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, 0.75);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        number = 0;
        size = initialSize;
        buckets = createTable(size);
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
        this.maxLoad = maxLoad;
        keySet = new HashSet<>();
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
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
        return new ArrayList<Node>();
        //return null;
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    @Override
    public void clear() {
        number = 0;
        size = 16;
        buckets = createTable(size);
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
        keySet = new HashSet<>();
    }

    @Override
    public boolean containsKey(K key) {
        Collection<Node> bucket = buckets[gethashcode(key) % buckets.length];
        for (Node node : bucket) {
            if (node.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        Collection<Node> bucket = buckets[gethashcode(key) % buckets.length];
        for (Node node : bucket) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return number;
    }

    @Override
    public void put(K key, V value) {
        Collection<Node> bucket = buckets[gethashcode(key) % buckets.length];
        for (Node node : bucket) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }
        help_put(key, value);
        if(need_resize()){
            resize();
        }
        keySet.add(key);
        number++;
    }
    private void help_put(K key, V value) {
        buckets[gethashcode(key) % size].add(createNode(key, value));
    }
    private void resize() {
        Collection<Node>[] newBuckets = createTable(size * 2);
        for (int i = 0; i < size * 2; i++) {
            newBuckets[i] = createBucket();
        }
        for (Collection<Node> bucket : buckets) {
            for (Node node : bucket) {
                newBuckets[gethashcode(node.key) % size].add(createNode(node.key, node.value));
            }
        }
        buckets = newBuckets;
    }
    private boolean need_resize(){
        return ((double) number / size) >= maxLoad;
    }

    private int gethashcode(K key){
        return Math.floorMod(key.hashCode(),size);
    }

    @Override
    public Set<K> keySet() {
        return keySet;
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }


}
