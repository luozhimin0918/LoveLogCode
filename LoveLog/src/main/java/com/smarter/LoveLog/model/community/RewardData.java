package com.smarter.LoveLog.model.community;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/8.
 */
public class RewardData implements Serializable {
    private String id;
    private String rewarded;
    private String reward_count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRewarded() {
        return rewarded;
    }

    public void setRewarded(String rewarded) {
        this.rewarded = rewarded;
    }

    public String getReward_count() {
        return reward_count;
    }

    public void setReward_count(String reward_count) {
        this.reward_count = reward_count;
    }
}
