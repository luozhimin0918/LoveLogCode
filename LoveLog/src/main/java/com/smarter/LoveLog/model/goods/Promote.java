package com.smarter.LoveLog.model.goods;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/8.
 */
public class Promote implements Serializable {
    private int price;
    private String formated_rice;
    private String start_date;
    private String end_date;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getFormated_rice() {
        return formated_rice;
    }

    public void setFormated_rice(String formated_rice) {
        this.formated_rice = formated_rice;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
