package com.smarter.LoveLog.utills;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.smarter.LoveLog.db.AppContextApplication;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/11.
 */
public class TestUtil {




    public static void VolleyGetRospone(Map<String,String> map,int Methed,String url){
        RequestQueue mQueue = AppContextApplication.getInstance().getmRequestQueue();
        JSONObject jsonObject=new JSONObject(map);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Methed,
                url,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TestUtil", "" + "Response is: " + response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError arg0) {
                        Log.d("TestUtil",""+"Response is:  error");
                        System.out.println("sorry,Error");
                    }
                });
        mQueue.add(jsonObjectRequest);
    }


}
