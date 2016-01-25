package com.smarter.LoveLog.model.community;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/8.
 */
public class ObjectComment implements Serializable {
    private String id;
    private String type;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
