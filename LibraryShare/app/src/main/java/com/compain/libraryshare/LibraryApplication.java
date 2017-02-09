package com.compain.libraryshare;

import android.support.multidex.MultiDexApplication;

import com.compain.libraryshare.gen.DaoMaster;
import com.compain.libraryshare.gen.DaoSession;
import com.compain.libraryshare.util.AppCache;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.greendao.database.Database;

import cn.bmob.v3.Bmob;

/**
 * Created by jie.du on 17/1/9.
 */
public class LibraryApplication extends MultiDexApplication {
    private static LibraryApplication APPLICATION;
    private static DaoSession daoSession;

    {
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setSinaWeibo("895954271", "a2a9c6fafbf4868dc4acd2487627b56d");
        PlatformConfig.setQQZone("1105873771", "8cqZsC5lyU0pOAjn");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        APPLICATION = this;
        UMShareAPI.get(this);
        Config.REDIRECT_URL = "您新浪后台的回调地址";
        //第一：默认初始化Bomb
        Bmob.initialize(this, AppCache.bomb_id);
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "doyer");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    public static LibraryApplication getApplication() {
        return APPLICATION;
    }
}
