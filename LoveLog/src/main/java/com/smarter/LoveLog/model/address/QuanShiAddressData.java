package com.smarter.LoveLog.model.address;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/1/8.
 */
public class QuanShiAddressData implements Serializable {
//    id:"36",
//    name:"安庆",
//    district:[
    private  String id;
    private  String name;
    private List<QuanQuOrXianAddressData> district;

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

    public List<QuanQuOrXianAddressData> getDistrict() {
        return district;
    }

    public void setDistrict(List<QuanQuOrXianAddressData> district) {
        this.district = district;
    }
}
