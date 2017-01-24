package com.compain.libraryshare.ui;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.compain.libraryshare.R;
import com.compain.libraryshare.ui.adapter.BookListAdapter;
import com.compain.libraryshare.util.OnRecyclerItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by doyer.du on 2016/5/15.
 */
public class BookFragment extends Fragment {
    @Bind(R.id.recyclerview_book)
    RecyclerView recyclerview_book;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        ButterKnife.bind(this, view);
        initRecyclerView();
        return view;
    }

    String[] bookName = {"十万个为什么", "格林童话", "一千零一夜"};
    String[] bookIntroduce = {"好多为什么", "好多童话", "一千零一个故事"};

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < bookName.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", bookName[i]);
            map.put("introduce", bookIntroduce[i]);
            list.add(map);
        }
        return list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public static BookFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        BookFragment fragment = new BookFragment();
        fragment.setArguments(args);
        return fragment;
    }

    BookListAdapter mBookListAdapter;

    public void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview_book.setLayoutManager(layoutManager);

        mBookListAdapter = new BookListAdapter(getActivity(), getData());
        recyclerview_book.setAdapter(mBookListAdapter);
        recyclerview_book.addOnItemTouchListener(new OnRecyclerItemClickListener(getActivity(), new OnRecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //跳转进入详情
                Intent i = new Intent(getActivity(), BookDetailActivity.class);
                i.putExtra("valueName", getData().get(position).get("name").toString());
                i.putExtra("valueIntroduce", getData().get(position).get("introduce").toString());
                startActivity(i);
            }
        }));
    }

    @Bind(R.id.ll_ageClassify)
    LinearLayout ll_ageClassify;
    @Bind(R.id.ll_typeClassify)
    LinearLayout ll_typeClassify;

    @OnClick({R.id.ll_ageClassify, R.id.ll_typeClassify})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.ll_ageClassify:
                //按年级排序
                popAgeWindow();
                break;
            case R.id.ll_typeClassify:
                //按类别排序
                popTypeWindow();
                break;
        }
    }

    public void popAgeWindow() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.popwindow_classify, null);
        ListView lv_classify = (ListView) view.findViewById(R.id.lv_classify);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.popwindow_item_classify,
                R.id.tv_value,
                classifyAge
        );
        lv_classify.setAdapter(arrayAdapter);
        final PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //测量view 注意这里，如果没有测量  ，下面的popupHeight高度为-2  ,因为LinearLayout.LayoutParams.WRAP_CONTENT这句自适应造成的
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = view.getMeasuredWidth();    //  获取测量后的宽度
        int popupHeight = view.getMeasuredHeight();  //获取测量后的高度
        int[] location = new int[2];
        // 允许点击外部消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());//注意这里如果不设置，下面的setOutsideTouchable(true);允许点击外部消失会失效
        popupWindow.setOutsideTouchable(true);   //设置外部点击关闭ppw窗口
        popupWindow.setFocusable(true);
        // 获得位置 这里的v是目标控件，就是你要放在这个v的上面还是下面
        ll_ageClassify.getLocationOnScreen(location);
        //这里就可自定义在上方和下方了 ，这种方式是为了确定在某个位置，某个控件的左边，右边，上边，下边都可以location[0] + ll_ageClassify.getWidth() / 2) - popupWidth / 2
        popupWindow.showAtLocation(ll_ageClassify, Gravity.NO_GRAVITY, 0, location[1] + popupHeight);
    }

    String[] classifyAge = {"学前班", "一年级", "二年级", "三年级"};
    String[] classifyType = {"文学", "艺术", "科技"};

    public void popTypeWindow() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.popwindow_classify, null);
        ListView lv_classify = (ListView) view.findViewById(R.id.lv_classify);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.popwindow_item_classify,
                R.id.tv_value,
                classifyType
        );
        lv_classify.setAdapter(arrayAdapter);
        final PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //测量view 注意这里，如果没有测量  ，下面的popupHeight高度为-2  ,因为LinearLayout.LayoutParams.WRAP_CONTENT这句自适应造成的
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = view.getMeasuredWidth();    //  获取测量后的宽度
        int popupHeight = view.getMeasuredHeight();  //获取测量后的高度
        int[] location = new int[2];
        // 允许点击外部消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());//注意这里如果不设置，下面的setOutsideTouchable(true);允许点击外部消失会失效
        popupWindow.setOutsideTouchable(true);   //设置外部点击关闭ppw窗口
        popupWindow.setFocusable(true);
        // 获得位置 这里的v是目标控件，就是你要放在这个v的上面还是下面
        ll_typeClassify.getLocationOnScreen(location);
        //popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);  //设置动画
        //这里就可自定义在上方和下方了 ，这种方式是为了确定在某个位置，某个控件的左边，右边，上边，下边都可以
        popupWindow.showAtLocation(ll_typeClassify, Gravity.NO_GRAVITY, 0, location[1] + popupHeight);
    }
}
