package com.smarter.LoveLog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bigkoo.pickerview.OptionsPickerView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.address.AddressData;
import com.smarter.LoveLog.model.address.AddressDataInfo;
import com.smarter.LoveLog.model.address.QuanGuoAddressDataInfo;
import com.smarter.LoveLog.model.address.QuanQuOrXianAddressData;
import com.smarter.LoveLog.model.address.QuanShengAddressData;
import com.smarter.LoveLog.model.address.QuanShiAddressData;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.home.DataStatusOne;
import com.smarter.LoveLog.model.jsonModel.AddAdreParam;
import com.smarter.LoveLog.model.loginData.SessionData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class CreateAddressActivity extends BaseFragmentActivity implements View.OnClickListener{
    String Tag= "CreateAddressActivity";
    @Bind(R.id.name)
    EditText name;
    @Bind(R.id.phone)
    EditText phone;
    @Bind(R.id.addresText)
    EditText addresText;
    @Bind(R.id.youNum)
    EditText youNum;
    @Bind(R.id.addAddressText)
    TextView addAddressText;


    @Bind(R.id.vMasker)
    LinearLayout vMasker;

    @Bind(R.id.addressSelect)
    LinearLayout addressSelect;

    @Bind(R.id.savaAddress)
    LinearLayout savaAddress;


    @Bind(R.id.tv_right_title)
    TextView tv_right_title;

    @Bind(R.id.backBUt)
    ImageView backBUt;
    @Bind(R.id.tv_top_title)
    TextView tv_top_title;






    OptionsPickerView pvOptions;
    private ArrayList<QuanShengAddressData> options1Items = new ArrayList<QuanShengAddressData>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();

    String  isCreatOrUpdate;//是否是修改地址？创建地址



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_address_view);
        ButterKnife.bind(this);



        getDataIntent();
        intData();
        setListen();

    }

    private void setListen() {
        addressSelect.setOnClickListener(this);
        savaAddress.setOnClickListener(this);
        tv_right_title.setOnClickListener(this);
        backBUt.setOnClickListener(this);
    }

    private void intData() {
        networkShengshiquInfo("", "");

    }

    AddressData addressData;
    private void getDataIntent() {
        Intent intent = getIntent();
        if(intent!=null){
            isCreatOrUpdate = intent.getStringExtra("xiugaiAddress");
            addressData= (AddressData) intent.getSerializableExtra("AddressData");
            if(isCreatOrUpdate!=null&&!isCreatOrUpdate.equals("")){
                tv_top_title.setText(isCreatOrUpdate);
            }

            Toast.makeText(this,isCreatOrUpdate+""+addressData.getCountry_name(),Toast.LENGTH_LONG).show();
        }


    }


    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case  R.id.addressSelect:

                 if(shengList!=null&& shengList.size()>0){
                     pvOptions.show();
                 }else {
                     Toast.makeText(this,"省市数据没成功请求下来",Toast.LENGTH_SHORT).show();
                 }

                 break;
             case  R.id.savaAddress:
             case  R.id.tv_right_title:
                 saveAddress();

                 break;
             case R.id.backBUt:
                 finish();
                 break;

         }
    }

    private void saveAddress() {
        Boolean isLogin =SharedPreferences.getInstance().getBoolean("islogin", false);
        if(isLogin){
            String  sessionString=SharedPreferences.getInstance().getString("session","");
            SessionData sessionData = JSON.parseObject(sessionString,SessionData.class);
            if(sessionData!=null){


                AddAdreParam  param = new AddAdreParam();
                //session
                    SessionData  sessionData1=new SessionData();
                      sessionData1.setSid(sessionData.getSid());
                      sessionData1.setUid(sessionData.getUid());
                //putong
//                 province,city,district,adressAll;
                  param.setName(name.getText().toString());
                  param.setMobile(phone.getText().toString());
                  param.setCountry(1 + "");
                  param.setProvince(province);
                  param.setCountry(city);
                  param.setDistrict(district);
                 param.setAddress(adressAll + addresText.getText().toString());
                 param.setZipcode(youNum.getText().toString());
                 param.setSession(sessionData1);




                if(!name.getText().toString().equals("")&&!phone.getText().toString().equals("")&&
                        !addAddressText.getText().equals("")&&!addresText.getText().toString().equals("")&&!youNum.getText().toString().equals("")){
                    networkAddAddressInfo(JSON.toJSONString(param));
                }else{
                    Toast.makeText(this,"请完善地址信息",Toast.LENGTH_SHORT).show();
                }


                Log.d("CreateAddressActivity","  Session  "+ sessionData.getUid() + "      "+sessionData.getSid());
            }

        }
    }


    List<QuanShengAddressData> shengList;
    private void networkShengshiquInfo(String uid,String sid) {
        String url = "http://mapp.aiderizhi.com/?url=/region";//
        Map<String, String> map = new HashMap<String, String>();
        String  sessinStr ="{\"parent_id\":\"1\"}";
        map.put("json", sessinStr);




        Log.d("CreateAddressActivity", sessinStr + "      ");


        FastJsonRequest<QuanGuoAddressDataInfo> fastJsonCommunity = new FastJsonRequest<QuanGuoAddressDataInfo>(Request.Method.POST, url, QuanGuoAddressDataInfo.class, null, new Response.Listener<QuanGuoAddressDataInfo>() {
            @Override
            public void onResponse(QuanGuoAddressDataInfo addressDataInfo) {

                DataStatus status = addressDataInfo.getStatus();
                if (status.getSucceed() == 1) {
                    shengList = addressDataInfo.getData().getProvince();
                    if(shengList!=null&&shengList.size()>0){

                        initpickerView();

                        SharedPreferences.getInstance().putString("quanguo-list", JSON.toJSONString(shengList));
                        Log.d("CreateAddressActivity", "全国省市区信息：   " + JSON.toJSONString(shengList)+ "++++succeed");
                    }


                } else {

                    // 请求失败
                    Log.d("CreateAddressActivity", "succeded=00000  " + JSON.toJSONString(status) + "");
                    Toast.makeText(getApplicationContext(), "" + status.getError_desc(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("CreateAddressActivity", "errror" + volleyError.toString() + "");
            }
        });
        fastJsonCommunity.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //fastJsonCommunity.setTag(TAG);
        fastJsonCommunity.setParams(map);
        fastJsonCommunity.setShouldCache(true);
        mQueue.add(fastJsonCommunity);
    }







    String province,city,district,adressAll;
    private void initpickerView() {
        //选项选择器
        pvOptions = new OptionsPickerView(this);

        //选项1
        if(shengList!=null&& shengList.size()>0){
            for(int i=0;i<shengList.size();i++){
                options1Items.add(shengList.get(i));
                List<QuanShiAddressData>  shiList=shengList.get(i).getCity();

                ArrayList<String> options2Items_01=new ArrayList<String>();
                ArrayList<ArrayList<String>> options3Items_01 = new ArrayList<ArrayList<String>>();
                for(int j=0;j<shiList.size();j++){

                    options2Items_01.add(shiList.get(j).getName());

                    List<QuanQuOrXianAddressData> qulist=shiList.get(j).getDistrict();

                    ArrayList<String> options3Items_01_01=new ArrayList<String>();
                    for(int k=0;k<qulist.size();k++){

                        options3Items_01_01.add(qulist.get(k).getName());
                    }
                    options3Items_01.add(options3Items_01_01);



                }
                options3Items.add(options3Items_01);
                options2Items.add(options2Items_01);
            }
        }



        //三级联动效果
        pvOptions.setPicker(options1Items, options2Items, options3Items, true);
        //设置选择的三级单位
//        pwOptions.setLabels("省", "市", "区");
        pvOptions.setTitle("");
        pvOptions.setCyclic(false, true, true);
        //设置默认选中的三级项目
        //监听确定选择按钮
        pvOptions.setSelectOptions(1, 1, 1);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String sheng=options1Items.get(options1).getPickerViewText();
                String shi=options2Items.get(options1).get(option2);
                String qu=options3Items.get(options1).get(option2).get(options3);
                String tx = sheng+"省"+ shi+"市"+ qu+"(县/区)";

                for(int i=0;i<shengList.size();i++){
                       if(shengList.get(i).getName().equals(sheng)){
                           province=shengList.get(i).getId();
                           List<QuanShiAddressData> shilist=shengList.get(i).getCity();
                           for(int j=0;j<shilist.size();j++){
                                if(shilist.get(j).getName().equals(shi)){
                                    city=shilist.get(j).getId();
                                    List<QuanQuOrXianAddressData> quList=shilist.get(j).getDistrict();
                                       for (int k=0;k<quList.size();k++){
                                            if(quList.get(k).getName().equals(qu)){
                                                district=quList.get(k).getId();
                                            }
                                       }


                                }
                           }
                       }

                }

                adressAll=tx;

                addAddressText.setText(tx);
                vMasker.setVisibility(View.GONE);
            }
        });


    }








    private void networkAddAddressInfo(String  param) {
        String url = "http://mapp.aiderizhi.com/?url=/address/add";//
        Map<String, String> map = new HashMap<String, String>();
        map.put("json", param);
        Log.d("CreateAddressActivity", param + "      ");


        FastJsonRequest<DataStatusOne> fastJsonCommunity = new FastJsonRequest<DataStatusOne>(Request.Method.POST, url, DataStatusOne.class, null, new Response.Listener<DataStatusOne>() {
            @Override
            public void onResponse(DataStatusOne dataStatusOne) {
                  DataStatus  dataStatus=dataStatusOne.getStatus();
                if (dataStatus.getSucceed() == 1) {




                    Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
                    finish();
                        Log.d("CreateAddressActivity", "保存返回的信息：   " + JSON.toJSONString(dataStatus)+ "++++succeed");



                } else {

                    // 请求失败
                    Log.d("CreateAddressActivity", "succeded=00000  " + JSON.toJSONString(dataStatus) + "");
                    Toast.makeText(getApplicationContext(), "" + dataStatus.getError_desc(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("CreateAddressActivity", "errror" + volleyError.toString() + "");
            }
        });
        fastJsonCommunity.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //fastJsonCommunity.setTag(TAG);
        fastJsonCommunity.setParams(map);
        fastJsonCommunity.setShouldCache(true);
        mQueue.add(fastJsonCommunity);
    }

}
