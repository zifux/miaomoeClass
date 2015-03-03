package com.miaomoe.www.miaomoe;

import android.app.Activity;
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


public class Login extends Activity implements View.OnClickListener {
    TextView textPicPath;
    SharedPreferences.Editor setting;
    android.support.v7.app.ActionBar abr;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.login);
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
}

