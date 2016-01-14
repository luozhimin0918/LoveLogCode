package com.smarter.LoveLog.model.community;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/8.
 */
public class User implements Serializable {
    private String id;
    private String name;
    private String avatar;
    private String wxid;
    private String real_name;
    private String sex;
    private String mobile;
    private String email;
    private String mobile_validated;
    private String email_validated;
    private String rank_name;
    private String signature;
    private String collection_num;
    private String uc_notice;
    private int rank_level;
    private UserOrderNum  order_num;





    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile_validated() {
        return mobile_validated;
    }

    public void setMobile_validated(String mobile_validated) {
        this.mobile_validated = mobile_validated;
    }

    public String getEmail_validated() {
        return email_validated;
    }

    public void setEmail_validated(String email_validated) {
        this.email_validated = email_validated;
    }

    public String getRank_name() {
        return rank_name;
    }

    public void setRank_name(String rank_name) {
        this.rank_name = rank_name;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getCollection_num() {
        return collection_num;
    }

    public void setCollection_num(String collection_num) {
        this.collection_num = collection_num;
    }

    public String getUc_notice() {
        return uc_notice;
    }

    public void setUc_notice(String uc_notice) {
        this.uc_notice = uc_notice;
    }

    public int getRank_level() {
        return rank_level;
    }

    public void setRank_level(int rank_level) {
        this.rank_level = rank_level;
    }

    public UserOrderNum getOrder_num() {
        return order_num;
    }

    public void setOrder_num(UserOrderNum order_num) {
        this.order_num = order_num;
    }
}
