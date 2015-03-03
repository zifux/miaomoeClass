package com.miaomoe.www.miaomoe;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;

public class Login extends ActionBarActivity implements View.OnClickListener {
    private android.support.v7.app.ActionBar abr;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        abr=getSupportActionBar();
        abr.setSubtitle("\t 教务处学生园地\\(≧ω≦)");
        Resources res=getResources();
        abr.setBackgroundDrawable(res.getDrawable(R.drawable.bar2));
        Log.i("y8ijg","dfdddddddddddddddddddddddddddddddddd");
        setContentView(R.layout.login);
    }
    @Override
    public void onClick(View v) {

    }
}
