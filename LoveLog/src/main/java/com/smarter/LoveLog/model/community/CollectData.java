package com.smarter.LoveLog.model.community;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/8.
 */
public class CollectData implements Serializable {
    private String is_collect;
    private String collect_count;
    private String message;

    public String getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(String is_collect) {
        this.is_collect = is_collect;
    }

    public String getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(String collect_count) {
        this.collect_count = collect_count;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
