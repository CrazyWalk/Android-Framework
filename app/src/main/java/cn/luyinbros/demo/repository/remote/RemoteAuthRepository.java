package cn.luyinbros.demo.repository.remote;

import cn.luyinbros.demo.repository.data.EmptyObject;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RemoteAuthRepository {

    @GET("login")
    EmptyObject login(@Query("phone") String username,
                      @Query("password") String password);
}
