package com.miaomoe.www.miaomoe;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ClassRoom {

    public JSONArray data;
    public MainActivity activity;
    public ClassRoom(MainActivity activity){
        this.activity=activity;
    }
    public void toUpdate(JSONArray json){
        if(json==null){
            return;
        }
        data=json;
        updateView();
    }

    public void updateView(){
        try {
            setClassData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Log.i("datalist1", (String) dataList5.get(0).get("name"));
        activity.upClassRoomView();
    }

    public void setClassData() throws JSONException {
        int s;
        int e;
        int next,last=10,lat,k;
        String name;
        int classRoom;
        activity.dataList1=new ArrayList<>();
        activity.dataList2=new ArrayList<>();
        activity.dataList3=new ArrayList<>();
        activity.dataList4=new ArrayList<>();
        activity.dataList5=new ArrayList<>();
        for(int i=0;i<data.length();i++){
            try {
                s=data.getJSONObject(i).getJSONObject("cla").getInt("start");
            } catch (JSONException e1) {
                e1.printStackTrace();
                s=0;
            }
            try {
                e=data.getJSONObject(i).getJSONObject("cla").getInt("end");
            } catch (JSONException e1) {
                e1.printStackTrace();
                e=0;
            }
            //Log.i("开始节", String.valueOf(s));
            if(1<=s&&e<=2){
                last=setDataList(activity.dataList1,i,last,s,e)+1;
            }else if(3<=s&&e<=4){
                last=setDataList(activity.dataList2,i,last,s,e)+1;
            }else if(5<=s&&e<=6){
                last=setDataList(activity.dataList3,i,last,s,e)+1;
            }else if(7<=s&&e<=8){
                last=setDataList(activity.dataList4,i,last,s,e)+1;
            }else if(9<=s&&e<=10){
                last=setDataList(activity.dataList5,i,last,s,e)+1;
            }

        }
    }

    private int setDataList(List<Map<String,Object>> dataList, int i,int last,int s,int e) throws JSONException {
        int classRoom,next;
        String[] classTypes={"专业限选","专业任选","专业必修","通识必修","学科基础","其他"};
        String[] testTypes={"考查","考试"};
        /*String[] learnTypes={"理论课"};*/
        String name,classRoomS;
        Map<String,Object> map=new HashMap<>();
        name=data.getJSONObject(i).getString("name");
        classRoomS=data.getJSONObject(i).getString("where");
        if(classRoomS.equals("null")){
            classRoomS="未知";
            next=10;
        }else{
            classRoom= Integer.parseInt(classRoomS);
            next=classRoom%100;
            for(;last<next;++last){
                String emptyRoom=String.valueOf(classRoom-(classRoom%100)+last).replaceFirst("^1","A").replaceFirst("^2","B").replaceFirst("^3","C");
                Map<String,Object> inMap= new HashMap<>();
                inMap.put("class", emptyRoom);
                inMap.put("name","空");
                inMap.put("cls",s+"-"+e);
                dataList.add(inMap);
            }
        }
        String classroom=classRoomS.replaceFirst("^1","A").replaceFirst("^2","B").replaceFirst("^3","C");
        String toClasses="";
        JSONArray temp=data.getJSONObject(i).getJSONArray("toClass");
        int tempL=temp.length();
        for (int m=0;m<tempL;m++){
            toClasses+="\n\t"+(m+1)+"."+temp.get(m);
        }

        map.put("id",data.getJSONObject(i).getString("id"));
        map.put("class", classroom);
        map.put("name", name);
        map.put("cls",s+"-"+e);
        map.put("teacher",data.getJSONObject(i).getString("teacher"));
        map.put("toClass",toClasses);
        map.put("learnTime",data.getJSONObject(i).getString("learnTime"));
        map.put("testType",testTypes[data.getJSONObject(i).getInt("testType")]);
        map.put("classType",classTypes[data.getJSONObject(i).getInt("classType")]);
        dataList.add(map);
        return next;
    }
}
