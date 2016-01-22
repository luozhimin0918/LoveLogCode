package com.smarter.LoveLog.model.itegralself;

import com.smarter.LoveLog.model.community.UserOrderNum;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/1/8.
 */
public class Itegraldata implements Serializable {
    private String pay_points;
    private String rank_points;
    private String help_url;
    private List<ItegralDataList> list;

    public String getPay_points() {
        return pay_points;
    }

    public void setPay_points(String pay_points) {
        this.pay_points = pay_points;
    }

    public String getRank_points() {
        return rank_points;
    }

    public void setRank_points(String rank_points) {
        this.rank_points = rank_points;
    }

    public String getHelp_url() {
        return help_url;
    }

    public void setHelp_url(String help_url) {
        this.help_url = help_url;
    }

    public List<ItegralDataList> getList() {
        return list;
    }

    public void setList(List<ItegralDataList> list) {
        this.list = list;
    }
}
