package com.miaomoe.www.miaomoe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends Activity implements View.OnClickListener {
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
        setting=getSharedPreferences("setting",0).edit();
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
        if (id == R.id.login_loginbtn) {
            String user=sid.getText().toString();
            String pw=spw.getText().toString();
            if(user.equals("")||user.length()!=10){
                Log.i("login","用户名错误");
            }else if(pw.equals("")){
                Log.i("login","密码不能为空");
            }else if(true){
                new NetLogin(mLogHandler,this,setting).execute("firstLog",user,pw);
            }else{
                Log.i("login","未知错误");
            }
        }
    }
    class LogHandler extends Handler {
        public void handleMessage(Message msg) {
            LoginActivity.this.toUpdate(msg.getData().getBoolean("LogResult"));
        }
    }
    private void showInfo(String info){
        Toast.makeText(getApplicationContext(),info ,
                Toast.LENGTH_SHORT).show();
    }

    private void toUpdate(Boolean data) {
        Log.i("update", String.valueOf(data));
        if(data!=null&&data){
            showInfo("登入成功");
            setResult(444);
            finish();
        }else{
            showInfo("用户名或密码错误");
        }


       /* Bitmap bitmap = Bitmap.createBitmap(8, 12, Bitmap.Config.RGB_565);
        bitmap.setPixels(data, 0, 8, 0, 0, 8, 12);
        yzm.setImageBitmap(bitmap);*/

    }
}

