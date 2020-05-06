package cn.luyinbros.demo.domain;

import cn.luyinbros.demo.repository.data.EmptyObject;

public interface UserCase {

    EmptyObject login(String phone,
                      String code);
}
