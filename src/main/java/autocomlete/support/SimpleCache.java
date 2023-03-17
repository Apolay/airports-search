package autocomlete.support;

import java.util.*;


public class SimpleCache {
    private static SimpleCache instance;
    private int capacity = 2395; // 1/3 of file lines
    private int size = 0;// lines
    private Map<String, List<String>> cache = new HashMap<>();
    private Queue<String> keys = new LinkedList<>();
    private boolean banned = false;

    private SimpleCache() {}

    public String cacheResult(String key, String line) {//!
        if(banned) return line;

        if(size + 1 > capacity) {
            banned = true;
            cache.remove(keys.poll());
            return line;
        }

        if(cache.containsKey(key)) {
            cache.get(key).add(line);
        } else {
            List<String> list = new ArrayList<>();
            list.add(line);
            cache.put(key, list);
            keys.add(key);
        }

        return line;
    }

    public static SimpleCache getInstance() {
        return Objects.requireNonNullElseGet(instance, () -> instance = new SimpleCache());
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return cache.isEmpty();
    }

    public boolean contains(String str) {
        return cache.containsKey(str);
    }

    public List<String> getCache(String str) {//!
        return cache.get(str);
    }

    public void restBanned() {
        banned = false;
    }
}
