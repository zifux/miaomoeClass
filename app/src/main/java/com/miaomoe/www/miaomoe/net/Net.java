package com.miaomoe.www.miaomoe.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.miaomoe.www.miaomoe.DataBase.mSql;
import com.miaomoe.www.miaomoe.ImageProcess;
import com.miaomoe.www.miaomoe.Until;
import com.miaomoe.www.miaomoe.table.Table;

import org.apache.http.cookie.Cookie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Net {
    private Post post =new Post();
    private Get get=new Get();

    public Boolean useSessionLogin(String sessionid){
        Boolean result=false;
        try {
            String returnHTML=get.setUrl("http://210.30.48.14:8080/ACTIONQUERYSTUDENTSCORE.APPPROCESS")
                    .setCookie("JSESSIONID=" + sessionid)
                    .send()
                    .getDataString();
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

    public Boolean firstReLogin(SharedPreferences setting,String user,String pw){
        Boolean result=false;
        int[] black=null;
        String oldCookie=setting.getString("Cookie",null);
        if(oldCookie!=null&&useSessionLogin(oldCookie)){
            return true;
        }
        try {
            InputStream yanzhengma=get
                    .setUrl("http://210.30.48.14:8080/ACTIONVALIDATERANDOMPICTURE.APPPROCESS")
                    .send()
                    .getDataStream();
            Bitmap w= BitmapFactory.decodeStream(yanzhengma);
            black= ImageProcess.blackwhite(135, w);/*识别验证码*/
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Cookie> cookies =get.getCookies();
        String sessionid=cookies.get(0).getValue();
        post.setUrl("http://210.30.48.14:8080/ACTIONLOGON.APPPROCESS").setCookie("JSESSIONID=" + sessionid);
        Log.i("a", "创建了一个Post");
        try {
            if (black != null) {
                post
                        .setValue("Agnomen",""+black[0]+black[1]+black[2]+black[3])
                        .setValue("WebUserNO",user)
                        .setValue("Password",pw)
                        .setValue("submit.x","12")
                        .setValue("submit.y","8")
                        .send();
            }else{
                return false;
            }
            if(post.getStatusCode()==200){
                if(useSessionLogin(sessionid)){
                    result=true;
                    setting.edit()
                            .putString("Cookie", sessionid)
                            .putString("user", user)
                            .putString("pw", pw)
                            .commit();
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

    public Table getStudentScore(String sessionid,String from,String to){
        Table scoreTable=null;
        if(sessionid==null){
            return null;
        }
        post.setUrl("http://210.30.48.14:8080/ACTIONQUERYSTUDENTSCORE.APPPROCESS?mode=2");

        try {
            post
                    .setValue("YearTermN1",to)
                    .setValue("YearTermNO",from)
                    .setValue("x", "30")
                    .setValue("y","19")
                    .setCookie("JSESSIONID=" + sessionid)
                    .send();
            if(post.getStatusCode()==200){
                String webHtml=post.getDataString();
                Document doc=Jsoup.parse(webHtml);
                Elements dataList=doc.select("html body table tbody tr td table tbody tr");
                scoreTable=Table.creatscoreTable(dataList);
                Log.i("Login:getRow",scoreTable.getRow(1)[8]);
                /*mHtml scoreTree=new mHtml(webHtml);
                scoreTree.bianli(scoreTree.getRoot());
                String[] score=scoreTree.getContent("html/body/table");
                for (int k=0;k<score.length;k++){
                    Log.i("html/body",score[k]);
                }*/
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return scoreTable;
    }

    public JSONArray getRoom(String z,String x){
        JSONArray result=null;
        String res="";
        try {
            post
                    .setUrl("http://www.miaomoe.com/talk/classa")
                    .setValue("z",z)
                    .setValue("x",x)
                    .send();
            if(post.getStatusCode()==200){
                res=post.getDataString();
                //(JSONArray) new JSONTokener(res).nextValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            res="[{\"name\":\"NO_DATA\"},{\"name\":\"NO_DATA\"}]";
        }
        Log.i("NetJson",res);
        try {
            result=(JSONArray) new JSONTokener(res).nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Table getClassTable(String sessionid,String Sid){
        Table classTable=null;
        Log.i("getClassTable","正在获取getclasstable");
        if(sessionid==null||Sid==null){
            Log.i("getClassTable",""+sessionid+Sid);
            return null;
        }
        post.setUrl("http://210.30.48.14:8080/ACTIONQUERYMAJORSCHEDULE.APPPROCESS?mode=2&query=1");
        Map<String,Object> studentSid= Until.parseSid(Sid);
        try {
            post
                    .setValue("YearTermNO",studentSid.get("YearTermNO").toString())
                    .setValue("DeptNO",studentSid.get("DeptNO").toString())
                    .setValue("ComeYear",studentSid.get("ComeYear").toString())
                    .setValue("MajorNO",studentSid.get("MajorNO").toString())
                    .setCookie("JSESSIONID=" + sessionid)
                    .send();
            if(post.getStatusCode()==200){
                String webHtml=post.getDataString();
                Document doc=Jsoup.parse(webHtml);
                Elements dataList=doc.select("html body table tbody tr td table.tableborder tbody tr");
                classTable=Table.creatClassTable(dataList);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Log.i("getClassTable","已经获取getclasstable"+(classTable==null));
        return classTable;
    }
    public Table[] initLogin(SharedPreferences setting,String from,String to,Context context){
        String sessionid=setting.getString("Cookie",null);
        String user=setting.getString("user",null);
        Boolean indb=setting.getBoolean("classTableInDB", false);
        Table classtable=null;
        Table scoretable=null;
        if(sessionid==null||!useSessionLogin(sessionid)){
            firstReLogin(setting,user,setting.getString("pw",null));
        }
        sessionid=setting.getString("Cookie",null);
        scoretable=getStudentScore(sessionid,from,to);
        if(indb){
            classtable=new mSql(context,"school").getClassTable();

        }else {
            classtable=getClassTable(sessionid,user);
        }
        return new Table[]{classtable,scoretable};
    }
}
