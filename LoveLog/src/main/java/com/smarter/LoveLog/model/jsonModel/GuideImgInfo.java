package com.smarter.LoveLog.model.jsonModel;

import com.smarter.LoveLog.model.home.DataStatus;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/12/8.
 */
public class GuideImgInfo implements Serializable{
    private DataStatus  status;
    private List<GuideImgData> data;


    public DataStatus getStatus() {
        return status;
    }

    public void setStatus(DataStatus status) {
        this.status = status;
    }

    public List<GuideImgData> getData() {
        return data;
    }

    public void setData(List<GuideImgData> data) {
        this.data = data;
    }
}
