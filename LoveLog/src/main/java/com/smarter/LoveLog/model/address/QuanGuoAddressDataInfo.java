package com.smarter.LoveLog.model.address;

import com.smarter.LoveLog.model.home.DataStatus;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/12/8.
 */
public class QuanGuoAddressDataInfo implements Serializable{
    private QuanProvinceData  data;
    private DataStatus  status;

    public QuanProvinceData getData() {
        return data;
    }

    public void setData(QuanProvinceData data) {
        this.data = data;
    }

    public DataStatus getStatus() {
        return status;
    }

    public void setStatus(DataStatus status) {
        this.status = status;
    }
}
