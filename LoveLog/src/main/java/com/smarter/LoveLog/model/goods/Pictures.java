package com.smarter.LoveLog.model.goods;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/8.
 */
public class Pictures implements Serializable {
    private String thumb;
    private String cover;

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
