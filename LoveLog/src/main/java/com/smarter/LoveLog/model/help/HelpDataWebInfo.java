package com.smarter.LoveLog.model.help;

import com.smarter.LoveLog.model.home.DataStatus;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/8.
 */
public class HelpDataWebInfo implements Serializable{
    private HelpDataWeb data;
    private DataStatus  status;

    public HelpDataWeb getData() {
        return data;
    }

    public void setData(HelpDataWeb data) {
        this.data = data;
    }

    public DataStatus getStatus() {
        return status;
    }

    public void setStatus(DataStatus status) {
        this.status = status;
    }
}
