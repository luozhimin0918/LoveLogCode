package com.smarter.LoveLog.model.loginData;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/8.
 */
public class LogingOutMess implements Serializable {

    private  String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
