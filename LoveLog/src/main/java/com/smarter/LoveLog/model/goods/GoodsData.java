package com.smarter.LoveLog.model.goods;

import com.smarter.LoveLog.model.community.Img;
import com.smarter.LoveLog.model.help.HelpDataList;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/1/8.
 */
public class GoodsData implements Serializable {
    private String id;
    private String cat_id;
    private String goods_sn;
    private String goods_name;
    private String goods_brief;
    private String market_price;
    private String shop_price;
    private int integral;
    private String brand_id;
    private String goods_number;

    private String goods_weight;

    private String is_shipping;
    private int collect_count;
    private String click_count;
    private String cmt_count;
    private String is_like;
    private String is_collect;




    private List<CmtGoods> cmt;
    private List<RankPrices> rank_prices;
    private List<Pictures> pictures;
    private List<Properties> properties;
    private List<Specification> specification;
    private Promote promote;
    private Img img;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
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

    public String getGoods_brief() {
        return goods_brief;
    }

    public void setGoods_brief(String goods_brief) {
        this.goods_brief = goods_brief;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getShop_price() {
        return shop_price;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getGoods_number() {
        return goods_number;
    }

    public void setGoods_number(String goods_number) {
        this.goods_number = goods_number;
    }

    public String getGoods_weight() {
        return goods_weight;
    }

    public void setGoods_weight(String goods_weight) {
        this.goods_weight = goods_weight;
    }

    public String getIs_shipping() {
        return is_shipping;
    }

    public void setIs_shipping(String is_shipping) {
        this.is_shipping = is_shipping;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public String getClick_count() {
        return click_count;
    }

    public void setClick_count(String click_count) {
        this.click_count = click_count;
    }

    public String getCmt_count() {
        return cmt_count;
    }

    public void setCmt_count(String cmt_count) {
        this.cmt_count = cmt_count;
    }

    public String getIs_like() {
        return is_like;
    }

    public void setIs_like(String is_like) {
        this.is_like = is_like;
    }

    public String getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(String is_collect) {
        this.is_collect = is_collect;
    }

    public List<CmtGoods> getCmt() {
        return cmt;
    }

    public void setCmt(List<CmtGoods> cmt) {
        this.cmt = cmt;
    }

    public List<RankPrices> getRank_prices() {
        return rank_prices;
    }

    public void setRank_prices(List<RankPrices> rank_prices) {
        this.rank_prices = rank_prices;
    }

    public List<Pictures> getPictures() {
        return pictures;
    }

    public void setPictures(List<Pictures> pictures) {
        this.pictures = pictures;
    }

    public List<Properties> getProperties() {
        return properties;
    }

    public void setProperties(List<Properties> properties) {
        this.properties = properties;
    }

    public List<Specification> getSpecification() {
        return specification;
    }

    public void setSpecification(List<Specification> specification) {
        this.specification = specification;
    }

    public Promote getPromote() {
        return promote;
    }

    public void setPromote(Promote promote) {
        this.promote = promote;
    }

    public Img getImg() {
        return img;
    }

    public void setImg(Img img) {
        this.img = img;
    }
}
