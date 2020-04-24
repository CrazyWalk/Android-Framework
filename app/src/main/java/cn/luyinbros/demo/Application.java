package cn.luyinbros.demo;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cn.luyinbros.logger.AndroidLoggerProvider;
import cn.luyinbros.logger.Logger;
import cn.luyinbros.logger.LoggerFactory;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        LoggerFactory.initialize(new AndroidLoggerProvider.Builder()
                .setLevel(AndroidLoggerProvider.Level.DEBUG)
                .setGlobalTag("controller")
                .build());

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            private Logger logger = LoggerFactory.getLogger("activityLifecycle");

            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                logger.debug("onActivityCreated "+activity.toString());
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                logger.debug("onActivityStarted "+activity.toString());
            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                logger.debug("onActivityResumed "+activity.toString());
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
                logger.debug("onActivityPaused "+activity.toString());
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
                logger.debug("onActivityStopped "+activity.toString());
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                logger.debug("onActivityDestroyed "+activity.toString());
            }
        });
    }
}
