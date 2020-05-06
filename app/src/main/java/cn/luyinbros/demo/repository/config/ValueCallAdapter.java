package cn.luyinbros.demo.repository.config;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;


import cn.luyinbros.demo.repository.data.EmptyObject;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class ValueCallAdapter extends CallAdapter.Factory {

    private ValueCallAdapter() {

    }

    public static ValueCallAdapter create() {
        return new ValueCallAdapter();
    }

    @Nullable
    @Override
    public CallAdapter<?, ?> get(@NonNull Type returnType,
                                 @NonNull Annotation[] annotations,
                                 @NonNull Retrofit retrofit) {
        return new Adapter<>(returnType);
    }


    private static class Adapter<R> implements CallAdapter<R, R> {
        final Type type;
        private static final ResponseBody EMPTY = ResponseBody.create("", MediaType.parse("UTF-8"));

        Adapter(Type type) {
            this.type = type;
        }

        @NonNull
        @Override
        public Type responseType() {
            return type;
        }


        @SuppressWarnings("unchecked")
        @Override
        public R adapt(Call<R> call) {
            try {
                R result = call.execute().body();
                if (result == null) {
                    // need handle null
                    return (R) EMPTY;
                } else {
                    return result;
                }

            } catch (IOException e) {
                return (R) EMPTY;
            }

        }
    }

}
