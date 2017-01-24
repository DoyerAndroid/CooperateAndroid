package com.compain.libraryshare.biz;

/**
 * Created by jie.du on 17/1/23.
 */
public interface DataAccessListener<T> {
    void onGetData(T data, Throwable error);
}
