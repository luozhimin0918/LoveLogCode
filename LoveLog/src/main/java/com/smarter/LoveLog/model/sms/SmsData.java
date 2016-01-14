package com.smarter.LoveLog.model.sms;

import com.smarter.LoveLog.model.community.User;
import com.smarter.LoveLog.model.loginData.SessionData;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/8.
 */
public class SmsData implements Serializable{
    private String  mobile;
    private String vcode;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }
}
