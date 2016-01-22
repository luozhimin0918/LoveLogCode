package com.smarter.LoveLog.model.feedback;

import com.smarter.LoveLog.model.help.HelpData;
import com.smarter.LoveLog.model.home.DataStatus;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/8.
 */
public class FeedDataInfo implements Serializable{
    private FeedMess data;
    private DataStatus  status;

    public FeedMess getData() {
        return data;
    }

    public void setData(FeedMess data) {
        this.data = data;
    }

    public DataStatus getStatus() {
        return status;
    }

    public void setStatus(DataStatus status) {
        this.status = status;
    }
}
