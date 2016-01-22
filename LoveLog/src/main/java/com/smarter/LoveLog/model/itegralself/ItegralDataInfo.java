package com.smarter.LoveLog.model.itegralself;

import com.smarter.LoveLog.model.category.Paginated;
import com.smarter.LoveLog.model.community.User;
import com.smarter.LoveLog.model.home.DataStatus;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/8.
 */
public class ItegralDataInfo implements Serializable{
    private Paginated paginated;
    private Itegraldata data;
    private DataStatus  status;

    public Paginated getPaginated() {
        return paginated;
    }

    public void setPaginated(Paginated paginated) {
        this.paginated = paginated;
    }

    public Itegraldata getData() {
        return data;
    }

    public void setData(Itegraldata data) {
        this.data = data;
    }

    public DataStatus getStatus() {
        return status;
    }

    public void setStatus(DataStatus status) {
        this.status = status;
    }
}
