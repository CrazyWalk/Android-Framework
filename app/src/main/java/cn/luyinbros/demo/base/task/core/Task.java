package cn.luyinbros.demo.base.task.core;

import androidx.annotation.NonNull;

public interface Task<T,E extends Exception> {

    void execute();

    void execute(OnTaskListener<T, E> listener);

    void cancel();

    boolean isCancel();

    void dispose();

    boolean isDisposed();

    @SuppressWarnings("UnusedReturnValue")
    Task<T,E> addTaskListener(@NonNull OnTaskListener<T, E> listener);

}
