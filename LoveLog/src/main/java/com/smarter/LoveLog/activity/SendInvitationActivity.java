package com.smarter.LoveLog.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.feedback.FeedDataInfo;
import com.smarter.LoveLog.model.feedback.FeedMess;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.loginData.SessionData;
import com.smarter.LoveLog.utills.DeviceUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.richeditor.RichEditor;

/**
 * Created by Administrator on 2015/11/30.
 */
public class SendInvitationActivity extends BaseFragmentActivity implements View.OnClickListener{
    String Tag= "SendInvitationActivity";



    @Bind(R.id.backBUt)
    ImageView backBUt;

    @Bind(R.id.tv_right_title)
    TextView tv_right_title;




    private RichEditor mEditor;
    private TextView mPreview;










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_invitation_view);
        ButterKnife.bind(this);



        getDataIntent();
        init();
        setListen();

    }

    private void init() {
        mEditor = (RichEditor) findViewById(R.id.editor);
        mEditor.setEditorHeight(DeviceUtil.getHeight(this));
        mEditor.setEditorWidth(DeviceUtil.getWidth(this));
        mEditor.setEditorFontSize(20);
        mEditor.setEditorFontColor(Color.parseColor("#333333"));
        //mEditor.setEditorBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        mEditor.setPadding(10, 10, 10, 60);
        //    mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        mEditor.setPlaceholder("发表你的帖子吧........");

        mPreview = (TextView) findViewById(R.id.preview);
        mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override public void onTextChange(String text) {
                mPreview.setText(text);
            }
        });

        findViewById(R.id.action_undo).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.undo();
            }
        });

        findViewById(R.id.action_redo).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.redo();
            }
        });

        findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setBold();
            }
        });

        findViewById(R.id.action_italic).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setItalic();
            }
        });

        findViewById(R.id.action_subscript).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setSubscript();
            }
        });

        findViewById(R.id.action_superscript).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setSuperscript();
            }
        });

        findViewById(R.id.action_strikethrough).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setStrikeThrough();
            }
        });

        findViewById(R.id.action_underline).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setUnderline();
            }
        });

        findViewById(R.id.action_heading1).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(1);
            }
        });

        findViewById(R.id.action_heading2).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(2);
            }
        });

        findViewById(R.id.action_heading3).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(3);
            }
        });

        findViewById(R.id.action_heading4).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(4);
            }
        });

        findViewById(R.id.action_heading5).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(5);
            }
        });

        findViewById(R.id.action_heading6).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(6);
            }
        });

        findViewById(R.id.action_txt_color).setOnClickListener(new View.OnClickListener() {
            boolean isChanged;

            @Override public void onClick(View v) {
                mEditor.setTextColor(isChanged ? Color.parseColor("#333333") : Color.RED);
                isChanged = !isChanged;
            }
        });

        findViewById(R.id.action_bg_color).setOnClickListener(new View.OnClickListener() {
            boolean isChanged;

            @Override public void onClick(View v) {
                mEditor.setTextBackgroundColor(isChanged ? Color.WHITE : Color.YELLOW);
                isChanged = !isChanged;
            }
        });

        findViewById(R.id.action_indent).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setIndent();
            }
        });

        findViewById(R.id.action_outdent).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setOutdent();
            }
        });

        findViewById(R.id.action_align_left).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setAlignLeft();
            }
        });

        findViewById(R.id.action_align_center).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setAlignCenter();
            }
        });

        findViewById(R.id.action_align_right).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setAlignRight();
            }
        });

        findViewById(R.id.action_blockquote).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setBlockquote();
            }
        });

        findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.insertImage("http://www.aiderizhi.com/uploads/image/201603/1457425089230872.png",
                        "dachshund");
                mEditor.setInitialScale(0);
               /* Snackbar.make(mEditor, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        findViewById(R.id.action_insert_link).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {


               showLinkDialog();
            }
        });
        findViewById(R.id.action_insert_checkbox).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.insertTodo();
            }
        });



    }

    private void showLinkDialog() {
//        final int start = knife.getSelectionStart();
//        final int end = knife.getSelectionEnd();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        View view = getLayoutInflater().inflate(R.layout.dialog_link, null, false);
        final EditText editText = (EditText) view.findViewById(R.id.edit);
        builder.setView(view);
        builder.setTitle(R.string.dialog_title);

        builder.setPositiveButton(R.string.dialog_button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String link = editText.getText().toString().trim();
                if (TextUtils.isEmpty(link)) {
                    return;
                }

                mEditor.insertLink(link, link);
            }
        });

        builder.setNegativeButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // DO NOTHING HERE
            }
        });

        builder.create().show();
    }

    private void setListen() {
        backBUt.setOnClickListener(this);
        tv_right_title.setOnClickListener(this);
    }
    SessionData sessionData;
    private void intData() {

        Boolean isLogin = SharedPreferences.getInstance().getBoolean("islogin", false);

        String  sessionString=SharedPreferences.getInstance().getString("session", "");
        if(isLogin){
            try {
                sessionData = JSON.parseObject(sessionString, SessionData.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        if(sessionData.getSid()!=null&&!sessionData.getSid().equals("")){
            networkPersonl(sessionData.getUid(),sessionData.getSid());
        }else{
            networkPersonl("","");
        }






    }



    /**
     * 反馈
     */
    FeedMess feedMess;
    private void networkPersonl(String uid,String sid) {
        String url = "http://mapp.aiderizhi.com/?url=/feedback";//
        Map<String, String> mapTou = new HashMap<String, String>();
        String  sessinStr ="{\"session\":{\"uid\":\""+uid+"\",\"sid\":\""+sid+"\"},\"title\":\""+"dd"+"\",\"content\":\""+"  dd"+"\",\"contact\":\""+"dd"+"\"}";
        mapTou.put("json", sessinStr);




        Log.d(Tag, sessinStr + "      ");


        FastJsonRequest<FeedDataInfo> fastJsonCommunity = new FastJsonRequest<FeedDataInfo>(Request.Method.POST, url, FeedDataInfo.class, null, new Response.Listener<FeedDataInfo>() {
            @Override
            public void onResponse(FeedDataInfo feedDataInfo) {

                DataStatus status = feedDataInfo.getStatus();
                if (status.getSucceed() == 1) {
                    feedMess=feedDataInfo.getData();
                    Toast.makeText(getApplicationContext(),feedMess.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(Tag, "反馈信息：   " + JSON.toJSONString(status)+ "++++succeed");


                } else {

                    // 请求失败
                    Log.d(Tag, "succeded=00000  " + JSON.toJSONString(status) + "");
                    Toast.makeText(getApplicationContext(), "" + status.getError_desc(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(Tag, "errror" + volleyError.toString() + "");
                Toast.makeText(getApplicationContext(),"未知错误", Toast.LENGTH_SHORT).show();
            }
        });
        fastJsonCommunity.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        fastJsonCommunity.setParams(mapTou);
        fastJsonCommunity.setShouldCache(true);
        mQueue.add(fastJsonCommunity);
    }


    private void getDataIntent() {
        Intent intent = getIntent();
        if(intent!=null){
            String  str = intent.getStringExtra("ObjectData");
           // Toast.makeText(this,str+"",Toast.LENGTH_LONG).show();
        }


    }


    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.backBUt:
                 finish();
                 break;

             case R.id.tv_right_title:
                 mEditor.getHtml();
                 Log.d(Tag, startHtml + mEditor.getHtml() + endHtml);

                 Intent intent =new Intent(this,RichTextActivity.class);
                 this.startActivity(intent);
                 break;

         }
    }




    String  startHtml="<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<meta charset=\"GBK\">\n" +
            "<title>帖子详情</title>\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\" />\n" +
            "<meta name=\"apple-mobile-web-app-capable\" content=\"yes\" />\n" +
            "<meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\" />\n" +
            "<meta name=\"format-detection\" content=\"telephone=no\" />\n" +
            "<style>\n" +
            "html{font-size:62.5%;}\n" +
            "body,div,span,applet,object,iframe,h1,h2,h3,h4,h5,h6,p,blockquote,pre,a,abbr,acronym,address,big,cite,code,del,dfn,em,font,img,ins,kbd,q,s,samp,small,strike,strong,sub,sup,tt,var,b,u,i,center,dl,dt,dd,ol,ul,li,fieldset,form,label,legend,table,caption,tbody,tfoot,thead,tr,th,td{margin:0;padding:0;border:0;outline:0;vertical-align:baseline;}\n" +
            "body{font-family: \"SimHei\",\"Helvetica Neue\",Arial,\"Droid Sans\",sans-serif;color:#666;position: relative;}\n" +
            "#content{color:#333;line-height:2.6rem;margin: 3rem 1rem 2.5rem;font-size:1.4rem;font-weight:300;text-align:justify; text-justify:distribute-all-lines;} \n" +
            "img{width:auto;height:auto;vertical-align:middle;border:0;max-width: 100%;display:block;margin:1rem auto;color: transparent;font-size: 0;}\n" +
            "</style>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div class=\"content\" id=\"post-content\">";
    String endHtml="</div>\n" +
            "</body>\n" +
            "<script type=\"text/javascript\">\n" +
            "//1，匹配出图片img标签（即匹配出所有图片），过滤其他不需要的字符\n" +
            "//2.从匹配出来的结果（img标签中）循环匹配出图片地址（即src属性）\n" +
            "function view_content_img(){\n" +
            "\t\n" +
            "\tvar str = document.getElementById(\"post-content\").innerHTML;\n" +
            "\t//匹配图片（g表示匹配所有结果i表示区分大小写）\n" +
            "\tvar imgReg = /<img.*?(?:>|\\/>)/gi;\n" +
            "\t//匹配src属性\n" +
            "\tvar srcReg = /src=[\\'\\\"]?([^\\'\\\"]*)[\\'\\\"]?/i;\n" +
            "\tvar arr = str.match(imgReg);\n" +
            "\talert('所有已成功匹配图片的数组：'+arr);\n" +
            "\tfor (var i = 0; i < arr.length; i++) {\n" +
            "\t  var src = arr[i].match(srcReg);\n" +
            "\t  //获取图片地址\n" +
            "\t  if(src[1]){\n" +
            "\t\t//alert('已匹配的图片地址'+(i+1)+'：'+src[1]);\n" +
            "\t  }\n" +
            "\t  //当然你也可以替换src属性\n" +
            "\t  if (src[0]) {\n" +
            "\t\tvar t = src[0].replace(/src/i, \"href\");\n" +
            "\t\t//alert(t);\n" +
            "\t  }\n" +
            "\t}\n" +
            "}\n" +
            "</script>\n" +
            "</html>\n" +
            "\n" +
            "\n";


}
