package com.smarter.LoveLog.model.orderMy;

import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 */
public class ShopCarOrderInfo {

    /**
     * succeed : 1
     */

    private StatusEntity status;
    /**
     * goods_list : [{"goods_id":"1","name":"爱的日志玫瑰水润蚕丝面膜（单片装）","goods_number":"10","subtotal":"¥39.00","formated_shop_price":"¥3.90","img":{"thumb":"http://www.aiderizhi.com/images/201603/thumb_img/1_thumb_G_1458155685646.png","cover":"http://www.aiderizhi.com/images/201603/goods_img/1_G_1458155685141.png"}},{"goods_id":"2","name":"爱的日志纪州备长炭净颜焕彩面膜","goods_number":"10","subtotal":"¥59.00","formated_shop_price":"¥5.90","img":{"thumb":"http://www.aiderizhi.com/images/201603/thumb_img/2_thumb_G_1458155762687.png","cover":"http://www.aiderizhi.com/images/201603/goods_img/2_G_1458155762104.png"}}]
     * total : {"goods_price":"¥0.00","market_price":"¥0.00","saving":"¥0.00","save_rate":0,"goods_amount":0,"real_goods_count":0,"virtual_goods_count":0}
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
        private  int error_code;
        private  String error_desc;

        public int getError_code() {
            return error_code;
        }

        public void setError_code(int error_code) {
            this.error_code = error_code;
        }

        public String getError_desc() {
            return error_desc;
        }

        public void setError_desc(String error_desc) {
            this.error_desc = error_desc;
        }

        public void setSucceed(int succeed) {
            this.succeed = succeed;
        }

        public int getSucceed() {
            return succeed;
        }
    }

    public static class DataEntity {
        /**
         * goods_price : ¥0.00
         * market_price : ¥0.00
         * saving : ¥0.00
         * save_rate : 0
         * goods_amount : 0
         * real_goods_count : 0
         * virtual_goods_count : 0
         */

        private TotalEntity total;
        /**
         * goods_id : 1
         * name : 爱的日志玫瑰水润蚕丝面膜（单片装）
         * goods_number : 10
         * subtotal : ¥39.00
         * formated_shop_price : ¥3.90
         * img : {"thumb":"http://www.aiderizhi.com/images/201603/thumb_img/1_thumb_G_1458155685646.png","cover":"http://www.aiderizhi.com/images/201603/goods_img/1_G_1458155685141.png"}
         */

        private List<GoodsListEntity> goods_list;

        public void setTotal(TotalEntity total) {
            this.total = total;
        }

        public void setGoods_list(List<GoodsListEntity> goods_list) {
            this.goods_list = goods_list;
        }

        public TotalEntity getTotal() {
            return total;
        }

        public List<GoodsListEntity> getGoods_list() {
            return goods_list;
        }

        public static class TotalEntity {
            private String goods_price;
            private String market_price;
            private String saving;
            private String save_rate;
            private int goods_amount;
            private int real_goods_count;
            private int virtual_goods_count;

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

            public void setGoods_amount(int goods_amount) {
                this.goods_amount = goods_amount;
            }

            public void setReal_goods_count(int real_goods_count) {
                this.real_goods_count = real_goods_count;
            }

            public void setVirtual_goods_count(int virtual_goods_count) {
                this.virtual_goods_count = virtual_goods_count;
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

            public int getGoods_amount() {
                return goods_amount;
            }

            public int getReal_goods_count() {
                return real_goods_count;
            }

            public int getVirtual_goods_count() {
                return virtual_goods_count;
            }
        }

        public static class GoodsListEntity {


            /**
             * rec_id : 6577
             * goods_id : 2
             * goods_sn : MC000002
             * goods_name : 爱的日志纪州备长炭净颜焕彩面膜
             * market_price : ¥8.90
             * goods_price : ¥5.90
             * goods_number : 20
             * goods_attr :
             * is_real : 1
             * extension_code :
             * parent_id : 0
             * rec_type : 0
             * is_gift : 0
             * is_shipping : 0
             * can_handsel : 0
             * goods_attr_id : 我是面膜
             * pid : 2
             * subtotal : ¥118.00
             * img_thumb : http://www.aiderizhi.com/images/201603/thumb_img/2_thumb_G_1458155762687.png
             */

            private String rec_id;
            private String goods_id;
            private String goods_sn;
            private String goods_name;
            private String market_price;
            private String goods_price;
            private String goods_number;
            private String goods_attr;
            private String is_real;
            private String extension_code;
            private String parent_id;
            private String rec_type;
            private String is_gift;
            private String is_shipping;
            private String can_handsel;
            private String goods_attr_id;
            private String pid;
            private String subtotal;
            private String img_thumb;

            public String getRec_id() {
                return rec_id;
            }

            public void setRec_id(String rec_id) {
                this.rec_id = rec_id;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_sn() {
                return goods_sn;
            }

            public void setGoods_sn(String goods_sn) {
                this.goods_sn = goods_sn;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getMarket_price() {
                return market_price;
            }

            public void setMarket_price(String market_price) {
                this.market_price = market_price;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public String getGoods_number() {
                return goods_number;
            }

            public void setGoods_number(String goods_number) {
                this.goods_number = goods_number;
            }

            public String getGoods_attr() {
                return goods_attr;
            }

            public void setGoods_attr(String goods_attr) {
                this.goods_attr = goods_attr;
            }

            public String getIs_real() {
                return is_real;
            }

            public void setIs_real(String is_real) {
                this.is_real = is_real;
            }

            public String getExtension_code() {
                return extension_code;
            }

            public void setExtension_code(String extension_code) {
                this.extension_code = extension_code;
            }

            public String getParent_id() {
                return parent_id;
            }

            public void setParent_id(String parent_id) {
                this.parent_id = parent_id;
            }

            public String getRec_type() {
                return rec_type;
            }

            public void setRec_type(String rec_type) {
                this.rec_type = rec_type;
            }

            public String getIs_gift() {
                return is_gift;
            }

            public void setIs_gift(String is_gift) {
                this.is_gift = is_gift;
            }

            public String getIs_shipping() {
                return is_shipping;
            }

            public void setIs_shipping(String is_shipping) {
                this.is_shipping = is_shipping;
            }

            public String getCan_handsel() {
                return can_handsel;
            }

            public void setCan_handsel(String can_handsel) {
                this.can_handsel = can_handsel;
            }

            public String getGoods_attr_id() {
                return goods_attr_id;
            }

            public void setGoods_attr_id(String goods_attr_id) {
                this.goods_attr_id = goods_attr_id;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getSubtotal() {
                return subtotal;
            }

            public void setSubtotal(String subtotal) {
                this.subtotal = subtotal;
            }

            public String getImg_thumb() {
                return img_thumb;
            }

            public void setImg_thumb(String img_thumb) {
                this.img_thumb = img_thumb;
            }
        }
    }
}
