package com.smarter.LoveLog.model.jsonModel;

import com.smarter.LoveLog.model.loginData.SessionData;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/8.
 */
public class PersonParamInfo implements Serializable {
  private  String  new_password;
  private  String  old_password;
  private  String  signature;
  private  String  birthday;
  private  String  sex;
  private  String  user_name;
  private String action;
  private String avatar;
  private SessionData session;


  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getNew_password() {
    return new_password;
  }

  public void setNew_password(String new_password) {
    this.new_password = new_password;
  }

  public String getOld_password() {
    return old_password;
  }

  public void setOld_password(String old_password) {
    this.old_password = old_password;
  }

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getUser_name() {
    return user_name;
  }

  public void setUser_name(String user_name) {
    this.user_name = user_name;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public SessionData getSession() {
    return session;
  }

  public void setSession(SessionData session) {
    this.session = session;
  }
}
