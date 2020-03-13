package com.incendiary.ambulanceapp.utils;

import org.greenrobot.eventbus.EventBus;

public class EBus {

    public static void post(Object o) {
        EventBus.getDefault().post(o);
    }

    public static void post(Class clazz) {
        try {
            EventBus.getDefault()
                    .post(clazz.newInstance());
        } catch (Exception ignored) {
        }
    }

    public static void register(Object o) {
        if (!EventBus.getDefault().isRegistered(o))
            EventBus.getDefault().register(o);
    }

    public static void unregister(Object o) {
        if (EventBus.getDefault().isRegistered(o))
            EventBus.getDefault().unregister(o);
    }
}
