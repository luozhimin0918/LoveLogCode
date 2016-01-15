package com.smarter.LoveLog.model.loginData;

import com.smarter.LoveLog.model.home.DataStatus;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/8.
 */
public class LogingOutInfo implements Serializable{
    private LogingOutMess data;
    private DataStatus  status;

    public LogingOutMess getData() {
        return data;
    }

    public void setData(LogingOutMess data) {
        this.data = data;
    }

    public DataStatus getStatus() {
        return status;
    }

    public void setStatus(DataStatus status) {
        this.status = status;
    }
}
