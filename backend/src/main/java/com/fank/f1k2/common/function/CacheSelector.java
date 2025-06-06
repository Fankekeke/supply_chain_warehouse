package com.fank.f1k2.common.function;

@FunctionalInterface
public interface CacheSelector<T> {
    T select() throws Exception;
}
