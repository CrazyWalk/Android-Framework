package cn.luyinbros.demo.repository;

import android.app.Application;
import android.content.Context;

import cn.luyinbros.demo.repository.impl.LocalAuthRepositoryImpl;
import cn.luyinbros.demo.repository.local.LocalAuthRepository;

public class LocalRepositoryClient implements LocalRepositoryFactory{
    private final Application application;
    private static volatile LocalRepositoryClient INSTANCE;

    private LocalRepositoryClient(Application application) {
        this.application = application;
    }

    public static LocalRepositoryClient getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (RemoteRepositoryClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalRepositoryClient((Application) context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }


    private LocalAuthRepository localAuthRepository;
    @Override
    public LocalAuthRepository localAuthRepository() {
        if (localAuthRepository == null) {
            synchronized (LocalAuthRepository.class) {
                if (localAuthRepository == null) {
                    localAuthRepository = new LocalAuthRepositoryImpl(application);
                }
            }
        }
        return localAuthRepository;
    }
}
