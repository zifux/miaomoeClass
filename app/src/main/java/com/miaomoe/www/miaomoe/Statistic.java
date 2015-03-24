package com.miaomoe.www.miaomoe;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by xiuu on 2015/2/25 0025.
 */
public class Statistic extends ActionBarActivity implements View.OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.findempty);
        TextView a= (TextView) findViewById(R.id.emptytextView);
        a.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.i("s","s");
        setResult(200);
        finish();
    }
}