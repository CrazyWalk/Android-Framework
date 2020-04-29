package cn.luyinbros.demo.base.task.core;

public interface AsyncValue<T> {
    T value() throws Throwable;
}
