package com.compain.libraryshare.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.compain.libraryshare.R;
import com.compain.libraryshare.biz.DataAccessListener;
import com.compain.libraryshare.biz.UserBiz;
import com.compain.libraryshare.model.UserBean;
import com.compain.libraryshare.util.AppCache;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/1/5.
 */

public class SetActivity extends Activity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_modifyPass)
    TextView tvModifyPass;
    @Bind(R.id.tv_exitLogin)
    TextView tvExitLogin;

    UserBiz userBiz = new UserBiz();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ButterKnife.bind(this);
        userBiz.fetchUser(new DataAccessListener<UserBean>() {
            @Override
            public void onGetData(UserBean data, Throwable error) {
                tvAddress.setText(data.getAddress());
            }
        });

    }

    @OnClick({R.id.iv_back, R.id.tv_modifyPass, R.id.tv_exitLogin})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_exitLogin:
                AppCache.putUserId(null);
                startActivity(new Intent(SetActivity.this, LoginActivity.class));
                break;
            case R.id.tv_modifyPass:
                startActivity(new Intent(SetActivity.this, ModifyPwdActivity.class));
                break;
        }
    }
}
