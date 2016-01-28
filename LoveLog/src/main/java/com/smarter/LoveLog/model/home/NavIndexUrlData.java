package com.smarter.LoveLog.model.home;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/8.
 */
public class NavIndexUrlData implements Serializable {
  private String  icon ;
    private String  name ;
    private String  action ;
    private String  param ;
    private String  id ;

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getParam() {
    return param;
  }

  public void setParam(String param) {
    this.param = param;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
