package com.smarter.LoveLog.model.orderMy;

/**
 * Created by Administrator on 2016/4/21.
 */
public class ShopCarCreate {


    /**
     * succeed : 1
     */

    private StatusEntity status;
    /**
     * cart_number : 30
     * goods_price : ¥19.50
     * market_price : ¥29.50
     * saving : ¥10.00
     * save_rate : 34%
     * goods_amount : 19.5
     * real_goods_count : 1
     * virtual_goods_count : 0
     */

    private DataEntity data;

    public void setStatus(StatusEntity status) {
        this.status = status;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public StatusEntity getStatus() {
        return status;
    }

    public DataEntity getData() {
        return data;
    }

    public static class StatusEntity {
        private int succeed;

        public void setSucceed(int succeed) {
            this.succeed = succeed;
        }

        public int getSucceed() {
            return succeed;
        }
    }

    public static class DataEntity {
        private String cart_number;
        private String goods_price;
        private String market_price;
        private String saving;
        private String save_rate;
        private double goods_amount;
        private int real_goods_count;
        private int virtual_goods_count;

        public void setCart_number(String cart_number) {
            this.cart_number = cart_number;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public void setMarket_price(String market_price) {
            this.market_price = market_price;
        }

        public void setSaving(String saving) {
            this.saving = saving;
        }

        public void setSave_rate(String save_rate) {
            this.save_rate = save_rate;
        }

        public void setGoods_amount(double goods_amount) {
            this.goods_amount = goods_amount;
        }

        public void setReal_goods_count(int real_goods_count) {
            this.real_goods_count = real_goods_count;
        }

        public void setVirtual_goods_count(int virtual_goods_count) {
            this.virtual_goods_count = virtual_goods_count;
        }

        public String getCart_number() {
            return cart_number;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public String getMarket_price() {
            return market_price;
        }

        public String getSaving() {
            return saving;
        }

        public String getSave_rate() {
            return save_rate;
        }

        public double getGoods_amount() {
            return goods_amount;
        }

        public int getReal_goods_count() {
            return real_goods_count;
        }

        public int getVirtual_goods_count() {
            return virtual_goods_count;
        }
    }
}
