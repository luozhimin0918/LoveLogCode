package com.smarter.LoveLog.model.redpacket;

import com.smarter.LoveLog.model.community.ObjectComment;
import com.smarter.LoveLog.model.community.User;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/8.
 */
public class RedList implements Serializable {
    private String id;
    private String sn;
    private String name;
    private String amount;
    private String min_order_amount;
    private String start_time;
    private String end_time;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMin_order_amount() {
        return min_order_amount;
    }

    public void setMin_order_amount(String min_order_amount) {
        this.min_order_amount = min_order_amount;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
}
