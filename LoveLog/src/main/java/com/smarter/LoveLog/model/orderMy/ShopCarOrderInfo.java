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
            private int save_rate;
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

            public void setSave_rate(int save_rate) {
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

            public int getSave_rate() {
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
            private String goods_id;
            private String name;
            private String goods_number;
            private String subtotal;
            private String formated_shop_price;
            /**
             * thumb : http://www.aiderizhi.com/images/201603/thumb_img/1_thumb_G_1458155685646.png
             * cover : http://www.aiderizhi.com/images/201603/goods_img/1_G_1458155685141.png
             */

            private ImgEntity img;

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setGoods_number(String goods_number) {
                this.goods_number = goods_number;
            }

            public void setSubtotal(String subtotal) {
                this.subtotal = subtotal;
            }

            public void setFormated_shop_price(String formated_shop_price) {
                this.formated_shop_price = formated_shop_price;
            }

            public void setImg(ImgEntity img) {
                this.img = img;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public String getName() {
                return name;
            }

            public String getGoods_number() {
                return goods_number;
            }

            public String getSubtotal() {
                return subtotal;
            }

            public String getFormated_shop_price() {
                return formated_shop_price;
            }

            public ImgEntity getImg() {
                return img;
            }

            public static class ImgEntity {
                private String thumb;
                private String cover;

                public void setThumb(String thumb) {
                    this.thumb = thumb;
                }

                public void setCover(String cover) {
                    this.cover = cover;
                }

                public String getThumb() {
                    return thumb;
                }

                public String getCover() {
                    return cover;
                }
            }
        }
    }
}
