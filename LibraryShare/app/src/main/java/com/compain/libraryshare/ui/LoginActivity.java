package com.compain.libraryshare.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.compain.libraryshare.LibraryApplication;
import com.compain.libraryshare.R;
import com.compain.libraryshare.biz.DataAccessListener;
import com.compain.libraryshare.biz.UserBiz;
import com.compain.libraryshare.gen.DaoSession;
import com.compain.libraryshare.model.UserBean;
import com.compain.libraryshare.util.AppCache;
import com.compain.libraryshare.util.ToastUtils;
import com.compain.libraryshare.widget.DrawableCenterTextView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.greendao.AbstractDao;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

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
    Button registerUser;
    @Bind(R.id.username_edit)
    EditText usernameEdit;
    @Bind(R.id.wb_login)
    DrawableCenterTextView wbLogin;
    private AbstractDao userbeanDao;
    private UMShareAPI umShareAPI;
    private UserBiz userBiz = new UserBiz();
    private UserBean userBean = new UserBean();
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            if (platform.equals(SHARE_MEDIA.QQ)) {
                String qq_openid = data.get("openid");
                if (qq_openid != null && !qq_openid.isEmpty()) {
                    AppCache.putQqId(qq_openid);
                    userBiz.loginByQq(new DataAccessListener<UserBean>() {
                        @Override
                        public void onGetData(UserBean data, Throwable error) {
                            if (data != null) {
                                ToastUtils.show(getApplicationContext(), "QQ登录成功");
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            }
                        }
                    }, qq_openid);
                }

            } else if (platform.equals(SHARE_MEDIA.WEIXIN)) {

            } else if (platform.equals(SHARE_MEDIA.SINA)) {

            }

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            ToastUtils.show(getApplicationContext(), "Authorize fail");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            ToastUtils.show(getApplicationContext(), "Authorize cancel");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        umShareAPI = UMShareAPI.get(this);
        initDao();
        if (!AppCache.getUserId("").isEmpty()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
        userBiz.fetchUser(new DataAccessListener<UserBean>() {
            @Override
            public void onGetData(UserBean data, Throwable error) {
                userBean = data;
            }
        });

    }

    @OnClick({R.id.login_btn, R.id.forget_password, R.id.wechat_login, R.id.qq_login, R.id.register_new_user, R.id.wb_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                login();
                break;
            case R.id.forget_password:
                break;
            case R.id.wechat_login:
                if (umShareAPI.isInstall(this, SHARE_MEDIA.WEIXIN)) {
                    umShareAPI.doOauthVerify(this, SHARE_MEDIA.WEIXIN, umAuthListener);
                } else {
                    ToastUtils.show(this, R.string.please_install_wechat);
                }
                break;
            case R.id.qq_login:
                if (umShareAPI.isInstall(this, SHARE_MEDIA.QQ)) {
                    umShareAPI.doOauthVerify(this, SHARE_MEDIA.QQ, umAuthListener);
                } else {
                    ToastUtils.show(this, R.string.please_install_qq);
                }
                break;
            case R.id.wb_login:
                if (umShareAPI.isInstall(this, SHARE_MEDIA.SINA)) {
                    umShareAPI.doOauthVerify(this, SHARE_MEDIA.SINA, umAuthListener);
                } else {
                    ToastUtils.show(this, R.string.please_install_wb);
                }
                break;
            case R.id.register_new_user:
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                break;
        }
    }


    private void login() {
        final String username = usernameEdit.getText().toString().trim();
        final String password = pwdEdit.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            ToastUtils.show(LoginActivity.this, R.string.please_enter_username);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.show(LoginActivity.this, R.string.please_enter_password);
            return;
        }
        BmobQuery<UserBean> query = new BmobQuery<UserBean>();
        //查询username叫username的数据
        query.addWhereEqualTo("username", username);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(10);
        //执行查询方法
        query.findObjects(new FindListener<UserBean>() {
            @Override
            public void done(List<UserBean> object, BmobException e) {
                if (e == null && object.size() > 0) {
                    for (UserBean userBean : object) {
                        if (userBean.getPassword().equals(password)) {
                            ToastUtils.show(LoginActivity.this, "登录成功！");
                            AppCache.putUserId(userBean.getObjectId());
                            String qq_id = AppCache.getQqId("");
                            if (userBean.getQqId().equals("") && !qq_id.equals("")) {
                                userBean.setQqId(qq_id);
                                userBiz.updateUser(new DataAccessListener<String>() {
                                    @Override
                                    public void onGetData(String data, Throwable error) {

                                    }
                                }, userBean);
                            }
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                } else if (e == null && object.size() == 0) {
                    ToastUtils.show(LoginActivity.this, "用户名不存在");
                } else if (e != null) {
                    ToastUtils.show(LoginActivity.this, "错误 " + e.getCause());
                }
            }
        });

    }

    private void initDao() {
        DaoSession daoSession = LibraryApplication.getDaoSession();
        userbeanDao = daoSession.getUserBeanDao();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

}
