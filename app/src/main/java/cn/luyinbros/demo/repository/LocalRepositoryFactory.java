package cn.luyinbros.demo.repository;

import cn.luyinbros.demo.repository.local.LocalAuthRepository;

public interface LocalRepositoryFactory {

    LocalAuthRepository localAuthRepository();
}
