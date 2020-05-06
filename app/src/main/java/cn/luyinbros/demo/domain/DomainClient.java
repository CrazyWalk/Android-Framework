package cn.luyinbros.demo.domain;

import android.app.Application;
import android.content.Context;

import cn.luyinbros.demo.domain.impl.UserCaseImpl;
import cn.luyinbros.demo.repository.LocalRepositoryClient;
import cn.luyinbros.demo.repository.RemoteRepositoryClient;

public class DomainClient implements DomainFactory {
    private static volatile DomainClient INSTANCE;
    private Application application;

    private DomainClient(Application application) {
        this.application = application;
    }

    public static DomainClient getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (RemoteRepositoryClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DomainClient((Application) context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }

    public static boolean isLogin(Context context) {
        return LocalRepositoryClient.getInstance(context)
                .localAuthRepository()
                .isLogin();

    }


    private UserCase userCase;

    @Override
    public UserCase userCase() {
        if (userCase == null) {
            synchronized (UserCase.class) {
                if (userCase == null) {
                    userCase = new UserCaseImpl(application);
                }
            }
        }
        return userCase;
    }


}
