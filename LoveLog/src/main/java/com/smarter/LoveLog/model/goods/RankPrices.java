package com.smarter.LoveLog.model.goods;

import com.smarter.LoveLog.model.community.User;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/8.
 */
public class RankPrices implements Serializable {
    private String id;
    private String rank_name;
    private String price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRank_name() {
        return rank_name;
    }

    public void setRank_name(String rank_name) {
        this.rank_name = rank_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
