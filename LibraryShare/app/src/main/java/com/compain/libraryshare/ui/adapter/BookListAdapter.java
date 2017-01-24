package com.compain.libraryshare.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.compain.libraryshare.R;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mikes on 2016-8-19.
 */
public class BookListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    private List<Map<String, Object>> mData;
    public BookListAdapter(Context context , List<Map<String, Object>> mData) {
        this.mContext = context;
        this.mData=mData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recyclerview_item_book, parent, false);
        return new CircleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        CircleViewHolder holder = (CircleViewHolder) viewHolder;
        holder.tv_bookName.setText(mData.get(position).get("name").toString());
        holder.tv_bookIntroduce.setText(mData.get(position).get("introduce").toString());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public int getItemViewType(int position) {
        return position;
    }


    static class CircleViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.tv_bookName)
        TextView tv_bookName;
        @Bind(R.id.tv_bookAuthor)
        TextView tv_bookAuthor;
        @Bind(R.id.tv_bookIntroduce)
        TextView tv_bookIntroduce;

        public CircleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
