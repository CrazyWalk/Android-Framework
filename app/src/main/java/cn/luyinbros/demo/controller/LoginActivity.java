package cn.luyinbros.demo.controller;

import android.widget.EditText;

import androidx.annotation.NonNull;

import cn.luyinbros.demo.R;
import cn.luyinbros.demo.base.BaseActivity;
import cn.luyinbros.demo.base.task.ControllerTask;
import cn.luyinbros.demo.base.task.ControllerTaskException;
import cn.luyinbros.demo.base.task.OnSimpleTaskListener;
import cn.luyinbros.demo.base.task.core.AsyncValue;
import cn.luyinbros.demo.domain.DomainClient;
import cn.luyinbros.demo.repository.RemoteRepositoryClient;
import cn.luyinbros.demo.repository.RemoteRepositoryFactory;
import cn.luyinbros.demo.repository.data.EmptyObject;
import cn.luyinbros.logger.Logger;
import cn.luyinbros.logger.LoggerFactory;
import cn.luyinbros.valleyframework.controller.annotation.BindView;
import cn.luyinbros.valleyframework.controller.annotation.Controller;
import cn.luyinbros.valleyframework.controller.annotation.OnClick;

@Controller(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    @BindView(R.id.accountEditText)
    EditText accountEditText;
    @BindView(R.id.passwordEditText)
    EditText passwordEditText;
    private Logger logger= LoggerFactory.getLogger(LoginActivity.class);

    @OnClick(R.id.loginButton)
    void onLoginClick() {
        ControllerTask.create(
                this,
                new AsyncValue<EmptyObject>() {
                    @Override
                    public EmptyObject value() throws Throwable {
                        return DomainClient.getInstance(getApplicationContext())
                                .userCase()
                                .login(accountEditText.getText().toString(),
                                        passwordEditText.getText().toString());
                    }
                })
                .setTaskName("login")
                .execute(new OnSimpleTaskListener<EmptyObject, ControllerTaskException>() {
                    @Override
                    public void onNext(@NonNull EmptyObject value) {
                        logger.debug("login success");
                    }

                    @Override
                    public void onError(@NonNull ControllerTaskException exception) {
                        logger.debug("login failure");
                    }
                });
    }
}
