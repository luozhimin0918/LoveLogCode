package com.smarter.LoveLog.model.jsonModel;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/8.
 */
public class GuideImgData implements Serializable{
    private String  id;
    private String img;
    private String sort_order;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSort_order() {
        return sort_order;
    }

    public void setSort_order(String sort_order) {
        this.sort_order = sort_order;
    }
}
