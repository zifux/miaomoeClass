package com.miaomoe.www.miaomoe;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.miaomoe.www.miaomoe.DataBase.mSql;
import com.miaomoe.www.miaomoe.net.Net;
import com.miaomoe.www.miaomoe.table.Table;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aszer_000 on 2015/3/4 0004.
 */
public class BackGround extends AsyncTask<String, Integer,mRes>  {
    private Activity context=null;
    private MainActivity mcontext=null;
    SharedPreferences setting=null;
    public BackGround(MainActivity a){
        Log.i("MainActivity","已经创建后台MainActivity");
        this.mcontext=a;
        this.context=a;
        setting=context.getSharedPreferences("setting",0);
    }
    public BackGround(Activity a){
        Log.i("Activity","已经创建后台Activity");
        this.context=a;
        setting=context.getSharedPreferences("setting",0);
    }
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
        @Override
        protected mRes doInBackground(String... params) {
            mRes res=new mRes();
            int todo= Integer.valueOf(params[0]);
            switch (todo){
                case Do.FirstLgoIn:
                    if(!isNetworkConnected(context)){
                        return res.put(Do.NotNet,true);
                    }
                    res.put(Do.FirstLgoIn,new Net().firstReLogin(setting, params[1], params[2]));
                    break;
                case Do.InitLogin:
                    if(!isNetworkConnected(context)){
                        if(setting.getBoolean("classTableInDB",false)){
                            Log.i("backGround","从数据库取出");
                            res.put(Do.GetClassTable,new mSql(context,"school").getClassTable());
                        }else {
                            return res.put(Do.NotNet,true);
                        }
                    }else {
                        res.put(Do.InitLogin, new Net().initLogin(setting, params[1],params[2],context));
                    }
                    break;
                case Do.GetClassTable:
                    if(!setting.getBoolean("login",false)){
                        return res.put(Do.needLogIn,true);
                    }
                    if(setting.getBoolean("classTableInDB",false)){
                        Log.i("backGround","从数据库取出");
                        res.put(Do.GetClassTable,new mSql(context,"classtable").getClassTable());
                    }else {
                        if(!isNetworkConnected(context)){
                            return res.put(Do.NotNet,true);
                        }
                        res.put(Do.GetClassTable,new Net().getClassTable(setting.getString("Cookie",null),setting.getString("user",null)));
                    }
                    break;
                case Do.GetRoom:
                    if(!isNetworkConnected(context)){
                        return res.put(Do.NotNet,true);
                    }
                    res.put(Do.GetRoom,new Net().getRoom(params[1], params[2]));
                    //mcontext.viewPager.getAdapter().notifyDataSetChanged();
                    break;
                case Do.getStudentScore:
                    if(!isNetworkConnected(context)){
                        return res.put(Do.NotNet,true);
                    }
                    if(!setting.getBoolean("login",false)){
                        return res.put(Do.needLogIn,true);
                    }
                    res.put(Do.getStudentScore,new Net().getStudentScore(setting.getString("Cookie",null), params[1],params[2]));
                    break;
                default:
            }
            return res;
        }

    @Override
    protected void onPostExecute(mRes result) {
        int todo= result.name();
        switch (todo){
            case Do.NotNet:
                Toast.makeText(context.getApplicationContext(), "无网络连接",
                        Toast.LENGTH_SHORT).show();
                break;
            case Do.FirstLgoIn:
                if((Boolean)result.get()){
                    Toast.makeText(context.getApplicationContext(), "登入成功",
                            Toast.LENGTH_SHORT).show();
                    context.getSharedPreferences("setting",0).edit().putBoolean("login",true).commit();
                    context.setResult(444);
                }else{
                    Toast.makeText(context.getApplicationContext(), "登入失败,请检查学号和密码",
                            Toast.LENGTH_SHORT).show();
                    context.getSharedPreferences("setting",0).edit().putBoolean("login",false).commit();
                    context.setResult(445);
                }
                context.finish();
                break;
            case Do.InitLogin:
                ClassTable.setClassTable(mcontext, ((Table[]) result.get())[0]);
                new My(mcontext,((Table[]) result.get())[1]).setMyInfo();
                break;
            case Do.needLogIn:
                Toast.makeText(context.getApplicationContext(), "请登录学生园地",
                        Toast.LENGTH_SHORT).show();
                break;
            case Do.GetClassTable:
                if(!setting.getBoolean("classTableInDB",false)){
                    Log.i("backGround","即将存入数据库");
                    new mSql(context,"school").logOut("classtable").addClassTable( (Table) result.get());
                    setting.edit().putBoolean("classTableInDB", true).commit();
                }
                ClassTable.setClassTable(mcontext, (Table) result.get());
                break;
            case Do.GetRoom:
                new ClassRoom(mcontext).toUpdate((JSONArray) result.get());
                break;
            case Do.getStudentScore:
                new My(mcontext,(Table) result.get()).setMyInfo();
                break;
            default:

        }
    }
}
