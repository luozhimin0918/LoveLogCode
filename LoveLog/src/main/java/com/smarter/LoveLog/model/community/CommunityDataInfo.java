package com.smarter.LoveLog.model.community;

import com.smarter.LoveLog.model.home.AdIndexUrlData;
import com.smarter.LoveLog.model.home.NavIndexUrlData;
import com.smarter.LoveLog.model.home.SliderUrlData;

import java.util.List;

/**
 * Created by Administrator on 2016/1/8.
 */
public class CommunityDataInfo {
   private List<SliderUrlData> slider;
    private List<NavIndexUrlData> nav;
    private List<PromotePostsData> promote_posts;//communityDataFrag有的参数，HomeDataFrag没有的参数


    public List<SliderUrlData> getSlider() {
        return slider;
    }

    public void setSlider(List<SliderUrlData> slider) {
        this.slider = slider;
    }

    public List<NavIndexUrlData> getNav() {
        return nav;
    }

    public void setNav(List<NavIndexUrlData> nav) {
        this.nav = nav;
    }


    public List<PromotePostsData> getPromote_posts() {
        return promote_posts;
    }

    public void setPromote_posts(List<PromotePostsData> promote_posts) {
        this.promote_posts = promote_posts;
    }

}
