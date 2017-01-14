package com.compain.libraryshare.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import cn.bmob.v3.BmobObject;

/**
 * Created by jie.du on 17/1/9.
 */

@Entity
public class UserBean extends BmobObject {
    public String getBombId() {
        return bombId;
    }

    public void setBombId(String bombId) {
        this.bombId = bombId;
    }

    @Id
    private Long id;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;
    private String password;
    private String phone;
    private String bombId;
    private String wechatId;
    private String qqId;

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getQqId() {
        return qqId;
    }

    public void setQqId(String qqId) {
        this.qqId = qqId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String address;
    @Generated(hash = 1760743181)
    public UserBean(Long id, String username, String password, String phone,
            String bombId, String wechatId, String qqId, String address) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.bombId = bombId;
        this.wechatId = wechatId;
        this.qqId = qqId;
        this.address = address;
    }

    @Generated(hash = 1203313951)
    public UserBean() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
