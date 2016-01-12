package com.smarter.LoveLog.model;

import com.smarter.LoveLog.model.category.Paginated;

/**
 * Created by Administrator on 2016/1/8.
 */
public class CategoryJson {
  private String  id ;

  private PaginationJson pagination;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PaginationJson getPagination() {
        return pagination;
    }

    public void setPagination(PaginationJson pagination) {
        this.pagination = pagination;
    }
}
