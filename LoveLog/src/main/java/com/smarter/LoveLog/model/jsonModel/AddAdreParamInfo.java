package com.smarter.LoveLog.model.jsonModel;

import com.smarter.LoveLog.model.loginData.SessionData;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/8.
 */
public class AddAdreParamInfo implements Serializable {

  private  String  id;
  private AddAdreParam address;
  private SessionData session;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public AddAdreParam getAddress() {
    return address;
  }

  public void setAddress(AddAdreParam address) {
    this.address = address;
  }

  public SessionData getSession() {
    return session;
  }

  public void setSession(SessionData session) {
    this.session = session;
  }
}
