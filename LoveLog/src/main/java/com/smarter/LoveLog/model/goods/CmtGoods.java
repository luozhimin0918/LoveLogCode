package com.smarter.LoveLog.model.goods;

import com.smarter.LoveLog.model.community.ObjectComment;
import com.smarter.LoveLog.model.community.User;
import com.smarter.LoveLog.model.help.HelpDataList;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/1/8.
 */
public class CmtGoods implements Serializable {
    private String cmt_id;
    private String parent_id;
    private String cmt_rank;
    private String content;
    private String like_count;
    private int floor;
    private ObjectComment object;
    private String add_time;
    private User user;

    public ObjectComment getObject() {
        return object;
    }

    public void setObject(ObjectComment object) {
        this.object = object;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getCmt_id() {
        return cmt_id;
    }

    public void setCmt_id(String cmt_id) {
        this.cmt_id = cmt_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getCmt_rank() {
        return cmt_rank;
    }

    public void setCmt_rank(String cmt_rank) {
        this.cmt_rank = cmt_rank;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
