package com.compain.libraryshare.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.compain.libraryshare.R;
import com.compain.libraryshare.biz.DataAccessListener;
import com.compain.libraryshare.biz.UserBiz;
import com.compain.libraryshare.model.UserBean;
import com.compain.libraryshare.util.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jie.du on 17/1/23.
 */
public class ModifyPwdActivity extends Activity {
    @Bind(R.id.tool_text)
    TextView toolText;
    @Bind(R.id.modify_finish)
    Button modifyFinish;
    @Bind(R.id.old_pwd)
    EditText oldPwd;
    @Bind(R.id.new_pwd)
    EditText newPwd;
    @Bind(R.id.old_pwd_ensure)
    EditText oldPwdEnsure;

    UserBiz userBiz = new UserBiz();
    UserBean userBean = new UserBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modidy_pwd);
        ButterKnife.bind(this);
        toolText.setText("修改密码");
        userBean = userBiz.getDefault();
    }

    @OnClick(R.id.modify_finish)
    public void onClick() {
        String old_pwd = oldPwd.getText().toString().trim();
        String new_pwd = newPwd.getText().toString().trim();
        String new_pwd_sure = oldPwdEnsure.getText().toString().trim();
        if (TextUtils.isEmpty(old_pwd) || !(old_pwd.equals(userBean.getPassword()))) {
            ToastUtils.show(this, "旧密码输入有误");
            return;
        }
        if (TextUtils.isEmpty(new_pwd) || TextUtils.isEmpty(new_pwd_sure) || !new_pwd.equals(new_pwd_sure)) {
            ToastUtils.show(this, "请检查两次密码输入是否有误");
            return;
        }
        userBean.setPassword(new_pwd);
        userBiz.updateUser(new DataAccessListener<String>() {
            @Override
            public void onGetData(String data, Throwable error) {
                ToastUtils.show(ModifyPwdActivity.this, data);
                if (error == null)
                    finish();
            }
        }, userBean);


    }
}
