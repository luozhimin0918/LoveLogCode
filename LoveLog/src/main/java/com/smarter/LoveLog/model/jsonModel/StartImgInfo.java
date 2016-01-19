package com.smarter.LoveLog.model.jsonModel;

import com.smarter.LoveLog.model.home.DataStatus;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/8.
 */
public class StartImgInfo implements Serializable{
    private DataStatus  status;
    private  StartImgData data;


    public DataStatus getStatus() {
        return status;
    }

    public void setStatus(DataStatus status) {
        this.status = status;
    }

    public StartImgData getData() {
        return data;
    }

    public void setData(StartImgData data) {
        this.data = data;
    }
}
