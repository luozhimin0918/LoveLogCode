package com.smarter.LoveLog.model.address;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/8.
 */
public class QuanQuOrXianAddressData implements Serializable {
//    id:"398",
//    name:"迎江区"
    private  String id;
    private  String name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
