package com.smarter.LoveLog.model.help;

import com.smarter.LoveLog.model.community.User;
import com.smarter.LoveLog.model.home.DataStatus;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/8.
 */
public class HelpDataInfo implements Serializable{
    private HelpData data;
    private DataStatus  status;

    public HelpData getData() {
        return data;
    }

    public void setData(HelpData data) {
        this.data = data;
    }

    public DataStatus getStatus() {
        return status;
    }

    public void setStatus(DataStatus status) {
        this.status = status;
    }
}
