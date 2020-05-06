package cn.luyinbros.demo.repository.interceptor;

import android.app.Application;
import android.content.Context;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import cn.luyinbros.demo.repository.LocalRepositoryClient;
import cn.luyinbros.demo.repository.local.LocalAuthRepository;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class PreInterceptor implements Interceptor {

    private final Context context;

    public PreInterceptor(Context context) {
        this.context = context;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        final LocalAuthRepository localAuthRepository = LocalRepositoryClient.getInstance(context).localAuthRepository();
        String token = localAuthRepository.getJwt();
        if (!token.isEmpty()) {
            return chain.proceed(chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer " + token)
                    .build());
        } else {
            return chain.proceed(chain.request());
        }

    }
}
