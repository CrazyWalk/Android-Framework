package cn.luyinbros.demo.repository.impl;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import cn.luyinbros.demo.repository.local.LocalAuthRepository;

public class LocalAuthRepositoryImpl implements LocalAuthRepository {
    private SharedPreferences sharedPreferences;

    public LocalAuthRepositoryImpl(Context context) {
        sharedPreferences = context.getSharedPreferences("asi", Context.MODE_PRIVATE);
    }

    @Override
    public void saveJwt(String authorization) {
        sharedPreferences.edit()
                .putString("as", authorization)
                .apply();
    }

    @Override
    public String getJwt() {
        return sharedPreferences.getString("as", "");
    }

    @Override
    public boolean isLogin() {
        return !getJwt().isEmpty();
    }
}
