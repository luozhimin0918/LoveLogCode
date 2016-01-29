package com.smarter.LoveLog.activity;

/**
 * Created by Administrator on 2016/1/27.
 */
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler.Callback;
import android.os.Handler;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Toast;

import com.smarter.LoveLog.R;
import com.smarter.LoveLog.ui.ZoomableImageView;
import com.smarter.LoveLog.ui.popwindow.ActionSheetDialog;
import com.smarter.LoveLog.utills.DeviceUtil;

public class ShowWebImageActivity extends BaseFragmentActivity implements ZoomableImageView.OnZoomLongClickListener{
    private TextView imageTextView = null;
    private  String imagePath = null;
    private ZoomableImageView imageView = null;
    LinearLayout allLinear;
    Bitmap bitmapDrawable;

    Uri uri=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_webimage);
        this.imagePath = getIntent().getStringExtra("image");


         this.uri = getIntent().getParcelableExtra("photo");

        this.imageTextView = (TextView) findViewById(R.id.show_webimage_imagepath_textview);
        imageTextView.setText(this.imagePath);
        imageView = (ZoomableImageView) findViewById(R.id.show_webimage_imageview);
        allLinear= (LinearLayout) findViewById(R.id.allLinear);
        allLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageView.setOnZoomLoogClick(this);


        Runnable downloadRun = new Runnable(){

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    if(imagePath!=null&&!imagePath.equals("")){
                        bitmapDrawable =((BitmapDrawable) ShowWebImageActivity.loadImageFromUrl(imagePath)).getBitmap();



                    }

                    if(uri!=null){
                        bitmapDrawable= getBitmapFromUri(uri);
                    }

                    if(bitmapDrawable!=null){
                        handler.sendEmptyMessageDelayed(SHOW_VIEW, 100);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(downloadRun).start();



    }


    private Bitmap getBitmapFromUri(Uri uri)
    {
        try
        {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            return bitmap;
        }
        catch (Exception e)
        {

            e.printStackTrace();
            return null;
        }
    }


    private final int SHOW_VIEW = 0;
    Handler handler = new Handler(new Callback() {

        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_VIEW:
                    imageView.setImageBitmap(bitmapDrawable);
                    break;

                default:
                    break;
            }
            return false;
        }
    });


    public static Drawable loadImageFromUrl(String url) throws IOException {

        URL m = new URL(url);
        InputStream i = (InputStream) m.getContent();
        Drawable d = Drawable.createFromStream(i, "src");
        return d;
    }










    // 手指上下滑动时的最小速度
    private static final int YSPEED_MIN = 1000;

    // 手指向右滑动时的最小距离
    private static final int XDISTANCE_MIN = 150;

    // 手指向上滑或下滑时的最小距离
    private static final int YDISTANCE_MIN = 100;

    // 记录手指按下时的横坐标。
    private float xDown;

    // 记录手指按下时的纵坐标。
    private float yDown;

    // 记录手指移动时的横坐标。
    private float xMove;

    // 记录手指移动时的纵坐标。
    private float yMove;

    // 用于计算手指滑动的速度。
    private VelocityTracker mVelocityTracker;



    /**
     * 创建VelocityTracker对象，并将触摸界面的滑动事件加入到VelocityTracker当中。
     *
     * @param event
     *
     */
    private void createVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 回收VelocityTracker对象。
     */
    private void recycleVelocityTracker() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }

    /**
     *
     * @return 滑动速度，以每秒钟移动了多少像素值为单位。
     */
    private int getScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getYVelocity();
        return Math.abs(velocity);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        createVelocityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = event.getRawX();
                yDown = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = event.getRawX();
                yMove = event.getRawY();
                // 滑动的距离
                int distanceX = (int) (xMove - xDown);
                int distanceY = (int) (yMove - yDown);
                // 获取顺时速度
                int ySpeed = getScrollVelocity();
                // 关闭Activity需满足以下条件：
                // 1.x轴滑动的距离>XDISTANCE_MIN
                // 2.y轴滑动的距离在YDISTANCE_MIN范围内
                // 3.y轴上（即上下滑动的速度）<XSPEED_MIN，如果大于，则认为用户意图是在上下滑动而非左滑结束Activity
                if (ySpeed > 100) {
                } else if (ySpeed < 100) {
                    if (distanceX > XDISTANCE_MIN
                            && (distanceY < YDISTANCE_MIN && distanceY > -YDISTANCE_MIN)
                            && ySpeed < YSPEED_MIN) {

                        if(xDown>0&&xDown<100){
                            finish();
                            overridePendingTransition(R.anim.in_from_left,
                                    R.anim.out_to_right);
                        }

                    } else if (distanceX < -XDISTANCE_MIN
                            && (distanceY < YDISTANCE_MIN && distanceY > -YDISTANCE_MIN)
                            && ySpeed < YSPEED_MIN) {


//                        overridePendingTransition(R.anim.in_from_right,
//                                R.anim.out_to_left);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                recycleVelocityTracker();
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }


    @Override
    public void onZoomlongClick() {

        new ActionSheetDialog(ShowWebImageActivity.this)
                .builder()
                .setCancelable(true)
                .setTitle("选项")
                .setCanceledOnTouchOutside(true)
                .addSheetItem("保存照片", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                savaPicture();// 保存照片
                            }
                        })
                .show();


    }

    private void savaPicture() {

        File pictureFileDir = new File(
                Environment.getExternalStorageDirectory(), "/LoveLogupload");
        if (!pictureFileDir.exists()) {
            pictureFileDir.mkdirs();
        }

        if (bitmapDrawable != null && imagePath != null) {
            File picFile = new File(pictureFileDir, DeviceUtil.fileName(imagePath));


//                            picFile.createNewFile();
            Boolean isSava = DeviceUtil.saveBitmap(bitmapDrawable, picFile);

            if (isSava) {
                Toast.makeText(getApplicationContext(), "已保存至"+pictureFileDir.getAbsolutePath()+"文件夹下", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "保存失败", Toast.LENGTH_SHORT).show();
            }


        }

        if(bitmapDrawable != null && uri!=null){
            File picFile = new File(pictureFileDir, DeviceUtil.fileName(uri.toString()));


//                            picFile.createNewFile();
            Boolean isSava = DeviceUtil.saveBitmap(bitmapDrawable, picFile);

            if (isSava) {
                Toast.makeText(getApplicationContext(), "已保存至"+pictureFileDir.getAbsolutePath()+"文件夹下", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "保存失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
