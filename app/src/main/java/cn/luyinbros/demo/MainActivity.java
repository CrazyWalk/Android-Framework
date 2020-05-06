package cn.luyinbros.demo;




import android.content.Intent;

import cn.luyinbros.demo.base.BaseActivity;
import cn.luyinbros.demo.controller.LoginActivity;
import cn.luyinbros.demo.controller.MineActivity;
import cn.luyinbros.valleyframework.controller.annotation.Controller;
import cn.luyinbros.valleyframework.controller.annotation.OnClick;

@Controller(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @OnClick(R.id.loginButton)
    void onLoginClick(){
        startActivity(new Intent(this, LoginActivity.class));
    }

    @OnClick(R.id.mineInfoButton)
    void onMineInfoClick(){
        startActivity(new Intent(this, MineActivity.class));
    }

}
