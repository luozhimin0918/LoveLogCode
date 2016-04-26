package com.smarter.LoveLog.db;

import com.alibaba.fastjson.JSON;
import com.smarter.LoveLog.model.loginData.SessionData;

/**
 * Created by Administrator on 2016/4/26.
 */
public class SharedPreUtil {
    /**
     * 是否登录
     * @return
     */
    public static boolean  isLogin(){
      return   SharedPreferences.getInstance().getBoolean("islogin", false);
    }

    /**
     * 登录sessionData
     * @return
     */
    public static SessionData  LoginSessionData(){
        String sessionString = SharedPreferences.getInstance().getString("session", "");
        SessionData  sessionData = JSON.parseObject(sessionString, SessionData.class);
        return  sessionData;
    }

}
