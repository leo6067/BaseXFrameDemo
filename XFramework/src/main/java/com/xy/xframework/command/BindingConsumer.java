package com.xy.xframework.command;

/**
 * 带参数的回调
 */
public interface BindingConsumer<T> {
    void call(T t);
}
