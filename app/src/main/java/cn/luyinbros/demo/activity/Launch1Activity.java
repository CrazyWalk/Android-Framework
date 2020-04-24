package cn.luyinbros.demo.activity;

import android.content.Intent;

import cn.luyinbros.valleyframework.controller.annotation.InitState;

public class Launch1Activity extends BaseLaunchModeActivity {


    @InitState
    void init() {

    }

    @Override
    public void onStartButtonClick() {
        startActivity(new Intent(this, Launch2Activity.class));
    }
}
