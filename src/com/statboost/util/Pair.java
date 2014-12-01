package com.statboost.util;

/**
 * Created by Sam Kerr
 * 10:31 PM on 11/30/2014
 */
public class Pair<K, V> {
    private K key; //first member of pair
    private V value; //second member of pair

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
