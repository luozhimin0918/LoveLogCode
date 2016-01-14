package com.smarter.LoveLog.model.loginData;

import com.smarter.LoveLog.model.category.Paginated;
import com.smarter.LoveLog.model.community.PromotePostsData;
import com.smarter.LoveLog.model.community.User;
import com.smarter.LoveLog.model.home.DataStatus;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/12/8.
 */
public class LoginDataActi implements Serializable{
    private SessionData  session;
    private User user;

    public SessionData getSession() {
        return session;
    }

    public void setSession(SessionData session) {
        this.session = session;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
