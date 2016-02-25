package com.smarter.LoveLog.model.orderMy;

import com.smarter.LoveLog.model.category.Paginated;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.redpacket.RedPacketData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/12/8.
 */
public class MyOrderInfo implements Serializable{
    private List<OrderList> data;
    private DataStatus  status;
    private Paginated  paginated;

    public Paginated getPaginated() {
        return paginated;
    }

    public void setPaginated(Paginated paginated) {
        this.paginated = paginated;
    }

    public List<OrderList> getData() {
        return data;
    }

    public void setData(List<OrderList> data) {
        this.data = data;
    }

    public DataStatus getStatus() {
        return status;
    }

    public void setStatus(DataStatus status) {
        this.status = status;
    }
}
