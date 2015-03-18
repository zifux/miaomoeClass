package com.miaomoe.www.miaomoe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiuu on 2015/3/15 0015.
 */
public class ClassTable{
    private MainActivity f;
    ClassTable(MainActivity a){
        this.f=a;
        GridView gv= (GridView) f.findViewById(R.id.class_table_grid);
        ScrollView jie= (ScrollView) f.findViewById(R.id.jieScrollView);
        jie.setVerticalScrollbarPosition(20);
        mSimpleAdapter kebiao=new mSimpleAdapter(f,getClassData(),R.layout.item_classtable,new String[]{"class","name","cls"},new int[]{R.id.class_room,R.id.class_name,R.id.class_cls});
        gv.setAdapter(kebiao);
    }
    ArrayList<Map<String,Object>> data=new ArrayList<>();
    private List<Map<String,Object>> getClassData(){
        for(int i=0;i<100;i++){
            Map<String,Object> k =new HashMap<>();
            k.put("class","A121");
            k.put("name","zixi");
            k.put("cls","3-4");
            data.add(k);
        }
        return data;
    }
}
