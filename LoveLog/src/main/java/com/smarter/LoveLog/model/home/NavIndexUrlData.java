package com.smarter.LoveLog.model.home;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/8.
 */
public class NavIndexUrlData implements Serializable {
  private String  icon ;
    private String  name ;
    private String  url ;
    private String  sort_order ;
    private String  id ;

    private int  action_id ;

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

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getSort_order() {
    return sort_order;
  }

  public void setSort_order(String sort_order) {
    this.sort_order = sort_order;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getAction_id() {
    return action_id;
  }

  public void setAction_id(int action_id) {
    this.action_id = action_id;
  }
}
