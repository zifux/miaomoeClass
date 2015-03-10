package com.miaomoe.www.miaomoe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class Settings extends ActionBarActivity implements View.OnClickListener {
    TextView textPicPath;
    SharedPreferences.Editor setting;
    android.support.v7.app.ActionBar abr;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        abr=getSupportActionBar();
        abr.setSubtitle("\t 努力学习吧\\(≧ω≦)");
        Resources res=getResources();
        abr.setBackgroundDrawable(res.getDrawable(R.drawable.bar));
        setContentView(R.layout.setting);
        Button selectPic= (Button) findViewById(R.id.setting_select_pic);
        Button deletePic= (Button) findViewById(R.id.setting_deleteBackPic);
        textPicPath= (TextView) findViewById(R.id.setting_backpic_path);
        String backpic=getSharedPreferences("setting",0).getString("BackPic",null);
        if(backpic!=null){
            textPicPath.setText(backpic);
        }
        selectPic.setOnClickListener(this);
        deletePic.setOnClickListener(this);
        setting=getSharedPreferences("setting",0).edit();
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id == R.id.setting_select_pic) {
            Intent pic=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pic,200);
        }else if(id==R.id.setting_deleteBackPic){
            setting.putString("BackPic","无");
            setting.putString("YBackPic","无");
            setting.commit();
            setResult(334);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==200&&resultCode==RESULT_OK){
            String[] type={MediaStore.Images.Media.DATA};
            Uri image=data.getData();
            Cursor res=getContentResolver().query(image,type,null,null,null);
            res.moveToFirst();
            final String YpicPath=res.getString(res.getColumnIndex(type[0]));
            final Bitmap bm=(BitmapFactory.decodeFile(YpicPath));
            String picPath=YpicPath;
            setting.putString("YBackPic",YpicPath);
            //原图宽高
            final int rawHeight = bm.getHeight();
            final int rawWidth = bm.getWidth();
            // 设定图片新的高宽
            final DisplayMetrics dm=new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            File backpicPath=new File("/sdcard/Android/data/com.miaomoe.zixi");
            if(!backpicPath.exists()){
                backpicPath.mkdirs();
            }
            final File backpic=new File("/sdcard/Android/data/com.miaomoe.zixi","backImage.jpeg");

            if(rawHeight>dm.heightPixels&&rawWidth>dm.widthPixels){

                Matrix smallPic=new Matrix();
                Bitmap newBitmap;
                if(rawHeight>rawWidth){
                    smallPic.reset();
                    smallPic.postScale((float)dm.widthPixels/(float)rawWidth,(float)dm.widthPixels/(float)rawWidth);
                    newBitmap = Bitmap.createBitmap(bm, 0, 0,rawWidth,rawHeight, smallPic, true);
                }else{
                    smallPic.reset();
                    smallPic.postScale((float)dm.heightPixels/(float)rawHeight,(float)dm.heightPixels/(float)rawHeight);
                    newBitmap = Bitmap.createBitmap(bm, 0, 0,rawWidth,rawHeight, smallPic, true);
                }
                //建立文件

                boolean backpicResult;
                try {
                    //if(backpic.exists())backpic.delete();
                    backpicResult=backpic.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileOutputStream fos=null;
                try {
                    fos=new FileOutputStream(backpic);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                newBitmap.compress(Bitmap.CompressFormat.JPEG,80,fos);
                //然后将Bitmap保存到SDCard中
                toUpdate(backpic.getAbsolutePath());
            }else{
                toUpdate(YpicPath);
            }
            res.close();
        }
    }
    private void toUpdate(String k){
        setting.putString("BackPic",k);
        setting.commit();
        textPicPath.setText(k);
        String backpic=getSharedPreferences("setting",0).getString("BackPic",null);
        Intent result=new Intent();
        if(backpic!=null){
            Uri pic=Uri.parse(backpic);
            result.setData(pic);
            setResult(333,result);
        }
    }

}
