package com.smarter.LoveLog.model.category;

import com.smarter.LoveLog.model.community.PromotePostsData;
import com.smarter.LoveLog.model.home.DataStatus;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/12/8.
 */
public class InvitationDataDeatil implements Serializable{
    private PromotePostsData data;
    private DataStatus  status;



    public DataStatus getStatus() {
        return status;
    }

    public void setStatus(DataStatus status) {
        this.status = status;
    }

    public PromotePostsData getData() {
        return data;
    }

    public void setData(PromotePostsData data) {
        this.data = data;
    }
}
