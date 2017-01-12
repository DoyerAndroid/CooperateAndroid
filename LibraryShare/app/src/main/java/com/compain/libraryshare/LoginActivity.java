package com.compain.libraryshare;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.compain.libraryshare.widget.DrawableCenterTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jie.du on 17/1/10.
 */
public class LoginActivity extends Activity {
    @Bind(R.id.pwd_edit)
    EditText pwdEdit;
    @Bind(R.id.login_btn)
    Button loginBtn;
    @Bind(R.id.forget_password)
    TextView forgetPassword;
    @Bind(R.id.wechat_login)
    DrawableCenterTextView wechatLogin;
    @Bind(R.id.qq_login)
    DrawableCenterTextView qqLogin;
    @Bind(R.id.register_new_user)
    Button registerNewUser;
    @Bind(R.id.username_edit)
    EditText usernameEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.login_btn, R.id.forget_password, R.id.wechat_login, R.id.qq_login, R.id.register_new_user})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                break;
            case R.id.forget_password:
                break;
            case R.id.wechat_login:
                break;
            case R.id.qq_login:
                break;
            case R.id.register_new_user:
                break;
        }
    }

}
