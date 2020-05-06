package cn.luyinbros.demo.repository;


import cn.luyinbros.demo.repository.remote.RemoteAuthRepository;
import cn.luyinbros.demo.repository.remote.RemoteUserRepository;

public interface RemoteRepositoryFactory {

    RemoteAuthRepository authRepository();

    RemoteUserRepository userRepository();


}
