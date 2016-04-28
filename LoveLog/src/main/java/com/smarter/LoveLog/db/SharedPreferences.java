package com.smarter.LoveLog.db;

import android.content.Context;

import com.alibaba.fastjson.JSON;

/**
 * Created by Administrator on 2016/1/4.
 */
public class SharedPreferences {
//    SharedPreferences.getInstance().putBoolean("islogin", true);
//    SharedPreferences.getInstance().putString("session", JSON.toJSONString(loginDataActi.getSession()));
//    SharedPreferences.getInstance().putString("user",JSON.toJSONString(loginDataActi.getUser()));//用户信息
//    SharedPreferences.getInstance().getBoolean("first-time-use", true);
//    SharedPreferences.getInstance().putString("address-list", JSON.toJSONString(addressDataList));//地址管理
//    SharedPreferences.getInstance().putString("quanguo-list", JSON.toJSONString(shengList));


//    SharedPreferences.getInstance().putString("usename",user);
//    SharedPreferences.getInstance().putString("password",pass);
//    SharedPreferences.getInstance().putString("local_shop_car",pass);
    private static final String SP_NAME = "lovgLog";


    private static SharedPreferences instance = new SharedPreferences();

    public SharedPreferences() {
    }

    private static synchronized void syncInit() {
        if (instance == null) {
            instance = new SharedPreferences();
        }
    }

    public static SharedPreferences getInstance() {
        if (instance == null) {
            syncInit();
        }
        return instance;
    }

    private android.content.SharedPreferences getSp() {
        return AppContextApplication.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public int getInt(String key, int def) {
        try {
            android.content.SharedPreferences sp = getSp();
            if (sp != null)
                def = sp.getInt(key, def);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public void putInt(String key, int val) {
        try {
            android.content.SharedPreferences sp = getSp();
            if (sp != null) {
                android.content.SharedPreferences.Editor e = sp.edit();
                e.putInt(key, val);
                e.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getLong(String key, long def) {
        try {
            android.content.SharedPreferences sp = getSp();
            if (sp != null)
                def = sp.getLong(key, def);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public void putLong(String key, long val) {
        try {
            android.content.SharedPreferences sp = getSp();
            if (sp != null) {
                android.content.SharedPreferences.Editor e = sp.edit();
                e.putLong(key, val);
                e.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getString(String key, String def) {
        try {
            android.content.SharedPreferences sp = getSp();
            if (sp != null)
                def = sp.getString(key, def);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public void putString(String key, String val) {
        try {
            android.content.SharedPreferences sp = getSp();
            if (sp != null) {
                android.content.SharedPreferences.Editor e = sp.edit();
                e.putString(key, val);
                e.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getBoolean(String key, boolean def) {
        try {
            android.content.SharedPreferences sp = getSp();
            if (sp != null)
                def = sp.getBoolean(key, def);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public void putBoolean(String key, boolean val) {
        try {
            android.content.SharedPreferences sp = getSp();
            if (sp != null) {
                android.content.SharedPreferences.Editor e = sp.edit();
                e.putBoolean(key, val);
                e.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void remove(String key) {
        try {
            android.content.SharedPreferences sp = getSp();
            if (sp != null) {
                android.content.SharedPreferences.Editor e = sp.edit();
                e.remove(key);
                e.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

