package cn.luyinbros.demo;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cn.luyinbros.logger.AndroidLoggerProvider;
import cn.luyinbros.logger.Logger;
import cn.luyinbros.logger.LoggerFactory;

public class FApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        LoggerFactory.initialize(new AndroidLoggerProvider.Builder()
                .setLevel(AndroidLoggerProvider.Level.DEBUG)
                .setGlobalTag("controller")
                .build());

    }
}
