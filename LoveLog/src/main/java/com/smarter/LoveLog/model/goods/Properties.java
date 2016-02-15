package com.smarter.LoveLog.model.goods;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/8.
 */
public class Properties implements Serializable {
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
