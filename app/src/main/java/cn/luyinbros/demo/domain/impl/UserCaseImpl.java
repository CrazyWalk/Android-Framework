package cn.luyinbros.demo.domain.impl;

import android.app.Application;

import cn.luyinbros.demo.domain.UserCase;
import cn.luyinbros.demo.repository.LocalRepositoryClient;
import cn.luyinbros.demo.repository.RemoteRepositoryClient;
import cn.luyinbros.demo.repository.data.AuthorizationResult;
import cn.luyinbros.demo.repository.data.EmptyObject;
import cn.luyinbros.demo.repository.local.LocalAuthRepository;
import cn.luyinbros.demo.repository.remote.RemoteAuthRepository;


public class UserCaseImpl implements UserCase {
    private Application application;

    public UserCaseImpl(Application application) {
        this.application = application;
    }

    @Override
    public EmptyObject login(String phone, String code) {
        final RemoteAuthRepository remoteAuthRepository = RemoteRepositoryClient.getInstance(application).authRepository();
        AuthorizationResult result = remoteAuthRepository.login(phone, code);
        final LocalAuthRepository localAuthRepository = LocalRepositoryClient.getInstance(application).localAuthRepository();
        localAuthRepository.saveJwt(result.authorization);
        return EmptyObject.INSTANCE;
    }
}
