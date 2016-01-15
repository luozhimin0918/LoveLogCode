package com.smarter.LoveLog.model.loginData;

import com.smarter.LoveLog.model.community.User;
import com.smarter.LoveLog.model.home.DataStatus;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/8.
 */
public class PersonalDataInfo implements Serializable{
    private User data;
    private DataStatus  status;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public DataStatus getStatus() {
        return status;
    }

    public void setStatus(DataStatus status) {
        this.status = status;
    }
}
