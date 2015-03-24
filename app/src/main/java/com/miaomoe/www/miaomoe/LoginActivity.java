package com.miaomoe.www.miaomoe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
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
                showInfo("学号长度错误");
            }else if(pw.equals("")){
                showInfo("密码不能为空");
            }else{
                new BackGround(this).execute(String.valueOf(Do.FirstLgoIn),user,pw);
            }
        }
    }
    private void showInfo(String info){
        Toast.makeText(getApplicationContext(),info ,
                Toast.LENGTH_SHORT).show();
    }

}

