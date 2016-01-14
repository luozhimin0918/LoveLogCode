package com.smarter.LoveLog.model.community;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/8.
 */
public class UserOrderNum implements Serializable {
    private String await_pay;
    private String await_ship;
    private String shipped;
    private String finished;

    public String getAwait_pay() {
        return await_pay;
    }

    public void setAwait_pay(String await_pay) {
        this.await_pay = await_pay;
    }

    public String getAwait_ship() {
        return await_ship;
    }

    public void setAwait_ship(String await_ship) {
        this.await_ship = await_ship;
    }

    public String getShipped() {
        return shipped;
    }

    public void setShipped(String shipped) {
        this.shipped = shipped;
    }

    public String getFinished() {
        return finished;
    }

    public void setFinished(String finished) {
        this.finished = finished;
    }
}
