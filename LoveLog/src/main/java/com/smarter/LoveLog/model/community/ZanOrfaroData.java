package com.smarter.LoveLog.model.community;

import com.smarter.LoveLog.model.community.UserOrderNum;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/8.
 */
public class ZanOrfaroData implements Serializable {
    private String id;
    private String type;
    private String like_count;
    private String total;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

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

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }
}
