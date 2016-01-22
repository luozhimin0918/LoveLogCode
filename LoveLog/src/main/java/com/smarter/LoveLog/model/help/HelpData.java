package com.smarter.LoveLog.model.help;

import com.smarter.LoveLog.model.community.UserOrderNum;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/1/8.
 */
public class HelpData implements Serializable {
    private String title;
    private List<HelpDataList> list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<HelpDataList> getList() {
        return list;
    }

    public void setList(List<HelpDataList> list) {
        this.list = list;
    }
}
