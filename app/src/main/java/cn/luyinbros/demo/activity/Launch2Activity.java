package cn.luyinbros.demo.activity;

import android.content.Intent;

public class Launch2Activity extends BaseLaunchModeActivity{

    @Override
    public void onStartButtonClick() {
        startActivity(new Intent(this,Launch3Activity.class));
    }
}
