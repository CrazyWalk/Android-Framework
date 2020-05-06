package cn.luyinbros.demo.repository.remote;

import cn.luyinbros.demo.repository.data.UserInfo;
import retrofit2.http.GET;

public interface RemoteUserRepository {

    @GET("/user/info")
    UserInfo getUserInfo();
}
