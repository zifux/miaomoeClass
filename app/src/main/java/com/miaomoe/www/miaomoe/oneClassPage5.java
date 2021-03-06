package com.miaomoe.www.miaomoe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


public class oneClassPage5 extends Fragment {
    private MainActivity f;
    public static oneClassPage createClassPage(MainActivity data){
        oneClassPage in=new oneClassPage();
        in.f=data;
        return in;
    }
    public oneClassPage5(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View theView=inflater.inflate(R.layout.class1_2, container, false);
        GridView grid= (GridView) theView.findViewById(R.id.gridView1);
        mSimpleAdapter classList=new mSimpleAdapter(getActivity(),f.dataList5,R.layout.item_classroom,R.layout.empty_item_classroom,new String[]{"class","name","cls"},new int[]{R.id.class_room,R.id.class_name,R.id.class_cls});
        grid.setAdapter(classList);
        grid.setOnItemClickListener(new ClassRoomInfo(getActivity()));
        return theView;
    }
}
