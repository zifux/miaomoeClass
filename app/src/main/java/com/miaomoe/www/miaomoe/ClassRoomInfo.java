package com.miaomoe.www.miaomoe;


import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.Map;

public class ClassRoomInfo implements AdapterView.OnItemClickListener {
    private Activity self;
    ClassRoomInfo(Activity a){
        this.self=a;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String,?> info=((mSimpleAdapter) parent.getAdapter()).getmData(position);
        TextView tex=(TextView)view.findViewById(R.id.class_name);
        if(tex.getText()!="空"){
            AlertDialog.Builder show = new AlertDialog.Builder(self)
                    .setMessage(""+"课程ID:"+info.get("id")
                            +"\n"+"名称:"+info.get("name")
                            +"\n"+"老师:"+info.get("teacher")
                            +"\n"+"授课班级:"+info.get("toClass")
                            +"\n"+"课时:"+info.get("learnTime")
                            +"\n"+"考核方式:"+info.get("testType")
                            +"\n"+"课程性质:"+info.get("classType"))
                    .setPositiveButton("知道了", null);
            show.show();
        }
    }
}
