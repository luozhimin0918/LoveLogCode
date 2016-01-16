package com.smarter.LoveLog.model.address;

import com.smarter.LoveLog.model.community.User;
import com.smarter.LoveLog.model.home.DataStatus;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/12/8.
 */
public class AddressDataInfo implements Serializable{
    private List<AddressData> data;
    private DataStatus  status;

    public List<AddressData> getData() {
        return data;
    }

    public void setData(List<AddressData> data) {
        this.data = data;
    }

    public DataStatus getStatus() {
        return status;
    }

    public void setStatus(DataStatus status) {
        this.status = status;
    }
}
