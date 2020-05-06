package cn.luyinbros.demo.repository;

import android.app.Application;
import android.content.Context;

import cn.luyinbros.demo.repository.config.GsonConverterFactory;
import cn.luyinbros.demo.repository.config.ValueCallAdapter;
import cn.luyinbros.demo.repository.interceptor.PreInterceptor;
import cn.luyinbros.demo.repository.remote.RemoteAuthRepository;
import cn.luyinbros.demo.repository.remote.RemoteUserRepository;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class RemoteRepositoryClient implements RemoteRepositoryFactory {
    private Application application;
    private static volatile RemoteRepositoryClient INSTANCE;

    private RemoteRepositoryFactory IMPL;


    private RemoteRepositoryClient(Application application) {
        this.application = application;
        IMPL = new RemoteRepositoryFactoryImpl(application);

    }

    public static RemoteRepositoryClient getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (RemoteRepositoryClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RemoteRepositoryClient((Application) context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }


    @Override
    public RemoteAuthRepository authRepository() {
        return IMPL.authRepository();
    }


    @Override
    public RemoteUserRepository userRepository() {
        return IMPL.userRepository();
    }

    private static class RemoteRepositoryFactoryImpl implements RemoteRepositoryFactory {
        private Application application;
        private Retrofit defaultRetrofit;


        RemoteRepositoryFactoryImpl(Application application) {
            this.application = application;

            defaultRetrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.2.100:10010/")
                    .client(new OkHttpClient.Builder()
                            .addInterceptor(new PreInterceptor(application))
                            .build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(ValueCallAdapter.create())
                    .build();
        }

        @Override
        public RemoteAuthRepository authRepository() {
            return defaultRetrofit.create(RemoteAuthRepository.class);
        }


        @Override
        public RemoteUserRepository userRepository() {
            return defaultRetrofit.create(RemoteUserRepository.class);
        }
    }


}
