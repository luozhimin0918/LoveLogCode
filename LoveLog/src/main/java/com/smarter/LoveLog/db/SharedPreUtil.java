package com.smarter.LoveLog.db;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.smarter.LoveLog.model.loginData.SessionData;
import com.smarter.LoveLog.model.orderMy.LocalShopCarData;
import com.smarter.LoveLog.model.orderMy.ShopCarOrderInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/26.
 */
public class SharedPreUtil {
    /**
     * 是否登录
     * @return
     */
    public static boolean  isLogin(){
      return   SharedPreferences.getInstance().getBoolean("islogin", false);
    }

    /**
     * 登录sessionData
     * @return
     */
    public static SessionData  LoginSessionData(){
        String sessionString = SharedPreferences.getInstance().getString("session", "");
        SessionData  sessionData = JSON.parseObject(sessionString, SessionData.class);
        return  sessionData;
    }


    /**
     * 保存本地购物车数据
     * @return
     */
    public static void   saveLocalShopCarData(ShopCarOrderInfo.DataEntity.GoodsListEntity localData){
        String shop_carString = SharedPreferences.getInstance().getString("local_shop_car", "");
        if(shop_carString!=null&&!shop_carString.equals("")){
            LocalShopCarData localShopCarData = JSON.parseObject(shop_carString, LocalShopCarData.class);
            List<ShopCarOrderInfo.DataEntity.GoodsListEntity> goodsListEntity =  new ArrayList<ShopCarOrderInfo.DataEntity.GoodsListEntity>();
            goodsListEntity.addAll(localShopCarData.getGoods_list());



            LocalShopCarData localShopCarDataTemp =new LocalShopCarData();
             List<ShopCarOrderInfo.DataEntity.GoodsListEntity> goodsListEntityTemp =  new ArrayList<ShopCarOrderInfo.DataEntity.GoodsListEntity>();

            boolean  isAdd =false;
            ShopCarOrderInfo.DataEntity.GoodsListEntity tempGoods=new ShopCarOrderInfo.DataEntity.GoodsListEntity();
            for(int i=0;i<goodsListEntity.size();i++){
                if(localData.getGoods_id().equals(goodsListEntity.get(i).getGoods_id())){
                    tempGoods=goodsListEntity.get(i);
                    isAdd=true;
                }
            }
            if(isAdd){
                int  dd =Integer.parseInt(tempGoods.getGoods_number());
                int  kk =Integer.parseInt(localData.getGoods_number());

                for(int i=0;i<goodsListEntity.size();i++){
                    if(goodsListEntity.get(i).getGoods_id().equals(tempGoods.getGoods_id())){

                        goodsListEntity.get(i).setGoods_number((dd+kk)+"");
                    }
                }

                SharedPreferences.getInstance().putString("local_shop_car", JSON.toJSONString(localShopCarData));
            }else{

                        for(int g=0;g<goodsListEntity.size();g++){
                            goodsListEntityTemp.add(goodsListEntity.get(g));
                        }
                       goodsListEntityTemp.add(localData);
                      localShopCarDataTemp.setGoods_list(goodsListEntityTemp);

                SharedPreferences.getInstance().putString("local_shop_car", JSON.toJSONString(localShopCarDataTemp));
//                goodsListEntity.addAll(goodsListEntityTemp);
//                Log.d("nnnnnn","    ");
//                goodsListEntityTemp.add(localData);
//                localShopCarData.setGoods_list(goodsListEntityTemp);


            }






        }else{
            LocalShopCarData localShopCarData2=new LocalShopCarData();
            List<ShopCarOrderInfo.DataEntity.GoodsListEntity> goodsListEntity2=new ArrayList<ShopCarOrderInfo.DataEntity.GoodsListEntity>();
            goodsListEntity2.add(localData);
            localShopCarData2.setGoods_list(goodsListEntity2);

            SharedPreferences.getInstance().putString("local_shop_car", JSON.toJSONString(localShopCarData2));
        }

    }


    /**
     * 获取本地购物车数据
     * @return
     */
    public static List<ShopCarOrderInfo.DataEntity.GoodsListEntity>    getLocalShopCarData() {
        LocalShopCarData localShopCarData=new LocalShopCarData();
        String  strLocal = SharedPreferences.getInstance().getString("local_shop_car", "");
        if(strLocal!=null&&!strLocal.equals("")){
            localShopCarData = JSON.parseObject(strLocal, LocalShopCarData.class);
            return  localShopCarData.getGoods_list();

        }else{
            return new ArrayList<ShopCarOrderInfo.DataEntity.GoodsListEntity>();
        }


    }
    /**
     * 删除本地购物车数据
     * @return
     */
    public static void    deleteLocalShopCarData(ShopCarOrderInfo.DataEntity.GoodsListEntity deleteGoods) {
        LocalShopCarData localShopCarData=new LocalShopCarData();
        String  strLocal = SharedPreferences.getInstance().getString("local_shop_car", "");
        if(strLocal!=null&&!strLocal.equals("")){
            localShopCarData = JSON.parseObject(strLocal, LocalShopCarData.class);
            List<ShopCarOrderInfo.DataEntity.GoodsListEntity> goodsListEntity =  localShopCarData.getGoods_list();
            for(int i=0;i<goodsListEntity.size();i++){
                if(deleteGoods.getGoods_id().equals(goodsListEntity.get(i).getGoods_id())){

                    goodsListEntity.remove(i);

                }
            }

            SharedPreferences.getInstance().putString("local_shop_car", JSON.toJSONString(localShopCarData));

        }

    }



    /**
     * 修改本地购物车数据
     * @return
     */
    public static void   updateLocalShopCarData(ShopCarOrderInfo.DataEntity.GoodsListEntity localData){
        String shop_carString = SharedPreferences.getInstance().getString("local_shop_car", "");
        if(shop_carString!=null&&!shop_carString.equals("")) {
            LocalShopCarData localShopCarData = JSON.parseObject(shop_carString, LocalShopCarData.class);
            List<ShopCarOrderInfo.DataEntity.GoodsListEntity> goodsListEntity = new ArrayList<ShopCarOrderInfo.DataEntity.GoodsListEntity>();
            goodsListEntity.addAll(localShopCarData.getGoods_list());

            for(int i=0;i<goodsListEntity.size();i++){
                if(localData.getGoods_id().equals(goodsListEntity.get(i).getGoods_id())){
                    goodsListEntity.get(i).setGoods_number(localData.getGoods_number());
                }
            }
            SharedPreferences.getInstance().putString("local_shop_car", JSON.toJSONString(localShopCarData));

        }
    }


}
