package com.smarter.LoveLog.model.jsonModel;

import com.smarter.LoveLog.model.community.User;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.loginData.SessionData;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/8.
 */
public class ZanOrFaroviteParame implements Serializable{
    private String id;
    private String type;
    private SessionData session;

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

    public SessionData getSession() {
        return session;
    }

    public void setSession(SessionData session) {
        this.session = session;
    }
}
