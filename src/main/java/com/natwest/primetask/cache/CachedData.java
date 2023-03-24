package com.natwest.primetask.cache;

import java.util.List;

public interface CachedData {

    List<Integer> get(final int key);

    void add(final int key,final List<Integer> values);
}
