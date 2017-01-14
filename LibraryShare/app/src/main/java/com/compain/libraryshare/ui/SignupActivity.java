package com.compain.libraryshare.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.compain.libraryshare.LibraryApplication;
import com.compain.libraryshare.R;
import com.compain.libraryshare.gen.DaoSession;
import com.compain.libraryshare.gen.UserBeanDao;
import com.compain.libraryshare.model.UserBean;
import com.compain.libraryshare.util.ToastUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class SignupActivity extends Activity {
    @Bind(R.id.tool_text)
    TextView toolText;
    @Bind(R.id.username_edit)
    EditText username_edit;
    @Bind(R.id.pwd_edit)
    EditText pwd_edit;
    @Bind(R.id.pwd_ensure)
    EditText pwd_ensure;
    @Bind(R.id.signup)
    Button signup;
    private UserBeanDao userbeanDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        toolText.setText(getText(R.string.signup));
        initDao();
    }

    private void initDao() {
        DaoSession daoSession = LibraryApplication.getDaoSession();
        userbeanDao = daoSession.getUserBeanDao();
    }

    @OnClick(R.id.signup)
    public void onClick() {
        signup();
    }

    private void signup() {
        final String username = username_edit.getText().toString().trim();
        final String password = pwd_edit.getText().toString().trim();
        final String password_sure = pwd_ensure.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            ToastUtils.show(SignupActivity.this, R.string.please_enter_username);
            return;
        }
        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(password_sure)) {
            ToastUtils.show(SignupActivity.this, R.string.please_enter_password);
            return;
        }
        if (!password.equals(password_sure)) {
            ToastUtils.show(SignupActivity.this, R.string.password_fail);
            return;
        }
        List<UserBean> userBeanList = userbeanDao.queryBuilder().where(UserBeanDao.Properties.Username.eq(username)).list();
        if (userBeanList.size() > 0) {
            String bombid = userBeanList.get(0).getBombId();
            BmobQuery<UserBean> bmobQuery = new BmobQuery<UserBean>();
            bmobQuery.getObject(bombid, new QueryListener<UserBean>() {
                @Override
                public void done(UserBean object, BmobException e) {
                    if (e == null && object.getUsername().equals(username)) {
                        ToastUtils.show(SignupActivity.this, "用户名已经注册！");
                    }
                }
            });

        } else {
            final UserBean userBean = new UserBean();
            userBean.setUsername(username);
            userBean.setPassword(password);
            userBean.save(new SaveListener<String>() {
                @Override
                public void done(String objectId, BmobException e) {
                    if (e == null) {
                        userBean.setBombId(objectId);
                        userbeanDao.insertOrReplace(userBean);
                        ToastUtils.show(SignupActivity.this, "恭喜您，注册成功！");
                        finish();

                    } else {
                        ToastUtils.show(SignupActivity.this, "创建数据失败：" + e.getMessage());
                    }
                }
            });
        }
    }
}
