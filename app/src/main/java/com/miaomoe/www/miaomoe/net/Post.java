package com.miaomoe.www.miaomoe.net;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieSpecRegistry;
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
public class Post {
    private HttpPost post=null;
    private List<NameValuePair> data;
    private DefaultHttpClient httpClient=new DefaultHttpClient();
    private String cookie="";
    private HttpResponse getRes;
    public Post(){
    }
    public Post setUrl(String url){
        if(post!=null){
            post.abort();
        }
        data=new ArrayList<>();
        post=new HttpPost(url);
        return this;
    }
    public Post send() throws IOException {
        if(!data.isEmpty()){
            post.setEntity(new UrlEncodedFormEntity(data, HTTP.UTF_8));
        }
        if(!cookie.isEmpty()){
            post.setHeader("Cookie",cookie);
        }
        if(httpClient!=null) {
            getRes = httpClient.execute(post);
        }
        return this;
    }
    public Post setValue(String name,String value){
        data.add(new BasicNameValuePair(name,value));
        return this;
    }
    public Post setCookie(String cookie1){
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
    public int getStatusCode(){
        return getRes.getStatusLine().getStatusCode();
    }

}
