package com.miaomoe.www.miaomoe;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;

/**
 * Created by xiuu on 2015/2/24 0024.
 */

public class class1 extends Fragment {
    private MainActivity f;
    //private mSimpleAdapter classList;
    class1(MainActivity a/*,mSimpleAdapter classList*/){
       this.f=a;
        //this.classList=classList;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View theView=inflater.inflate(R.layout.class1_2, container, false);
        GridView grid= (GridView) theView.findViewById(R.id.gridView1);
        mSimpleAdapter classList=new mSimpleAdapter(getActivity(),f.dataList1,R.layout.item_classroom,new String[]{"class","name","cls"},new int[]{R.id.class_room,R.id.class_name,R.id.class_cls});
        grid.setAdapter(classList);
        grid.setOnItemClickListener(new ClassRoomInfo(getActivity()));
        return theView;
    }

}
