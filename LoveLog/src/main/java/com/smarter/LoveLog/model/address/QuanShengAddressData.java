package com.smarter.LoveLog.model.address;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/1/8.
 */
public class QuanShengAddressData implements Serializable {
//    id:"2",
//    name:"北京",
//    city:[
    private  String id;
    private  String name;
    private List<QuanShiAddressData> city;

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

    public List<QuanShiAddressData> getCity() {
        return city;
    }

    public void setCity(List<QuanShiAddressData> city) {
        this.city = city;
    }

    //这个用来显示在PickerView上面的字符串,PickerView会通过反射获取getPickerViewText方法显示出来。
    public String getPickerViewText() {
        //这里还可以判断文字超长截断再提供显示
        return name;
    }
}
