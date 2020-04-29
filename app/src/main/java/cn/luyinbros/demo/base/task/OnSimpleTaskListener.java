package cn.luyinbros.demo.base.task;

import androidx.annotation.NonNull;

import cn.luyinbros.demo.base.task.core.OnTaskListener;


public abstract class OnSimpleTaskListener<T, E extends Exception> implements OnTaskListener<T, E> {

    @Override
    public void onStart() {
        //empty
    }

    @Override
    public abstract void onNext(@NonNull T value);

    @Override
    public abstract void onError(@NonNull E exception);

    @Override
    public void onCancel() {
        //empty
    }

    @Override
    public void onDispose() {
        //empty
    }
}
