package com.miaomoe.www.miaomoe;

import android.app.Activity;
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
public class NetLogin extends AsyncTask<Integer, Integer,int[]>  {
    private Activity context;
    private Handler mainHandler;
    NetLogin(Handler a,Activity b){
        Log.i("a","已经创建登录后台");
        this.mainHandler=a;
        this.context=b;
    }

        @Override
        protected int[] doInBackground(Integer... params) {
            String result="";
            HttpPost post;
            HttpGet get,get3;
            int[] black=null;
            HttpResponse getRes=null;
            DefaultHttpClient httpClient=new DefaultHttpClient();
            /*get3=new HttpGet("http://210.30.48.14:8080/ACTIONLOGON.APPPROCESS");
            try {
                httpClient.execute(get3);
            } catch (IOException e) {
                e.printStackTrace();
            }
            get3.abort();*/

            get=new HttpGet("http://210.30.48.14:8080/ACTIONVALIDATERANDOMPICTURE.APPPROCESS");
           /* get.setHeader("Cookie","JSESSIONID=" + sessionid);*/
            try {
                getRes = httpClient.execute(get);
                HttpEntity entity = getRes.getEntity();
                Bitmap w= BitmapFactory.decodeStream(entity.getContent());
                Log.i("wwww", w.toString());
                black=new ImageProcess().blackwhite(135,w);
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
            data.add(new BasicNameValuePair("Agnomen",""+black[0]+black[1]+black[2]+black[3]));
            data.add(new BasicNameValuePair("WebUserNO","1305010317"));
            data.add(new BasicNameValuePair("Password","2813333"));
            data.add(new BasicNameValuePair("submit.x","12"));
            data.add(new BasicNameValuePair("submit.y","8"));
            String res="";
            InputStream is;
            String kkk;
            try {
                HttpEntity en = new UrlEncodedFormEntity(data, HTTP.UTF_8);
                Log.i("a",en.toString());
                post.setEntity(en);
                HttpResponse toget=httpClient.execute(post);
                Log.i("StatusCode", String.valueOf(toget.getStatusLine().getStatusCode()));
                post.abort();
                if(toget.getStatusLine().getStatusCode()==200){
                    //is = toget.getEntity().getContent();
                    sessionid=cookies.get(0).getValue();
                    Log.i("cookic",sessionid);
                    HttpGet get2=new HttpGet("http://210.30.48.14:8080/ACTIONQUERYSTUDENTSCORE.APPPROCESS");
                    get2.setHeader("Cookie", "JSESSIONID=" + sessionid);
                    try {
                        getRes = httpClient.execute(get2);
                        kkk= EntityUtils.toString(getRes.getEntity());
                        Log.i("kkk",kkk);
                        Pattern p = Pattern.compile("\\(学生\\) .+ 成绩查询");
                        Matcher m = p.matcher(kkk);
                        if(m.find()){
                            Log.i("xuesheng",m.group());
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    /*SAXParserFactory sax=SAXParserFactory.newInstance();
                    try {
                        SAXParser parser=sax.newSAXParser();
                        InputSource source=new InputSource(new StringReader(kkk));

                        parser.parse(source,new xmlHandler());
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    }*/
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            Log.i("a","完成,即将回调");
            result=res;
            return  black;
        }

    protected void onProgressUpdate(Integer... progress) {
        Log.i("Update","更新"+progress[0]);

    }

    @Override
    protected void onPostExecute(int[] jsonArray) {
        Log.i("a","完成,正在回调");
        Message msg=new Message();
        Bundle bun=new Bundle();
        bun.putIntArray("pic",jsonArray);
        msg.setData(bun);
        mainHandler.sendMessage(msg);
    }

    class xmlHandler extends DefaultHandler {
        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            Log.i("sax",qName);
        }

    }
}
