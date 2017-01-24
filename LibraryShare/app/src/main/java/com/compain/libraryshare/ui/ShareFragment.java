package com.compain.libraryshare.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.compain.libraryshare.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by doyer.du on 2016/5/15.
 */
public class ShareFragment extends Fragment {
    @Bind(R.id.spinner_inputType)
    Spinner spinner_inputType;
    @Bind(R.id.spinner_inputAge)
    Spinner getSpinner_inputAge;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        ButterKnife.bind(this, view);
        //类型选择事件
        spinner_inputType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public static ShareFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        ShareFragment fragment = new ShareFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick({R.id.iv_addCover})
    public void onClick(View v) {
        switch (v.getId()) {
            //添加图片
            case R.id.iv_addCover:

                break;
        }
    }
}
