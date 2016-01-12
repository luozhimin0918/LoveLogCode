package com.smarter.LoveLog.model.category;

import com.smarter.LoveLog.model.community.CommunityDataInfo;
import com.smarter.LoveLog.model.community.PromotePostsData;
import com.smarter.LoveLog.model.home.DataStatus;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/12/8.
 */
public class InvitationDataActi implements Serializable{
    private List<PromotePostsData> data;
    private Paginated paginated;
    private DataStatus  status;

    public List<PromotePostsData> getData() {
        return data;
    }

    public void setData(List<PromotePostsData> data) {
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
