package com.smarter.LoveLog.model.itegralself;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/8.
 */
public class ItegralDataList implements Serializable {
    private String log_id;
    private String prefix;
    private String value;
    private String change_time;
    private String change_desc;

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getChange_time() {
        return change_time;
    }

    public void setChange_time(String change_time) {
        this.change_time = change_time;
    }

    public String getChange_desc() {
        return change_desc;
    }

    public void setChange_desc(String change_desc) {
        this.change_desc = change_desc;
    }
}
