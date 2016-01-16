package com.smarter.LoveLog.model.address;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/8.
 */
public class AddressData implements Serializable {
//            "id":"72",
//            "consignee":"撒旦发88",
//            "mobile":"15083806889",
//            "country_name":"中国",
//            "province_name":"福建",
//            "city_name":"南平",
//            "district_name":"建阳市",
//            "address":"三代富贵时代个三代富贵三代富贵三代富贵",
//            "is_default":1
    private  String id;
    private  String consignee;
    private  String mobile;
    private  String country_name;
    private  String province_name;
    private  String city_name;
    private  String district_name;
    private  String address;
    private  int is_default;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }
}
