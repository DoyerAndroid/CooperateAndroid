package com.compain.libraryshare.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.compain.libraryshare.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by luo on 2017/1/5.
 */

public class BookDetailActivity extends Activity {
    @Bind(R.id.tv_titleName)
    TextView tv_titleName;
    @Bind(R.id.tv_bookName)
    TextView tv_bookName;
    @Bind(R.id.tv_bookIntroduce)
    TextView tv_bookIntroduce;
    String valueName,valueIntroduce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        ButterKnife.bind(this);
        tv_titleName.setText("书籍详情");
        valueName=getIntent().getStringExtra("valueName");
        valueIntroduce=getIntent().getStringExtra("valueIntroduce");
        tv_bookName.setText(valueName);
        tv_bookIntroduce.setText(valueIntroduce);
    }
    @OnClick({R.id.iv_back})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
