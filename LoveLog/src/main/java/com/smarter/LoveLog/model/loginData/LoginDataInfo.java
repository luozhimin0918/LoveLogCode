package com.smarter.LoveLog.model.loginData;

import com.smarter.LoveLog.model.home.DataStatus;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/8.
 */
public class LoginDataInfo implements Serializable{
    private LoginDataActi data;
    private DataStatus  status;

    public LoginDataActi getData() {
        return data;
    }

    public void setData(LoginDataActi data) {
        this.data = data;
    }

    public DataStatus getStatus() {
        return status;
    }

    public void setStatus(DataStatus status) {
        this.status = status;
    }
}
