package com.smarter.LoveLog.model.community;

import com.smarter.LoveLog.model.category.Paginated;
import com.smarter.LoveLog.model.home.DataStatus;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/12/8.
 */
public class InvitationDataPinglunActi implements Serializable{
    private List<Pinglun> data;
    private Paginated paginated;
    private DataStatus  status;

    public List<Pinglun> getData() {
        return data;
    }

    public void setData(List<Pinglun> data) {
        this.data = data;
    }

    public DataStatus getStatus() {
        return status;
    }

    public void setStatus(DataStatus status) {
        this.status = status;
    }

    public Paginated getPaginated() {
        return paginated;
    }

    public void setPaginated(Paginated paginated) {
        this.paginated = paginated;
    }
}
