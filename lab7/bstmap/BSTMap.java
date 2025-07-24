package bstmap;

import org.w3c.dom.Node;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>,V> implements Map61B<K,V> {

    private class BSTNode<K,V>{
        K key;
        V value;
        BSTNode<K,V> left;
        BSTNode<K,V> right;
        BSTNode(K key, V value, BSTNode<K,V> left, BSTNode<K,V> right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    private BSTNode<K,V> root;
    private int size;
    public BSTMap() {
        clear();
    }
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return help_contain(root, key);
    }
    @Override
    public V get(K key) {
        return help_get(root,key);
    }
    private V help_get(BSTNode<K,V> move,K key){
        if (key==null) {throw new IllegalArgumentException("key is null");}
        if(move == null){
            return null;
        }
        if(move.key.compareTo(key) < 0){
            return help_get(move.left,key);
        }
        else if(move.key.compareTo(key) > 0){
            return help_get(move.right,key);
        }
        else{
            return move.value;
        }
    }
    private boolean help_contain(BSTNode<K,V> move,K key){
        if (key==null) {throw new IllegalArgumentException("key is null");}
        if(move == null){
            return false;
        }
        if(move.key.compareTo(key) < 0){
            return help_contain(move.left,key);
        }
        else if(move.key.compareTo(key) > 0){
            return help_contain(move.right,key);
        }
        else{
            return true;
        }
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        if (key==null) {throw new IllegalArgumentException("key is null");}
        root=help_put(root,key,value);
    }
    private BSTNode<K,V> help_put(BSTNode<K,V> node ,K key,V value){
        if (key==null) {throw new IllegalArgumentException("key is null");}
        if(node == null){
            size++;
            return new BSTNode<>(key,value,null,null);
        }
        if(node.key.compareTo(key) < 0){
            node.left = help_put(node.left,key,value);
        }else if(node.key.compareTo(key) > 0){
            node.right = help_put(node.right,key,value);
        }else {
            node.value = value;
        }
        return node;
    }
    public void printInOrder() {

    }
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }
}
