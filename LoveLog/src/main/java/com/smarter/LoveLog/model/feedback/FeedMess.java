package com.smarter.LoveLog.model.feedback;

import com.smarter.LoveLog.model.help.HelpDataList;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/1/8.
 */
public class FeedMess implements Serializable {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
