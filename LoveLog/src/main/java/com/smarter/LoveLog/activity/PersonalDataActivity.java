package com.smarter.LoveLog.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.ImagePagerAdapter;
import com.smarter.LoveLog.adapter.RecyclePersonAdapter;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.community.User;
import com.smarter.LoveLog.model.jsonModel.PersonParamInfo;
import com.smarter.LoveLog.model.loginData.LogingOutInfo;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.loginData.PersonalDataInfo;
import com.smarter.LoveLog.model.loginData.SessionData;
import com.smarter.LoveLog.ui.CircleNetworkImage;
import com.smarter.LoveLog.ui.popwindow.ActionSheetDialog;
import com.smarter.LoveLog.utills.TestUtil;
import com.smarter.LoveLog.utills.ViewUtill;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class PersonalDataActivity extends BaseFragmentActivity implements View.OnClickListener,RecyclePersonAdapter.OnItemClickListener{
    String Tag= "PersonalDataActivity";
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.imageTitle)
    CircleNetworkImage imageTitle;

    @Bind(R.id.touImeBut)
    RelativeLayout touImeBut;

    @Bind(R.id.backBUt)
    ImageView backBUt;










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data_view);
        ButterKnife.bind(this);



        getDataIntent();

        setListen();

    }

    @Override
    protected void onResume() {
        super.onResume();
        intData();
    }

    private void setListen() {
        touImeBut.setOnClickListener(this);
        backBUt.setOnClickListener(this);
    }
    SessionData sessionData;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void intData() {

        Boolean isLogin =SharedPreferences.getInstance().getBoolean("islogin", false);
        if(isLogin){
            String  sessionString=SharedPreferences.getInstance().getString("session", "");
           sessionData = JSON.parseObject(sessionString,SessionData.class);
            if(sessionData!=null){

                    networkPersonl(sessionData.getUid(),sessionData.getSid());

                Log.d("PersonalDataActivity","  Session  "+ sessionData.getUid() + "      "+sessionData.getSid());
            }

        }else{
            initRecycleViewVertical();
            Toast.makeText(getApplicationContext(), "未登录，请先登录" , Toast.LENGTH_SHORT).show();
        }


    }

    private void getDataIntent() {
        Intent intent = getIntent();
        if(intent!=null){
            String  str = intent.getStringExtra("ObjectData");
           // Toast.makeText(this,str+"",Toast.LENGTH_LONG).show();
        }


    }

    public void initRecycleViewVertical(){

        //头像
        imageTitle.setDefaultImageResId(R.mipmap.login);
        imageTitle.setErrorImageResId(R.mipmap.login);
        String UserimageUrl="";
        if(user!=null&&user.getAvatar()!=null){
             UserimageUrl=user.getAvatar();
        }

        if(mQueue.getCache().get(UserimageUrl)==null){
            imageTitle.startAnimation(ImagePagerAdapter.getInAlphaAnimation(2000));
        }
        imageTitle.setImageUrl(UserimageUrl, AppContextApplication.getInstance().getmImageLoader());




        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // 默认是Vertical，可以不写
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // 设置布局管理器
        recyclerView.setLayoutManager(layoutManager);

        // 创建数据集
        String[] dataset = new String[]{"用户名/昵称","绑定手机号","性别","会员等级","修改密码","收货地址"};
//        String[] dataValue=new String[]{"美羊羊","15083806689","男","V0初级会员","",""};
        String[] dataValue=new String[]{"","","","","",""};
        if(user!=null){
            dataValue[0]=user.getName();
            dataValue[1]=user.getMobile();

            dataValue[2]=user.getSex();

            dataValue[3]=user.getRank_level()+"";
        }

        // 创建Adapter，并指定数据集
        RecyclePersonAdapter adapter = new RecyclePersonAdapter(dataset,dataValue);
        // 设置Adapter
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }







    private final int PIC_FROM_CAMERA = 1;
    private final int PIC_FROM＿LOCALPHOTO = 0;
    private Uri photoUri;
    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case  R.id.touImeBut:
                 new ActionSheetDialog(PersonalDataActivity.this)
                         .builder()
                         .setCancelable(true)
                         .setTitle("选项")
                         .setCanceledOnTouchOutside(false)
                         .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue,
                                 new ActionSheetDialog.OnSheetItemClickListener() {
                                     @Override
                                     public void onClick(int which) {
                                         doHandlerPhoto(PIC_FROM_CAMERA);// 用户点击了从照相机获取
                                     }
                                 })
                         .addSheetItem("从相册中选取", ActionSheetDialog.SheetItemColor.Blue,
                                 new ActionSheetDialog.OnSheetItemClickListener() {
                                     @Override
                                     public void onClick(int which) {
                                         doHandlerPhoto(PIC_FROM＿LOCALPHOTO);
                                     }
                                 })
                         .show();

                 break;
             case  R.id.backBUt:
                 finish();
                 break;
         }
    }









    private void doHandlerPhoto(int type) {
        try {
            // 保存裁剪后的图片文件
            File pictureFileDir = new File(
                    Environment.getExternalStorageDirectory(), "/LoveLogupload");
            if (!pictureFileDir.exists()) {
                pictureFileDir.mkdirs();
            }
            File picFile = new File(pictureFileDir, "love.jpeg");
            if (!picFile.exists()) {
                picFile.createNewFile();
            }
            photoUri = Uri.fromFile(picFile);

            if (type == PIC_FROM＿LOCALPHOTO) {
                Intent intent = getCropImageIntent();
                startActivityForResult(intent, PIC_FROM＿LOCALPHOTO);
            } else {
                Intent cameraIntent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(cameraIntent, PIC_FROM_CAMERA);
            }

        } catch (Exception e) {
            Log.i("HandlerPicError", "处理图片出现错误");
        }
    }

    /**
     * 调用图片剪辑程序
     */
    public Intent getCropImageIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        setIntentParams(intent);
        return intent;
    }

    /**
     * 设置公用参数
     */
    private void setIntentParams(Intent intent)
    {
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 600);
        intent.putExtra("outputY", 600);
        intent.putExtra("noFaceDetection", true); // no face detection
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
    }
    /**
     * 启动裁剪
     */
    private void cropImageUriByTakePhoto() {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoUri, "image/*");
        setIntentParams(intent);
        startActivityForResult(intent, PIC_FROM＿LOCALPHOTO);
    }


    private Bitmap decodeUriAsBitmap(Uri uri)
    {
        Bitmap bitmap = null;
        try
        {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case PIC_FROM_CAMERA: // 拍照
                try
                {
                    cropImageUriByTakePhoto();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            case PIC_FROM＿LOCALPHOTO:
                try
                {
                    if (photoUri != null)
                    {
                        Bitmap bitmap = decodeUriAsBitmap(photoUri);
                        String imgStr =TestUtil.bitmaptoBase64String(bitmap,80);
                                if(sessionData!=null){
                                    PersonParamInfo  paramInfo=new PersonParamInfo();
                                    paramInfo.setSession(sessionData);
                                    paramInfo.setAvatar(imgStr);
                                    paramInfo.setAction("avatar");
                                    String  param= JSON.toJSONString(paramInfo);
                                    networkUpTouMig(param);
                                }else{
                                    Log.d("PersonalDataActivity", null + "    nullSession  ");
                                }

//                        imageTitle.setImageBitmap(bitmap);
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 获取个人资料
     */
    User  user;
    private void networkPersonl(String uid,String sid) {
        String url = "http://mapp.aiderizhi.com/?url=/user/info";//
        Map<String, String> mapTou = new HashMap<String, String>();
        String  sessinStr ="{\"session\":{\"uid\":\""+uid+"\",\"sid\":\""+sid+"\"}}";
        mapTou.put("json", sessinStr);




        Log.d("PersonalDataActivity", sessinStr + "      ");


        FastJsonRequest<PersonalDataInfo> fastJsonCommunity = new FastJsonRequest<PersonalDataInfo>(Request.Method.POST, url, PersonalDataInfo.class, null, new Response.Listener<PersonalDataInfo>() {
            @Override
            public void onResponse(PersonalDataInfo personalDataInfo) {

                DataStatus status = personalDataInfo.getStatus();
                if (status.getSucceed() == 1) {
                    user = personalDataInfo.getData();
                    if(user!=null){
                        SharedPreferences.getInstance().putString("user",JSON.toJSONString(user));
                        initRecycleViewVertical();//ok
                        Log.d("PersonalDataActivity", "用户信息：   " + JSON.toJSONString(user)+ "++++succeed");
                    }


                } else {

                    // 请求失败
                    Log.d("PersonalDataActivity", "succeded=00000  " + JSON.toJSONString(status) + "");
                    if(status.getError_code()==1000){
                        SharedPreferences.getInstance().putBoolean("islogin",false);
//                        ViewUtill.ShowAlertDialog(PersonalDataActivity.this);
                        finish();
                    }
                    Toast.makeText(getApplicationContext(), "" + status.getError_desc(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("PersonalDataActivity", "errror" + volleyError.toString() + "");
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
     * 上传头像
     */
    User  userTou;
    private void networkUpTouMig(String paramNet) {
        String url = "http://mapp.aiderizhi.com/?url=/user/modify";//
        Map<String, String> mapTou = new HashMap<String, String>();
        mapTou.put("json", paramNet);




        Log.d("PersonalDataActivity", paramNet + "      ");


        FastJsonRequest<PersonalDataInfo> fastJsonCommunity = new FastJsonRequest<PersonalDataInfo>(Request.Method.POST, url, PersonalDataInfo.class, null, new Response.Listener<PersonalDataInfo>() {
            @Override
            public void onResponse(PersonalDataInfo personalDataInfo) {

                DataStatus status = personalDataInfo.getStatus();
                if (status.getSucceed() == 1) {
                    userTou = personalDataInfo.getData();
                    if(userTou!=null){
                        intData();
//                        SharedPreferences.getInstance().putString("user",JSON.toJSONString(user));
//                        initRecycleViewVertical();//ok
                        Log.d("PersonalDataActivity", "modift 成功返回信息：   " + JSON.toJSONString(userTou)+ "++++succeed");
                    }


                } else {

                    // 请求失败
                    Log.d("PersonalDataActivity", "succeded=0  modift 返回信息 " + JSON.toJSONString(status) + "");
                    Toast.makeText(getApplicationContext(), "" + status.getError_desc(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("PersonalDataActivity", "errror" + volleyError.toString() + "");
            }
        });
        fastJsonCommunity.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //fastJsonCommunity.setTag(TAG);
        fastJsonCommunity.setParams(mapTou);
        fastJsonCommunity.setShouldCache(true);
        mQueue.add(fastJsonCommunity);
    }





    //个人资料修改Item点击处理
    @Override
    public void onItemClickAdapter(int ischeckArray) {
        if(ischeckArray==0){
            //挑战到修改用户名/昵称界面//
            //给CreateAddress传参数 表示修改用户名昵称
            Intent intent2 = new Intent(this, CreateAddressActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean("xiugaiName",true);
            intent2.putExtras(bundle);
            this.startActivity(intent2);
        }

        if(ischeckArray==2){
            //挑战到修改性别界面//
            //
            Intent intent2 = new Intent(this, CreateAddressActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean("xiugaiSex",true);
            bundle.putString("sexValue", user.getSex());
            intent2.putExtras(bundle);
            this.startActivity(intent2);
        }
        if(ischeckArray==4){
            //挑战到修改密码界面//
            //
            Intent intent2 = new Intent(this, CreateAddressActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean("xiugaiPassword",true);
            intent2.putExtras(bundle);
            this.startActivity(intent2);
        }


        if(ischeckArray==5){//收货地址
            //挑战到地址管理界面
            Intent intent2 = new Intent(this, AddressManageActivity.class);
              /*  Bundle bundle = new Bundle();
                bundle.putSerializable("PromotePostsData", (Serializable) pp);
                intent.putExtras(bundle);*/
            this.startActivity(intent2);
        }
    }
}
