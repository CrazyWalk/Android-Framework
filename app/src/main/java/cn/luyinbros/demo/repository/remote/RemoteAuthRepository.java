package cn.luyinbros.demo.repository.remote;

import cn.luyinbros.demo.repository.data.AuthorizationResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RemoteAuthRepository {

    @FormUrlEncoded
    @POST("login")
    AuthorizationResult login(@Field("phone") String phone,
                              @Field("code") String code);
}
