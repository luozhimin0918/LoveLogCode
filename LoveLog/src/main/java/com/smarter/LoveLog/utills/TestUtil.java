package com.smarter.LoveLog.utills;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.smarter.LoveLog.db.AppContextApplication;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/11.
 */
public class TestUtil {




    public static void VolleyGetRospone(final Map<String,String> maptop, final Map<String,String> map,int Methed,String url){
        RequestQueue mQueue = AppContextApplication.getInstance().getmRequestQueue();
        Log.d("TestUtil",""+"Response is: json        "+map.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Methed,
                url,
                null,
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
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return maptop;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(8000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        fastJsonCommunity.setTag(TAG);
        jsonObjectRequest.setShouldCache(true);
        mQueue.add(jsonObjectRequest);
    }
    public static byte[] getBitmapByte(Bitmap bitmap){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);


        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }



    public static Bitmap drawableToBitamp(Drawable drawable)
    {
         Bitmap bitmap;
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        System.out.println("Drawable转Bitmap");
        Bitmap.Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565;
        bitmap = Bitmap.createBitmap(w,h,config);
        //注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);

        return  bitmap;
    }


    public static String encodeBase64(byte[] input) throws Exception {
        Class clazz = Class
                .forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
        Method mainMethod = clazz.getMethod("encode", byte[].class);
        mainMethod.setAccessible(true);
        Object retObj = mainMethod.invoke(null, new Object[] { input });
        return (String) retObj;
    }


}
