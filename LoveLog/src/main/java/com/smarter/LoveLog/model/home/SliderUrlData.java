package com.smarter.LoveLog.model.home;

/**
 * Created by Administrator on 2016/1/8.
 */
public class SliderUrlData {
  private String  image_url ;
    private String  url ;
    private String  sort_order ;
    private String   id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
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


}
