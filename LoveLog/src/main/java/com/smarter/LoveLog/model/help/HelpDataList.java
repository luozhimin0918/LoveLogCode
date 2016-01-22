package com.smarter.LoveLog.model.help;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/8.
 */
public class HelpDataList implements Serializable {
    private String id;
    private String name;
    private String sort_order;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort_order() {
        return sort_order;
    }

    public void setSort_order(String sort_order) {
        this.sort_order = sort_order;
    }
}
