package com.compain.libraryshare.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.compain.libraryshare.R;
import com.compain.libraryshare.model.UserBean;
import com.compain.libraryshare.util.AppCache;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by doyer.du on 2016/5/15.
 */
public class PersonFragment extends Fragment {
    @Bind(R.id.iv_userPhoto)
    ImageView ivUserPhoto;
    @Bind(R.id.tv_userName)
    TextView tvUserName;
    @Bind(R.id.tv_userTel)
    TextView tvUserTel;
    @Bind(R.id.tv_myCollect)
    TextView tvMyCollect;
    @Bind(R.id.tv_myShare)
    TextView tvMyShare;
    @Bind(R.id.tv_mySet)
    TextView tvMySet;

    UserBean userBean = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        ButterKnife.bind(this, view);
        BmobQuery<UserBean> query = new BmobQuery<UserBean>();
        String user_id = AppCache.getUserId("");
        if (!user_id.isEmpty())
            query.getObject(user_id, new QueryListener<UserBean>() {
                @Override
                public void done(UserBean userBean, BmobException e) {
                    if (e == null && userBean != null) {
                        PersonFragment.this.userBean = userBean;
                        tvUserName.setText(userBean.getUsername());
                        tvUserTel.setText(userBean.getPhone());
                    }
                }
            });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public static PersonFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        PersonFragment fragment = new PersonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_myCollect, R.id.tv_myShare, R.id.tv_mySet})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_myCollect:
                break;
            case R.id.tv_myShare:
                break;
            case R.id.tv_mySet:
                startActivity(new Intent(this.getActivity(),SetActivity.class));
                break;
        }
    }
}
