package com.smarter.LoveLog.model.address;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/1/8.
 */
public class QuanProvinceData implements Serializable {

    private List<QuanShengAddressData> province;

    public List<QuanShengAddressData> getProvince() {
        return province;
    }

    public void setProvince(List<QuanShengAddressData> province) {
        this.province = province;
    }
}
