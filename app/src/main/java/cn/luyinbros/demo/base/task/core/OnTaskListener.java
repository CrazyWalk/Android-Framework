package cn.luyinbros.demo.base.task.core;

import androidx.annotation.NonNull;

public interface OnTaskListener<T,E> {

    void onStart();

    void onNext(@NonNull T value);

    void onError(@NonNull E exception);

    void onCancel();

    void onDispose();


}
