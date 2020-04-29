package cn.luyinbros.demo.base.task;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.luyinbros.demo.base.task.core.AsyncValue;
import cn.luyinbros.demo.base.task.core.OnTaskListener;
import cn.luyinbros.demo.base.task.core.Task;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public abstract class ControllerTask<T> implements Task<T, ControllerTaskException> {
    private static final TaskDispatcher mTaskDispatcher = new TaskDispatcher();

    public abstract void setTaskName(String taskName);


    @UiThread
    public static <T> ControllerTask<T> create(@NonNull AsyncValue<T> asyncValue,
                                               @NonNull LifecycleOwner lifecycleOwner) {
        return new ControllerTaskImpl<>(
                mTaskDispatcher.getOrCreateTaskProvider(lifecycleOwner.getLifecycle()),
                asyncValue);
    }


    private static class ControllerTaskImpl<T> extends ControllerTask<T> implements LifecycleEventObserver {
        private List<OnTaskListener<T, ControllerTaskException>> taskListeners;
        private final TaskProvider taskProvider;
        private AsyncValue<T> asyncValue;
        private Disposable mDisposable;
        private boolean isDisposed = false;
        private String taskName;
        private boolean isSetTaskName;

        private ControllerTaskImpl(TaskProvider taskProvider,
                                   AsyncValue<T> asyncValue) {
            this.taskProvider = taskProvider;
            this.asyncValue = asyncValue;
        }

        @Override
        public void setTaskName(String taskName) {
            if (isSetTaskName) {
                throw new IllegalStateException();
            }
            this.taskName = taskName;
            isSetTaskName = true;
        }

        @Override
        public void execute() {
            execute(null);
        }

        @Override
        public void execute(OnTaskListener<T, ControllerTaskException> listener) {
            if (isDisposed()) {
                return;
            }

            if (!isCancel()) {
                return;
            }
            if (!isSetTaskName) {
                setTaskName("");
            }

            if (!taskName.isEmpty()) {
                ControllerTaskImpl controllerTask = taskProvider.getRunningTask(taskName);
                if (controllerTask != null) {
                    controllerTask.dispose();
                }

            }

            Observable.create((ObservableOnSubscribe<T>) emitter -> {
                emitter.onNext(asyncValue.value());
                emitter.onComplete();
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<T>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            mDisposable = d;
                            dispatchStart(listener);
                        }

                        @Override
                        public void onNext(T t) {
                            listener.onNext(t);
                            dispatchNext(listener, t);
                            cancel();
                        }

                        @Override
                        public void onError(Throwable e) {
                            dispatchError(listener, e);
                            cancel();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }

        @Override
        public void cancel() {
            if (!isCancel()) {
                mDisposable.dispose();
            }
        }

        @Override
        public boolean isCancel() {
            return isDisposed || mDisposable == null || mDisposable.isDisposed();
        }

        @Override
        public void dispose() {
            if (!isDisposed) {
                cancel();
                isDisposed = true;
            }
        }

        @Override
        public boolean isDisposed() {
            return isDisposed;
        }

        @Override
        public Task<T, ControllerTaskException> addTaskListener(@NonNull OnTaskListener<T, ControllerTaskException> listener) {
            if (taskListeners == null) {
                taskListeners = new ArrayList<>();
            }
            taskListeners.add(listener);
            return this;
        }

        @Override
        public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
            if (event == Lifecycle.Event.ON_DESTROY) {
                cancel();
            }
        }

        private void dispatchStart(OnTaskListener<T, ControllerTaskException> listener) {
            listener.onStart();
            if (taskListeners != null && !taskListeners.isEmpty()) {
                for (OnTaskListener onTaskListener : taskListeners) {
                    onTaskListener.onStart();
                }
            }

        }


        private void dispatchNext(OnTaskListener<T, ControllerTaskException> listener, T value) {
            listener.onNext(value);
            if (taskListeners != null && !taskListeners.isEmpty()) {
                for (OnTaskListener<T, ControllerTaskException> onTaskListener : taskListeners) {
                    onTaskListener.onNext(value);
                }
            }
        }

        private void dispatchError(OnTaskListener<T, ControllerTaskException> listener, Throwable e) {
            ControllerTaskException exception = new ControllerTaskException();
            listener.onError(exception);
            if (taskListeners != null && !taskListeners.isEmpty()) {
                for (OnTaskListener<T, ControllerTaskException> onTaskListener : taskListeners) {
                    onTaskListener.onError(exception);
                }
            }
        }


    }


    private static class TaskProvider implements LifecycleEventObserver {
        private final Lifecycle lifecycle;
        private final Map<String, ControllerTaskImpl> runningTask = new HashMap<>();


        TaskProvider(Lifecycle lifecycle) {
            this.lifecycle = lifecycle;
            lifecycle.addObserver(this);
        }

        public ControllerTaskImpl getRunningTask(String taskName) {
            return runningTask.get(taskName);
        }


        @Override
        public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
            if (event == Lifecycle.Event.ON_DESTROY) {
                lifecycle.removeObserver(this);
                mTaskDispatcher.map.remove(lifecycle);
            }
        }


    }

    private static class TaskDispatcher {
        private Map<Lifecycle, TaskProvider> map = new HashMap<>();

        private TaskProvider getOrCreateTaskProvider(Lifecycle lifecycle) {
            TaskProvider taskProvider = map.get(lifecycle);
            if (taskProvider == null) {
                taskProvider = new TaskProvider(lifecycle);
                map.put(lifecycle, taskProvider);
            }
            return taskProvider;
        }

    }
}
