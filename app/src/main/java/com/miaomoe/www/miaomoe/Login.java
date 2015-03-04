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
import android.os.AsyncTask;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class Login extends Activity implements View.OnClickListener {
    TextView textPicPath;
    SharedPreferences.Editor setting;
    android.support.v7.app.ActionBar abr;
    Button slog;
    EditText spw;
    TextView sid;
    ImageView yzm;
    LogHandler mLogHandler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.login);
        initView();
        initEvent();
    }

    private void initEvent() {
        slog.setOnClickListener(this);
        mLogHandler=new LogHandler();
    }

    private void initView() {
        slog= (Button) findViewById(R.id.login_loginbtn);
        sid= (TextView) findViewById(R.id.login_studentID);
        spw= (EditText) findViewById(R.id.login_studentPW);
        yzm= (ImageView) findViewById(R.id.yzm);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        Log.i("login","aaaaaaaaaaaaaaadddddddd");
        if (id == R.id.login_loginbtn) {
            Log.i("login","判断");
            if(false/*sid.getText().equals("")||sid.getText().length()!=10*/){
                Log.i("login","用户名错误");
            }else if(false/*spw.getText().toString().equals("")*/){
                Log.i("login","密码错误");
            }else if(true){
                Log.i("login","即将启动");
                Log.i("login","dddddddddddddddddddddddddd");
new NetLogin(mLogHandler,this).execute(0,1,2);
            }else{
                Log.i("login","未知错误");
            }
        }
    }
    class LogHandler extends Handler {
        public void handleMessage(Message msg) {
            Login.this.toUpdate(msg.getData().getIntArray("pic"));
        }
    }

    private void toUpdate(int[] data) {
        Log.i("update", String.valueOf(data));
       /* Bitmap bitmap = Bitmap.createBitmap(8, 12, Bitmap.Config.RGB_565);
        bitmap.setPixels(data, 0, 8, 0, 0, 8, 12);
        yzm.setImageBitmap(bitmap);*/

    }
}

