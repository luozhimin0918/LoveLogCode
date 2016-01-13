package com.smarter.LoveLog.model.community;

import com.smarter.LoveLog.model.home.Ad;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/1/8.
 */
public class PromotePostsData implements Serializable {
    private String id;
    private String title;
    private String url;
    private String share_url;
    private String cat_id;
    private String content;
    private String is_best;
    private String is_hot;
    private String audit;
    private String brief;
    private String cat_name;
    private String cat_url;
    private String like_count;
    private String reward_count;
    private String click_count;
    private String cmt_count;

    private String is_like;
    private String add_time;

    private List<Pinglun> cmt;
    private User    user;
    private Img    img;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
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

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Img getImg() {
        return img;
    }

    public void setImg(Img img) {
        this.img = img;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_url() {
        return cat_url;
    }

    public void setCat_url(String cat_url) {
        this.cat_url = cat_url;
    }

    public String getReward_count() {
        return reward_count;
    }

    public void setReward_count(String reward_count) {
        this.reward_count = reward_count;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIs_best() {
        return is_best;
    }

    public void setIs_best(String is_best) {
        this.is_best = is_best;
    }

    public String getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(String is_hot) {
        this.is_hot = is_hot;
    }

    public String getAudit() {
        return audit;
    }

    public void setAudit(String audit) {
        this.audit = audit;
    }

    public List<Pinglun> getCmt() {
        return cmt;
    }

    public void setCmt(List<Pinglun> cmt) {
        this.cmt = cmt;
    }
}
