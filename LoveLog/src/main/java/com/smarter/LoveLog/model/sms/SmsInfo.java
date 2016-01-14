package com.smarter.LoveLog.model.sms;

import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.loginData.LoginDataActi;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/8.
 */
public class SmsInfo implements Serializable{
    private SmsData data;
    private DataStatus  status;

    public SmsData getData() {
        return data;
    }

    public void setData(SmsData data) {
        this.data = data;
    }

    public DataStatus getStatus() {
        return status;
    }

    public void setStatus(DataStatus status) {
        this.status = status;
    }
}
