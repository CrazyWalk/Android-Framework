package cn.luyinbros.demo.repository.config;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import cn.luyinbros.demo.R;
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

         Adapter(Type type) {
            this.type = type;
        }

        @NonNull
        @Override
        public Type responseType() {
            return type;
        }



        @SuppressWarnings("NullableProblems")
        @Override
        public R adapt(Call<R> call) {
            try {
                return call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
