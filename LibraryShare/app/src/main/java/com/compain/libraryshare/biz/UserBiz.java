package com.compain.libraryshare.biz;

import com.compain.libraryshare.model.UserBean;
import com.compain.libraryshare.util.AppCache;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by jie.du on 17/1/23.
 */

public class UserBiz {
    private static UserBean DEFAULT;

    public UserBean getDefault() {
        return DEFAULT;
    }

    public void setDefault(UserBean userBean) {
        DEFAULT = userBean;
    }

    public void fetchUser(final DataAccessListener<UserBean> outerListener) {
        if (DEFAULT == null) {
            BmobQuery<UserBean> query = new BmobQuery<UserBean>();
            String object_id = AppCache.getUserId("");
            if (!object_id.isEmpty()) {
                query.getObject(object_id, new QueryListener<UserBean>() {
                    @Override
                    public void done(UserBean userBean, BmobException e) {
                        if (e == null && userBean != null) {
                            setDefault(userBean);
                        }
                        outerListener.onGetData(DEFAULT, e);
                    }
                });
            }
        } else {
            outerListener.onGetData(DEFAULT, null);
        }
    }

    public void updateUser(final DataAccessListener<String> outerListener, final UserBean userBean) {
        String object_id = AppCache.getUserId("");
        if (!object_id.isEmpty())
            userBean.update(object_id, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        outerListener.onGetData("更新成功", null);
                        setDefault(userBean);
                    } else
                        outerListener.onGetData("修改失败", e);
                }
            });
    }

    public void loginByQq(final DataAccessListener<UserBean> outerListener, String qq_id) {
        BmobQuery<UserBean> query = new BmobQuery<UserBean>();
        //查询username叫username的数据
        query.addWhereEqualTo("qqId", qq_id);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(10);
        query.findObjects(new FindListener<UserBean>() {
            @Override
            public void done(List<UserBean> list, BmobException e) {
                if (e == null && list.size() > 0) {
                    setDefault(list.get(0));
                    AppCache.putUserId(list.get(0).getObjectId());
                    outerListener.onGetData(list.get(0), null);
                } else if (e != null) {
                    outerListener.onGetData(null, e);
                }
            }
        });
    }

}
