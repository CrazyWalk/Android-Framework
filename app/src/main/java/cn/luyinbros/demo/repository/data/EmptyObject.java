package cn.luyinbros.demo.repository.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Arrays;

public class EmptyObject {
    public static final EmptyObject INSTANCE = new EmptyObject();

    private EmptyObject() {

    }

    @NonNull
    @Override
    public String toString() {
        return "EMPTY_OBJECT";
    }

    @Override
    public int hashCode() {
        return -1;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj == INSTANCE;
    }
}
