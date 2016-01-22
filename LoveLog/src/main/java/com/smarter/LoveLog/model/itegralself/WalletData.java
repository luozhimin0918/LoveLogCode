package com.smarter.LoveLog.model.itegralself;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/1/8.
 */
public class WalletData implements Serializable{
    private String  total_money ;
    private  String user_money;
    private  String frozen_money;
    private  String help_url;
     private List<ItegralDataList>       list;


    public String getTotal_money() {
        return total_money;
    }

    public void setTotal_money(String total_money) {
        this.total_money = total_money;
    }

    public String getUser_money() {
        return user_money;
    }

    public void setUser_money(String user_money) {
        this.user_money = user_money;
    }

    public String getFrozen_money() {
        return frozen_money;
    }

    public void setFrozen_money(String frozen_money) {
        this.frozen_money = frozen_money;
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
