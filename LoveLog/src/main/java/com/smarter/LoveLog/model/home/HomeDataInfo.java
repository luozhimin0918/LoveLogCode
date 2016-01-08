package com.smarter.LoveLog.model.home;

import java.util.List;

/**
 * Created by Administrator on 2016/1/8.
 */
public class HomeDataInfo {
   private List<SliderUrlData> slider;
    private List<NavIndexUrlData> nav;
    private List<AdIndexUrlData> ad;

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

    public List<AdIndexUrlData> getAd() {
        return ad;
    }

    public void setAd(List<AdIndexUrlData> ad) {
        this.ad = ad;
    }
}
