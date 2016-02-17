package com.smarter.LoveLog.model.notifacation;

import com.smarter.LoveLog.model.category.Paginated;
import com.smarter.LoveLog.model.home.DataStatus;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/12/8.
 */
public class notificationActi implements Serializable{
    private List<notificationData> data;
    private Paginated paginated;
    private DataStatus  status;

    public List<notificationData> getData() {
        return data;
    }

    public void setData(List<notificationData> data) {
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
