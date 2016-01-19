package com.smarter.LoveLog.model.jsonModel;


import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/8.
 */
public class AddAddressParam implements Serializable {


  private  String  is_default;
  private  String  best_time;
  private  String  sign_building;
  private  String  tel;
  private  String  zipcode;
  private  String  address;
  private  String  district;
  private  String  city;
  private  String  province;
  private  String  country;
  private  String  email;
  private  String  mobile;
  private  String  consignee;
  private  String  id;


  public String getIs_default() {
    return is_default;
  }

  public void setIs_default(String is_default) {
    this.is_default = is_default;
  }

  public String getBest_time() {
    return best_time;
  }

  public void setBest_time(String best_time) {
    this.best_time = best_time;
  }

  public String getSign_building() {
    return sign_building;
  }

  public void setSign_building(String sign_building) {
    this.sign_building = sign_building;
  }

  public String getTel() {
    return tel;
  }

  public void setTel(String tel) {
    this.tel = tel;
  }

  public String getZipcode() {
    return zipcode;
  }

  public void setZipcode(String zipcode) {
    this.zipcode = zipcode;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getConsignee() {
    return consignee;
  }

  public void setConsignee(String consignee) {
    this.consignee = consignee;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
