package cn.luyinbros.demo.controller;

import android.widget.TextView;

import androidx.annotation.NonNull;

import cn.luyinbros.demo.R;
import cn.luyinbros.demo.base.BaseActivity;
import cn.luyinbros.demo.base.task.ControllerTask;
import cn.luyinbros.demo.base.task.ControllerTaskException;
import cn.luyinbros.demo.base.task.OnSimpleTaskListener;
import cn.luyinbros.demo.base.task.core.AsyncValue;
import cn.luyinbros.demo.repository.RemoteRepositoryClient;
import cn.luyinbros.demo.repository.data.UserInfo;
import cn.luyinbros.valleyframework.controller.annotation.BindView;
import cn.luyinbros.valleyframework.controller.annotation.BuildView;
import cn.luyinbros.valleyframework.controller.annotation.Controller;

@Controller(R.layout.activity_mine)
public class MineActivity extends BaseActivity {
    @BindView(R.id.infoText)
    TextView infoText;


    @BuildView
    void onCreatedView() {
        ControllerTask.create(this,
                new AsyncValue<UserInfo>() {
                    @Override
                    public UserInfo value() throws Throwable {
                        return RemoteRepositoryClient.getInstance(getApplicationContext())
                                .userRepository()
                                .getUserInfo();
                    }
                })
                .setTaskName("userInfo")
                .execute(new OnSimpleTaskListener<UserInfo, ControllerTaskException>() {
                    @Override
                    public void onNext(@NonNull UserInfo value) {
                        infoText.setText("phone:" + value.getPhone());
                    }

                    @Override
                    public void onError(@NonNull ControllerTaskException exception) {
                        infoText.setText("error:");
                    }
                });
    }


}
