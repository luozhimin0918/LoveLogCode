package com.smarter.LoveLog.model.orderMy;

import java.util.List;

/**
 * Created by Administrator on 2016/4/28.
 */
public class LocalShopCarData {

    /**
     * rec_id : 7412
     * goods_id : 1
     * goods_sn : MC000001
     * goods_name : 爱的日志玫瑰水润蚕丝面膜
     * market_price : ¥5.90
     * goods_price : ¥3.90
     * goods_number : 5
     * goods_attr :
     * is_real : 1
     * extension_code :
     * parent_id : 0
     * rec_type : 0
     * is_gift : 0
     * is_shipping : 0
     * can_handsel : 0
     * goods_attr_id :
     * pid : 1
     * subtotal : ¥19.50
     * img_thumb : http://www.aiderizhi.com/images/201603/thumb_img/1_thumb_G_1458155685646.png
     */

    private List<ShopCarOrderInfo.DataEntity.GoodsListEntity> goods_list;

    public void setGoods_list(List<ShopCarOrderInfo.DataEntity.GoodsListEntity> goods_list) {
        this.goods_list = goods_list;
    }

    public List<ShopCarOrderInfo.DataEntity.GoodsListEntity> getGoods_list() {
        return goods_list;
    }


}
