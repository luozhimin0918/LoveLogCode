package com.smarter.LoveLog.model.notifacation;

import com.smarter.LoveLog.model.community.ObjectComment;
import com.smarter.LoveLog.model.community.User;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/8.
 */
public class notificationData implements Serializable {
    private String nid;
    private String icon;
    private String title;
    private String content;
    private String is_read;
    private String add_time;


    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
