package com.smarter.LoveLog.model.category;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/8.
 */
public class Paginated implements Serializable{
    private String  total ;
    private  int count;
    private  int more;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getMore() {
        return more;
    }

    public void setMore(int more) {
        this.more = more;
    }
}
