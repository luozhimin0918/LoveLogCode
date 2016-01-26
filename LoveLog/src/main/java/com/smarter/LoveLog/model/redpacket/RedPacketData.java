package com.smarter.LoveLog.model.redpacket;

import com.smarter.LoveLog.model.community.Pinglun;
import com.smarter.LoveLog.model.community.User;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/1/8.
 */
public class RedPacketData implements Serializable {
  private CountRed count;
    private List<RedList> list;

    public CountRed getCount() {
        return count;
    }

    public void setCount(CountRed count) {
        this.count = count;
    }

    public List<RedList> getList() {
        return list;
    }

    public void setList(List<RedList> list) {
        this.list = list;
    }
}
