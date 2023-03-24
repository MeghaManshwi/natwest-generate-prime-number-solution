package com.natwest.primetask.cache;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Cached layer.
 */
@Component
public class CachedDataImpl implements CachedData{
    private static Map<Integer, List<Integer>> cachedData=new HashMap<>();

    /**
     * Get from cache.
     *
     * @param key {@link Integer}
     * @return {@link List of {@link Integer}}
     */
    @Override
    public List<Integer> get(final int key){
        return cachedData.get(key);
    }

    /**
     * Add to cache.
     *
     * @param key {@link Integer}
     * @param values {@link List of {@link Integer}}
     */
    @Override
    public void add(final int key,final List<Integer> values){
         cachedData.put(key,values);
    }

}
