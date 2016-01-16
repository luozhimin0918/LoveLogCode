package com.smarter.LoveLog.model.home;

import com.smarter.LoveLog.model.home.DataStatus;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/8.
 */
public class DataStatusOne implements Serializable {
  private DataStatus status ;

    public DataStatus getStatus() {
        return status;
    }

    public void setStatus(DataStatus status) {
        this.status = status;
    }
}
