package com.miaomoe.www.miaomoe;


import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.miaomoe.www.miaomoe.table.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassTable{
    public static void setClassTable(MainActivity a,Table data){
        if(data==null){
            return;
        }
        GridView gv= (GridView) a.findViewById(R.id.class_table_grid);
        /*ScrollView jie= (ScrollView) a.findViewById(R.id.jieScrollView);
        jie.setVerticalScrollbarPosition(20);*/
        mSimpleAdapter kebiao=new mSimpleAdapter(a,getClassData(data),R.layout.item_classtable,R.layout.empty_class,new String[]{"class","name","cls"},new int[]{R.id.class_room,R.id.class_name,R.id.class_cls});
        gv.setAdapter(kebiao);
    }
    private static List<Map<String,Object>> getClassData(Table table){
        ArrayList<Map<String,Object>> data=new ArrayList<>();
        String[] xingqi=new String[]{"一","二","三","四","五"};
        String[] jie=new String[]{"1-2","3-4","5-6","7-8","9-10"};
        for(int i=0;i<5;i++){
            //String[] mm=table.getRow(i);
            //Log.i("classTable:tableInfo",mm[0]+":"+mm[1]+":"+mm[2]+":"+mm[3]+":"+mm[4]+":"+mm[5]+":"+mm[6]+":"+mm[7]+":"+mm[8]);
            for(int k=0;k<5;k++){
                Map<String,Object> item =new HashMap<>();
                String[] tab=table.getItem("星期",xingqi[k],table.getIndex("节次"),jie[i]);
                if(tab==null){
                    item.put("class","");
                    item.put("name","");
                    item.put("cls","");
                }else {
                    item.put("class",tab[table.getIndex("教室")]);
                    item.put("name",tab[table.getIndex("课程名称")]);
                    item.put("cls",jie[i]);
                }
                data.add(item);
            }
        }
        return data;
    }
}
