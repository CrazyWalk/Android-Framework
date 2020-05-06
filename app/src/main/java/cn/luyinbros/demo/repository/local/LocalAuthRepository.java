package cn.luyinbros.demo.repository.local;

public interface LocalAuthRepository {

    void saveJwt(String authorization);

    String getJwt();

    boolean isLogin();


}
