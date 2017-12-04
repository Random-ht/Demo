package com.example.random.Entity;

/**
 * Created by Administrator on 2017/10/19.
 */

public class User {

    private String uid;//用户id
    private String password;//用户密码
    private String nickname;//昵称
    private String mobile;//电话
    private String wechat;//微信号
    private String addressCn;//地址
    private String sex;//性别
    private String avater;//头像
    private int iconRes;//资源图片

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getAddressCn() {
        return addressCn;
    }

    public void setAddressCn(String addressCn) {
        this.addressCn = addressCn;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }
}
