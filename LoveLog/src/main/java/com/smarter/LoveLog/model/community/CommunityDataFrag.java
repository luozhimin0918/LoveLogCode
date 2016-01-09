package com.smarter.LoveLog.model.community;

import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.home.HomeDataInfo;

/**
 * Created by Administrator on 2015/12/8.
 */
public class CommunityDataFrag {
    private CommunityDataInfo data;
    private DataStatus  status;

    public CommunityDataInfo getData() {
        return data;
    }

    public void setData(CommunityDataInfo data) {
        this.data = data;
    }

    public DataStatus getStatus() {
        return status;
    }

    public void setStatus(DataStatus status) {
        this.status = status;
    }
}
