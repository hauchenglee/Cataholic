package com.example.dong.cataholic.webtask;

import com.example.dong.cataholic.MainActivity;

public class MyTaskParams {
    String url;
    Object object;
    MainActivity mainActivity;

    public MyTaskParams(String url, Object object) {
        this.url = url;
        this.object = object;
    }

    public MyTaskParams(String url, Object object, MainActivity mainActivity) {
        this.url = url;
        this.object = object;
        this.mainActivity = mainActivity;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
