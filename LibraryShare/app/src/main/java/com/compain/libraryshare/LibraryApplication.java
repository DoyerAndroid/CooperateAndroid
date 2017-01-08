package com.compain.libraryshare;

import android.app.Application;

import com.compain.libraryshare.Util.AppCache;

import cn.bmob.v3.Bmob;

/**
 * Created by jie.du on 17/1/9.
 */
public class LibraryApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //第一：默认初始化Bomb
        Bmob.initialize(this, AppCache.bomb_id);
    }
}
