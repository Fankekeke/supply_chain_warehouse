package com.fank.f1k2.common.function;

import com.fank.f1k2.common.exception.RedisConnectException;

@FunctionalInterface
public interface JedisExecutor<T, R> {
    R excute(T t) throws RedisConnectException;
}
