package com.smarter.LoveLog.model.redpacket;

import com.smarter.LoveLog.model.category.Paginated;
import com.smarter.LoveLog.model.home.DataStatus;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/8.
 */
public class RedPacketInfo implements Serializable{
    private RedPacketData data;
    private Paginated paginated;
    private DataStatus  status;

    public RedPacketData getData() {
        return data;
    }

    public void setData(RedPacketData data) {
        this.data = data;
    }

    public Paginated getPaginated() {
        return paginated;
    }

    public void setPaginated(Paginated paginated) {
        this.paginated = paginated;
    }

    public DataStatus getStatus() {
        return status;
    }

    public void setStatus(DataStatus status) {
        this.status = status;
    }
}
