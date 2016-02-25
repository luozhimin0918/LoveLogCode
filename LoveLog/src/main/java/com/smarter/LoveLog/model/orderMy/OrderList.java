package com.smarter.LoveLog.model.orderMy;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/1/8.
 */
public class OrderList implements Serializable {
    private String order_id;
    private String order_sn;
    private String order_time;
    private String total_fee;
    private String type;
    private List<GoodsList> goods_list;


    private String formated_integral_money;
    private String formated_bonus;
    private String formated_shipping_fee;
    private OrderInfo order_info;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<GoodsList> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsList> goods_list) {
        this.goods_list = goods_list;
    }

    public String getFormated_integral_money() {
        return formated_integral_money;
    }

    public void setFormated_integral_money(String formated_integral_money) {
        this.formated_integral_money = formated_integral_money;
    }

    public String getFormated_bonus() {
        return formated_bonus;
    }

    public void setFormated_bonus(String formated_bonus) {
        this.formated_bonus = formated_bonus;
    }

    public String getFormated_shipping_fee() {
        return formated_shipping_fee;
    }

    public void setFormated_shipping_fee(String formated_shipping_fee) {
        this.formated_shipping_fee = formated_shipping_fee;
    }

    public OrderInfo getOrder_info() {
        return order_info;
    }

    public void setOrder_info(OrderInfo order_info) {
        this.order_info = order_info;
    }
}
