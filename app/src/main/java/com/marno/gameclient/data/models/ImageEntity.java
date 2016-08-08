package com.marno.gameclient.data.models;

import io.realm.RealmObject;

/**
 * Created by marno on 2016/7/21/21:02.
 */
public class ImageEntity extends RealmObject {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
