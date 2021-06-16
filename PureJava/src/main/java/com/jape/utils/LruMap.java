package com.jape.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class LruMap<K, V> extends LinkedHashMap<K, V> {

    private final int maxSize;

    public LruMap(int maxSize) {
        super(maxSize, 0.75F, true);
        this.maxSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return maxSize < this.size();
    }

    public static void main(String[] args) {
        Map<String, Object> map = new LruMap<>(5);
        map.put("1", 1);
        map.put("2", 2);
        map.put("3", 3);
        map.put("4", 4);
        map.put("5", 5);

        map.get("3");

        map.put("6", 6);
        map.put("7", 7);
        System.err.println(map);
    }
}
