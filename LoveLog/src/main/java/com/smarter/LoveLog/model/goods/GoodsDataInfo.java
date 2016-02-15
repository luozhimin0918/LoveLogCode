package com.smarter.LoveLog.model.goods;

import com.smarter.LoveLog.model.help.HelpData;
import com.smarter.LoveLog.model.home.DataStatus;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/8.
 */
public class GoodsDataInfo implements Serializable{
    private GoodsData data;
    private DataStatus  status;

    public GoodsData getData() {
        return data;
    }

    public void setData(GoodsData data) {
        this.data = data;
    }

    public DataStatus getStatus() {
        return status;
    }

    public void setStatus(DataStatus status) {
        this.status = status;
    }
}
