package com.miaomoe.www.miaomoe;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by aszer_000 on 2015/3/5 0005.
 */
public class Login {
    public static Boolean useSessionLogin(String sessionid){
        DefaultHttpClient httpClient=new DefaultHttpClient();
        Boolean result=false;
        Log.i("cookic", sessionid);
        HttpGet get2=new HttpGet("http://210.30.48.14:8080/ACTIONQUERYSTUDENTSCORE.APPPROCESS");
        get2.setHeader("Cookie", "JSESSIONID=" + sessionid);
        try {
            HttpResponse getRes = httpClient.execute(get2);
            String returnHTML= EntityUtils.toString(getRes.getEntity());
            Log.i("returnHTML",returnHTML);
            Pattern p = Pattern.compile("\\(学生\\) .+ 成绩查询");
            Matcher m = p.matcher(returnHTML);
            if(m.find()){
                result=true;
            }else{
                result=false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Boolean firstReLogin(SharedPreferences.Editor setting,String user,String pw){
        DefaultHttpClient httpClient=new DefaultHttpClient();
        Boolean result=false;
        HttpPost post;
        HttpGet get;
        int[] black=null;
        HttpResponse getRes=null;
        get=new HttpGet("http://210.30.48.14:8080/ACTIONVALIDATERANDOMPICTURE.APPPROCESS");
        try {
            getRes = httpClient.execute(get);
            HttpEntity entity = getRes.getEntity();
            Bitmap w= BitmapFactory.decodeStream(entity.getContent());
            black=new ImageProcess().blackwhite(135,w);/*识别验证码*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Cookie> cookies = httpClient.getCookieStore().getCookies();
        String sessionid=cookies.get(0).getValue();
        Log.i("cookic",sessionid);
        get.abort();
        post = new HttpPost("http://210.30.48.14:8080/ACTIONLOGON.APPPROCESS");
        post.setHeader("Cookie","JSESSIONID=" + sessionid);
        Log.i("a", "创建了一个Post");
        List<NameValuePair> data=new ArrayList<>();
        if (black != null) {
            data.add(new BasicNameValuePair("Agnomen",""+black[0]+black[1]+black[2]+black[3]));
        }else{
            return false;
        }
        data.add(new BasicNameValuePair("WebUserNO",user));
        data.add(new BasicNameValuePair("Password",pw));
        data.add(new BasicNameValuePair("submit.x","12"));
        data.add(new BasicNameValuePair("submit.y","8"));
        try {
            HttpEntity en = new UrlEncodedFormEntity(data, HTTP.UTF_8);
            Log.i("a",en.toString());
            post.setEntity(en);
            HttpResponse toget=httpClient.execute(post);
            Log.i("StatusCode", String.valueOf(toget.getStatusLine().getStatusCode()));
            post.abort();
            if(toget.getStatusLine().getStatusCode()==200){
                if(useSessionLogin(sessionid)){
                    result=true;
                    setting.putString("Cookie",sessionid);
                    setting.putString("user",sessionid);
                    setting.putString("pw",sessionid);
                    setting.commit();
                }else{
                    result=false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        Log.i("a","完成,即将回调");
        return  result;
    }
}
