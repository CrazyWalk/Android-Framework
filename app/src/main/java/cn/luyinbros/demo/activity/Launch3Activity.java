package cn.luyinbros.demo.activity;

import android.content.Intent;

public class Launch3Activity extends BaseLaunchModeActivity{

    @Override
    public void onStartButtonClick() {
        startActivity(new Intent(this,Launch1Activity.class));
    }
}
