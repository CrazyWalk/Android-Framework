package cn.luyinbros.demo.repository;



import cn.luyinbros.demo.repository.remote.RemoteAuthRepository;

public interface RemoteRepositoryFactory {


    RemoteAuthRepository authRepository();

}
