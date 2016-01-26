package com.smarter.LoveLog.model.redpacket;

import com.smarter.LoveLog.model.community.UserOrderNum;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/8.
 */
public class CountRed implements Serializable {
    private int unused;
    private String used;
    private String overdue;

    public int getUnused() {
        return unused;
    }

    public void setUnused(int unused) {
        this.unused = unused;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getOverdue() {
        return overdue;
    }

    public void setOverdue(String overdue) {
        this.overdue = overdue;
    }
}
