package com.smarter.LoveLog.model.orderMy;

import com.smarter.LoveLog.model.community.Img;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/8.
 */
public class GoodsList implements Serializable {
    private String goods_id;
    private String name;
    private String goods_number;
    private String subtotal;
    private String formated_shop_price;
    private Img img;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoods_number() {
        return goods_number;
    }

    public void setGoods_number(String goods_number) {
        this.goods_number = goods_number;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getFormated_shop_price() {
        return formated_shop_price;
    }

    public void setFormated_shop_price(String formated_shop_price) {
        this.formated_shop_price = formated_shop_price;
    }

    public Img getImg() {
        return img;
    }

    public void setImg(Img img) {
        this.img = img;
    }
}
