package com.smarter.LoveLog.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.smarter.LoveLog.model.address.QuanProvinceData;
import com.smarter.LoveLog.model.address.QuanQuOrXianAddressData;
import com.smarter.LoveLog.model.address.QuanShengAddressData;
import com.smarter.LoveLog.model.address.QuanShiAddressData;
import com.smarter.LoveLog.model.community.User;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.home.DataStatusOne;
import com.smarter.LoveLog.model.jsonModel.AddAdreParam;
import com.smarter.LoveLog.model.jsonModel.AddAdreParamInfo;
import com.smarter.LoveLog.model.jsonModel.PersonParamInfo;
import com.smarter.LoveLog.model.jsonModel.StatusJsonInfo;
import com.smarter.LoveLog.model.loginData.LoginDataActi;
import com.smarter.LoveLog.model.loginData.LoginDataInfo;
import com.smarter.LoveLog.model.loginData.PersonalDataInfo;
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


    @Bind(R.id.tv_top_title)
    TextView tv_top_title;
    @Bind(R.id.tv_right_title)
    TextView tv_right_title;
    @Bind(R.id.backBUt)
    ImageView backBUt;




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
    @Bind(R.id.addressSelect)
    LinearLayout addressSelect;
    @Bind(R.id.ShouHuoAddressLinear)
    LinearLayout ShouHuoAddressLinear;//创建/修改收货地址布局




    @Bind(R.id.vMasker)
    LinearLayout vMasker;


    @Bind(R.id.savaAddress)
    LinearLayout savaAddress;




    @Bind(R.id.newName)
    EditText newName;//新用户名
    @Bind(R.id.UserLinear)
    LinearLayout UserLinear;//新用户名布局



    @Bind(R.id.group)
    RadioGroup group;//新用户名
    @Bind(R.id.baomi)
    RadioButton baomi;//保密
    @Bind(R.id.nan)
    RadioButton nan;//男
    @Bind(R.id.nv)
    RadioButton nv;//女


    @Bind(R.id.oldPassword)
    EditText oldPassword;//旧密码
    @Bind(R.id.newPassword)
    EditText newPassword;//新密码
    @Bind(R.id.anotherNewPassword)
    EditText anotherNewPassword;//再次输入新密码

    @Bind(R.id.PassLinear)
    LinearLayout PassLinear;//密码布局





    OptionsPickerView pvOptions;
    private ArrayList<QuanShengAddressData> options1Items = new ArrayList<QuanShengAddressData>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_address_view);
        ButterKnife.bind(this);



        getDataIntent();
//        intData();
        setListen();

    }

    private void setListen() {
        addressSelect.setOnClickListener(this);
        savaAddress.setOnClickListener(this);
        tv_right_title.setOnClickListener(this);
        backBUt.setOnClickListener(this);



        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                   case R.id.baomi:
                       sexStr="0";
                        break;
                    case R.id.nan:
                        sexStr="1";
                        break;
                    case R.id.nv:
                        sexStr="2";
                        break;


                }

            }
        });
    }

    private void intData() {
        networkShengshiquInfo("", "");

    }



    AddressData addressData;//回填数据所有
    String  isCreatOrUpdate;//是否是修改地址？创建地址


    Boolean  isEditUserName;//是否是修改用户名/昵称
    Boolean isCreateAddressTag;//是否是创建收货地址
    Boolean  isEditSex;//是否是修改性别
    String  sexStr;//性别str
    Boolean  isEditPassword;//是否是修改密码
    private void getDataIntent() {
        /**
         * 省市区数据有无判断处理 （创建收货地址和修改收货地址用到）
         */
        //   SharedPreferences.getInstance().putString("quanguo-list", JSON.toJSONString(addressDataInfo.getData()));
        String quanPro=SharedPreferences.getInstance().getString("quanguo-list", "");

        if(quanPro!=null&&!quanPro.equals("")){

            QuanProvinceData quanProvinceData= null;
            try {
                quanProvinceData = JSON.parseObject(quanPro, QuanProvinceData.class);
                if(quanProvinceData.getProvince()!=null&&quanProvinceData.getProvince().size()>0){
                    shengList=quanProvinceData.getProvince();
                    initpickerView();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else{
            intData();
        }





        Intent intent = getIntent();
        if(intent!=null){
//            xiugaiPassword
            /**
             * 修改密码
             */
             isEditPassword =  intent.getBooleanExtra("xiugaiPassword", false);
            if( isEditPassword){
                PassLinear.setVisibility(View.VISIBLE);
                ShouHuoAddressLinear.setVisibility(View.GONE);
                tv_top_title.setText("修改密码");
            }

            /**
             * 修改性别
             */
             isEditSex = intent.getBooleanExtra("xiugaiSex",false);
             if(isEditSex){
                  sexStr =intent.getStringExtra("sexValue");

                 group.setVisibility(View.VISIBLE);
                 ShouHuoAddressLinear.setVisibility(View.GONE);
                 tv_top_title.setText("修改性别");

                   if(sexStr.equals("男")){
                         nan.setChecked(true);
                       sexStr="1";
                   }else if(sexStr.equals("女")){
                       nv.setChecked(true);
                       sexStr="2";
                   }else{
                       baomi.setChecked(true);
                       sexStr="0";
                   }





             }

            /**
             * 修改用户名/昵称
             */
            isEditUserName=intent.getBooleanExtra("xiugaiName", false);

            if(isEditUserName){
                UserLinear.setVisibility(View.VISIBLE);
                ShouHuoAddressLinear.setVisibility(View.GONE);
                tv_top_title.setText("修改用户名/昵称");
            }

            /**
             * 创建收货地址
             */
//            isCreateAddress
            isCreateAddressTag=intent.getBooleanExtra("isCreateAddress",false);


            /**
             * 修改地址
             */
            isCreatOrUpdate = intent.getStringExtra("xiugaiAddress");
            addressData= (AddressData) intent.getSerializableExtra("AddressData");

            if(isCreatOrUpdate!=null&&!isCreatOrUpdate.equals("")&&addressData!=null){


                tv_top_title.setText(isCreatOrUpdate);//topBar标题

                        //地区字符转换
                        String addText;
                        if(addressData.getProvince_name().equals(addressData.getCity_name())){
                            addText=addressData.getProvince_name()+"市"+addressData.getDistrict_name();
                            addAddressText.setText(addText);//所在地区
                        }else{
                            addText=addressData.getProvince_name()+"省"+addressData.getCity_name()+"市"+addressData.getDistrict_name();
                            addAddressText.setText(addText);//所在地区
                        }
                adressAll=addText;//全部地址
                addresText.setText(addressData.getAddress().replace(addText,"").trim());//详细地址

                name.setText(addressData.getConsignee());//姓名
                phone.setText(addressData.getMobile());//电话



            }


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


                //session 公用的
                    SessionData  sessionData1=new SessionData();
                      sessionData1.setSid(sessionData.getSid());
                      sessionData1.setUid(sessionData.getUid());




                String url = "";//
                String paramStr="";//所有json字符参数


                if(isEditPassword){
                    String oldPass =oldPassword.getText().toString();
                    String  newPass=newPassword.getText().toString();
                    String  anothPass=anotherNewPassword.getText().toString();
                    PersonParamInfo paramInfo=new PersonParamInfo();
                    paramInfo.setSession(sessionData1);
                    paramInfo.setOld_password(oldPass);
                    paramInfo.setNew_password(newPass);
                    paramInfo.setAction("change_pwd");
                    String  param= JSON.toJSONString(paramInfo);

                   if(oldPass.length()>=6&&newPass.length()>=6&&anothPass.length()>=6){
                       if(anothPass.equals(newPass)){

                           networkEditPassWordInfo(param);

                       }else{
                           Toast.makeText(this,"两次密码不一致",Toast.LENGTH_SHORT).show();
                       }
                   }else{
                       Toast.makeText(this,"密码长度不能小于6个字符",Toast.LENGTH_SHORT).show();
                   }

                }
                /**
                 * 修改性别
                 */
                    if(isEditSex){
                        PersonParamInfo paramInfo=new PersonParamInfo();
                        paramInfo.setSession(sessionData1);
                        paramInfo.setSex(sexStr);
                        paramInfo.setAction("sex");
                        String  param= JSON.toJSONString(paramInfo);


                            networkEditPersonInfo(param);

                    }

                /**
                 * 修改用户名/昵称的参数
                 */
                 if(isEditUserName){
                     PersonParamInfo paramInfo=new PersonParamInfo();
                     paramInfo.setSession(sessionData1);
                     paramInfo.setUser_name(newName.getText().toString());
                     paramInfo.setAction("user_name");
                     String  param= JSON.toJSONString(paramInfo);

                     if(newName.getText().toString()!=null&&!newName.getText().toString().equals("")){
                           networkEditPersonInfo(param);
                     }else{
                         Toast.makeText(this,"请填写新用户名",Toast.LENGTH_SHORT).show();
                     }

                 }


                /**
                 * 创建收货地址的参数
                 */
                if(isCreateAddressTag){
                    AddAdreParamInfo adInfo=new AddAdreParamInfo();
                    adInfo.setSession(sessionData1);
                    AddAdreParam  param = new AddAdreParam();
                    //putong
//                 province,city,district,adressAll;
                    param.setConsignee(name.getText().toString());
                    param.setMobile(phone.getText().toString());
                    param.setCountry(1 + "");
                    param.setProvince(province);
                    param.setCity(city);
                    param.setDistrict(district);
                    param.setAddress(adressAll + addresText.getText().toString());
                    param.setZipcode(youNum.getText().toString());
                    param.setBest_time("");
                    param.setEmail("");
                    param.setId("");
                    param.setIs_default("");
                    param.setSign_building("");
                    param.setTel(phone.getText().toString());
                    adInfo.setAddress(param);
                  paramStr=JSON.toJSONString(adInfo);
                    url = "http://mapp.aiderizhi.com/?url=/address/add";//

                    if(!name.getText().toString().equals("")&&!phone.getText().toString().equals("")&&
                            !addAddressText.getText().equals("")&&!addresText.getText().toString().equals("")){
                        networkAddAddressInfo(url,paramStr);
                    }else{
                        Toast.makeText(this,"请完善地址信息",Toast.LENGTH_SHORT).show();
                    }

                }
                /**
                 * 修改收货地址的参数
                 */
                if(addressData!=null&&isCreatOrUpdate.equals("修改收货地址")){
                    AddAdreParamInfo adInfo=new AddAdreParamInfo();
                    adInfo.setSession(sessionData1);
                    AddAdreParam  param = new AddAdreParam();
                    //putong
//                 province,city,district,adressAll;
                    param.setConsignee(name.getText().toString());
                    param.setMobile(phone.getText().toString());
                    param.setCountry(1 + "");
                    param.setProvince(province);
                    param.setCity(city);
                    param.setDistrict(district);
                    param.setAddress(adressAll + addresText.getText().toString());
                    param.setZipcode(youNum.getText().toString());
                    param.setBest_time("");
                    param.setEmail("");
                    param.setId("");
                    param.setIs_default("");
                    param.setSign_building("");
                    param.setTel(phone.getText().toString());
                    adInfo.setAddress(param);
                    adInfo.setId(addressData.getId());
                    param.setId(addressData.getId());
                    url = "http://mapp.aiderizhi.com/?url=/address/update";//
                    paramStr=JSON.toJSONString(adInfo);

                    if(!name.getText().toString().equals("")&&!phone.getText().toString().equals("")&&
                            !addAddressText.getText().equals("")&&!addresText.getText().toString().equals("")){
                        networkAddAddressInfo(url,paramStr);
                    }else{
                        Toast.makeText(this,"请完善地址信息",Toast.LENGTH_SHORT).show();
                    }
                }








//                Log.d("CreateAddressActivity","  Session  "+ sessionData.getUid() + "      "+sessionData.getSid());
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

                        SharedPreferences.getInstance().putString("quanguo-list", JSON.toJSONString(addressDataInfo.getData()));
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
                options2Items.add(options2Items_01);
                options3Items.add(options3Items_01);


            }
        }



        //三级联动效果
        pvOptions.setPicker(options1Items, options2Items, options3Items, true);
        //设置选择的三级单位
        pvOptions.setLabels("省", "市", "");
        pvOptions.setTitle("请选择地区");
        pvOptions.setCyclic(false, false, false);
        //设置默认选中的三级项目
        //监听确定选择按钮
        pvOptions.setSelectOptions(0, 0, 0);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String sheng=options1Items.get(options1).getPickerViewText();
                String shi=options2Items.get(options1).get(option2);
                String qu=options3Items.get(options1).get(option2).get(options3);
                String tx="";
                if(sheng.equals(shi)){
                    tx =  shi+"市"+ qu;
                }else{
                    tx = sheng+"省"+ shi+"市"+ qu;
                }


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

                addAddressText.setText(adressAll);
                vMasker.setVisibility(View.GONE);
            }
        });


    }


    /**
     * 创建和修改收货地址信息
     * @param url
     * @param param
     */
    private void networkAddAddressInfo(String url,String  param) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("json", param);
        Log.d("CreateAddressActivity", url + "      ");
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





    /**
     * 修改用户名
     */
    User userTou;
    private void networkEditPersonInfo(String paramNet) {
        String url = "http://mapp.aiderizhi.com/?url=/user/modify";//
        Map<String, String> mapTou = new HashMap<String, String>();
        mapTou.put("json", paramNet);




        Log.d("CreateAddressActivity", paramNet + "      ");


        FastJsonRequest<PersonalDataInfo> fastJsonCommunity = new FastJsonRequest<PersonalDataInfo>(Request.Method.POST, url, PersonalDataInfo.class, null, new Response.Listener<PersonalDataInfo>() {
            @Override
            public void onResponse(PersonalDataInfo personalDataInfo) {

                DataStatus status = personalDataInfo.getStatus();
                if (status.getSucceed() == 1) {
                    userTou = personalDataInfo.getData();
                    if(userTou!=null){

                        String toastStr="";
                        if(isEditSex){
                            toastStr="修改性别成功";
                        }

                        if(isEditUserName){
                            toastStr="修改用户名成功";
                        }

                        if(isEditPassword){
                            toastStr="修改密码成功";
                        }
                        Toast.makeText(getApplicationContext(), toastStr, Toast.LENGTH_LONG).show();

                       /* String username =SharedPreferences.getInstance().getString("usename","");
                        String password=SharedPreferences.getInstance().getString("password", "");
                        if(username!=null&&!username.equals("")&&password!=null&&!password.equals("")){
                            RepeatLoginBut(username,password,"","");
                        }*/
                        Log.d("CreateAddressActivity", "modift 成功返回信息：   " + JSON.toJSONString(userTou)+ "++++succeed");
                    }


                } else {

                    // 请求失败
                    Log.d("CreateAddressActivity", "succeded=0  modift 返回信息 " + JSON.toJSONString(status) + "");
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
        fastJsonCommunity.setParams(mapTou);
        fastJsonCommunity.setShouldCache(true);
        mQueue.add(fastJsonCommunity);
    }


    /**
     * 重新登录方法
     */
    public LoginDataActi loginDataActi;
    private void RepeatLoginBut( String user,String pass,String uid,String sid) {
        String url = "http://mapp.aiderizhi.com/?url=/user/signin";//
        Map<String, String>   mapTou = new HashMap<String, String>();
        String  sessinStr ="{\"session\":{\"uid\":\""+uid+"\",\"sid\":\""+sid+"\"}}";
        mapTou.put("json", sessinStr);


        Map<String, String> map = new HashMap<String, String>();
        String  value="{\"account\":\""+user+"\",\"password\":\""+pass+"\"}";
        map.put("json", value);

        Log.d("CreateAddressActivity", sessinStr + "      " + value);


        FastJsonRequest<LoginDataInfo> fastJsonCommunity = new FastJsonRequest<LoginDataInfo>(Request.Method.POST, url, LoginDataInfo.class, mapTou, new Response.Listener<LoginDataInfo>() {
            @Override
            public void onResponse(LoginDataInfo loginDataInfo) {

                DataStatus status = loginDataInfo.getStatus();
                if (status.getSucceed() == 1) {
                    loginDataActi = loginDataInfo.getData();
                    if(loginDataActi!=null){
                        SharedPreferences.getInstance().putBoolean("islogin", true);
                        SharedPreferences.getInstance().putString("session", JSON.toJSONString(loginDataActi.getSession()));
                        SharedPreferences.getInstance().putString("user", JSON.toJSONString(loginDataActi.getUser()));
                        Toast.makeText(getApplicationContext(), "重新登录成功", Toast.LENGTH_SHORT).show();


                        if(isEditPassword){

                        }else {
                            finish();
                        }

                        Log.d("CreateAddressActivity", "登录信息：   "+JSON.toJSONString(loginDataActi.getSession())+"" + JSON.toJSONString(loginDataActi.getUser())+ "++++succeed");
                    }


                } else {

                    // 请求失败
                    Log.d("CreateAddressActivity", "登录信息succeded=0  " + JSON.toJSONString(status) + "");
//                    Toast.makeText(getApplicationContext(),""+status.getError_desc(),Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("CreateAddressActivity", "登录信息errror" + volleyError.toString() + "");
//                Toast.makeText(mContext,"服务器出错啦。。。",Toast.LENGTH_SHORT).show();
            }
        });

        fastJsonCommunity.setParams(map);

        mQueue.add(fastJsonCommunity);
    }




    /**
     * 修改密码，
     */
    private void networkEditPassWordInfo(String paramNet) {
        String url = "http://mapp.aiderizhi.com/?url=/user/modify";//
        Map<String, String> mapTou = new HashMap<String, String>();
        mapTou.put("json", paramNet);




        Log.d("CreateAddressActivity", paramNet + "      ");


        FastJsonRequest<StatusJsonInfo> fastJsonCommunity = new FastJsonRequest<StatusJsonInfo>(Request.Method.POST, url, StatusJsonInfo.class, null, new Response.Listener<StatusJsonInfo>() {
            @Override
            public void onResponse(StatusJsonInfo statusJsonInfo) {

                DataStatus status = statusJsonInfo.getStatus();
                if (status.getSucceed() == 1) {
                   String toastStr="";
                        if(isEditPassword){
                            toastStr="修改密码成功";
                            SharedPreferences.getInstance().putString("password",newPassword.getText().toString());
                        }
                        Toast.makeText(getApplicationContext(), toastStr, Toast.LENGTH_LONG).show();


                       String username =SharedPreferences.getInstance().getString("usename","");
                        String password=SharedPreferences.getInstance().getString("password", "");
                        if(username!=null&&!username.equals("")&&password!=null&&!password.equals("")){
                            RepeatLoginBut(username,password,"","");
                        }
                        Log.d("CreateAddressActivity", "modift 成功返回信息：   " + JSON.toJSONString(userTou) + "++++succeed");


                } else {

                    // 请求失败
                    Log.d("CreateAddressActivity", "succeded=0  modift 返回信息 " + JSON.toJSONString(status) + "");
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
        fastJsonCommunity.setParams(mapTou);
        fastJsonCommunity.setShouldCache(true);
        mQueue.add(fastJsonCommunity);
    }


}
