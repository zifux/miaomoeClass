
package com.miaomoe.www.miaomoe;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

import java.util.List;


public class NetPost extends AsyncTask<Integer, Integer, String> {
    private MainActivity.mHandler mainHandler;
    private Activity context;
    NetPost(MainActivity.mHandler a,Activity b){
        Log.i("a","已经创建后台");
        this.mainHandler=a;
        this.context=b;
    }
    @Override
    protected String doInBackground(Integer... params) {
        String result="";
        HttpPost getClass;
        getClass = new HttpPost("http://www.miaomoe.com/talk/classa");
        Log.i("a","创建了一个Post");
        List<NameValuePair> data=new ArrayList<>();
        data.add(new BasicNameValuePair("z",params[0].toString()));
        data.add(new BasicNameValuePair("x",params[1].toString()));
        String res="[{\"name\":\"NO_DATA\"},{\"name\":\"NO_DATA\"}]";;
        try {
            HttpEntity en = new UrlEncodedFormEntity(data, HTTP.UTF_8);
            Log.i("a",en.toString());
            getClass.setEntity(en);
            HttpResponse toget=new DefaultHttpClient().execute(getClass);
            if(toget.getStatusLine().getStatusCode()==200){
                res=EntityUtils.toString(toget.getEntity());
                Log.i("a",res);
                 //(JSONArray) new JSONTokener(res).nextValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            res="[{\"name\":\"NO_DATA\"},{\"name\":\"NO_DATA\"}]";
        }
        result=res;
        Log.i("a","完成,即将回调");
        return  result;
    }

    protected void onProgressUpdate(Integer... progress) {
        Log.i("Update","更新"+progress[0]);
    }

    private void showDialog(String s) {
        Toast.makeText(this.context,s,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(String jsonArray) {
        Log.i("a","完成,正在回调");
        Message msg=new Message();
        Bundle bun=new Bundle();
        bun.putString("data",jsonArray);
        msg.setData(bun);
        mainHandler.sendMessage(msg);
    }
}