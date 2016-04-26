package com.smarter.LoveLog.model.orderMy;

/**
 * Created by Administrator on 2016/4/26.
 */
public class ShopCarNum {
    /**
     * succeed : 1
     */

    private StatusEntity status;
    /**
     * cart_number : null
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
        private Object cart_number;

        public void setCart_number(Object cart_number) {
            this.cart_number = cart_number;
        }

        public Object getCart_number() {
            return cart_number;
        }
    }
}
