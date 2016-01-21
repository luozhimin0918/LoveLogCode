package com.smarter.LoveLog.model.community;

import com.smarter.LoveLog.model.home.DataStatus;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/8.
 */
public class RewardDataInfo implements Serializable{
    private RewardData data;
    private DataStatus  status;

    public RewardData getData() {
        return data;
    }

    public void setData(RewardData data) {
        this.data = data;
    }

    public DataStatus getStatus() {
        return status;
    }

    public void setStatus(DataStatus status) {
        this.status = status;
    }
}
