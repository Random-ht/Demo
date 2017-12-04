package com.example.random.EventBus;


import org.greenrobot.eventbus.EventBus;

/**
 * 消息分发管理
 * Created by linzenos on 16/10/17.
 */
public class EventManager {
    public static void register(Object object) {
        if (!EventBus.getDefault().isRegistered(object)) {
            EventBus.getDefault().register(object);
        }
    }

    public static void unregister(Object object) {
        if (EventBus.getDefault().isRegistered(object)) {
            EventBus.getDefault().unregister(object);
        }
    }

    public static void post(Object object) {
        EventBus.getDefault().post(object);
    }

    public static void post(int what, Object object) {
        PostEvent event = new PostEvent();
        event.what = what;
        event.object = object;
        post(event);
    }

}
