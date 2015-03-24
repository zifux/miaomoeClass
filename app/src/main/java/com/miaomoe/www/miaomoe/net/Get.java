package com.miaomoe.www.miaomoe.net;

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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiuu on 2015/3/19 0019.
 */
public class Get {
    private HttpGet get=null;
    private DefaultHttpClient httpClient=new DefaultHttpClient();
    private String cookie="";
    private HttpResponse getRes;
    public Get(){
    }
    public Get setUrl(String url){
        if(get!=null){
            get.abort();
        }
        get=new HttpGet(url);
        return this;
    }
    public Get send() throws IOException {
        if(!cookie.isEmpty()){
            get.setHeader("Cookie",cookie);
        }
        if(httpClient!=null){
            getRes=httpClient.execute(get);
        }
        return this;
    }
    public Get setCookie(String cookie1){
        cookie=cookie1;
        return this;
    }
    public String getDataString() throws IOException {
        return EntityUtils.toString(getRes.getEntity());
    }
    public InputStream getDataStream() throws IOException {
        return getRes.getEntity().getContent();
    }
    public List<Cookie> getCookies(){
        return httpClient.getCookieStore().getCookies();
    }
}
