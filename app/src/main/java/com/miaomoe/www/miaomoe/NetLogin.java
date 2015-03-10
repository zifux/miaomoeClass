package com.miaomoe.www.miaomoe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieSpecRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by aszer_000 on 2015/3/4 0004.
 */
public class NetLogin extends AsyncTask<String, Integer,Boolean>  {
    private Activity context;
    private Handler mainHandler;
    SharedPreferences.Editor setting;
    NetLogin(Handler a,Activity b,SharedPreferences.Editor setting){
        Log.i("a","已经创建登录后台");
        this.mainHandler=a;
        this.context=b;
        this.setting=setting;
    }

        @Override
        protected Boolean doInBackground(String... params) {
            if(params[0].equals("firstLog")){
                return Login.firstReLogin(setting,params[1],params[2]);
            }else{
                return Login.useSessionLogin(params[1]);
            }

        }

    @Override
    protected void onPostExecute(Boolean result) {
        Log.i("NetLgoinResult", String.valueOf(result));
        Message msg=new Message();
        Bundle bun=new Bundle();
        bun.putBoolean("LogResult", result);
        msg.setData(bun);
        mainHandler.sendMessage(msg);
    }
}
