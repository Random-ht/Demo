package com.example.random.EventBus;

import java.io.Serializable;

/**
 * 消息分发载体
 */
public class PostEvent implements Serializable {

    public int what = 0;
    public Object object = null;
    private int flag;//在首页任务下拉刷新处用到  用来标识是否可下拉刷新

    public int isFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public PostEvent(int flag) {
        this.flag = flag;
    }

    public PostEvent() {

    }


}
