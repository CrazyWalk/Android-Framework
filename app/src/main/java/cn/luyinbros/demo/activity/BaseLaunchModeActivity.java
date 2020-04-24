package cn.luyinbros.demo.activity;


import cn.luyinbros.demo.R;
import cn.luyinbros.demo.base.BaseActivity;
import cn.luyinbros.valleyframework.controller.annotation.Controller;
import cn.luyinbros.valleyframework.controller.annotation.OnClick;

@Controller(R.layout.activity_launch_mode)
public abstract class BaseLaunchModeActivity extends BaseActivity {


    @OnClick(R.id.startButton)
    public void onStartButtonClick() {
        //empty
    }
}
