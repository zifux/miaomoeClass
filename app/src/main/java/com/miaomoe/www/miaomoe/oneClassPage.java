package com.miaomoe.www.miaomoe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;


public class oneClassPage extends Fragment {
    public MainActivity f;
    public oneClassPage(){}
    public static oneClassPage createClassPage(MainActivity data){
        oneClassPage in=new oneClassPage();
        in.f=data;
        return in;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View theView=inflater.inflate(R.layout.class1_2, container, false);
        GridView grid= (GridView) theView.findViewById(R.id.gridView1);
        mSimpleAdapter classList=new mSimpleAdapter(getActivity(),f.dataList1,R.layout.item_classroom,R.layout.empty_item_classroom,new String[]{"class","name","cls"},new int[]{R.id.class_room,R.id.class_name,R.id.class_cls});
        grid.setAdapter(classList);
        grid.setOnItemClickListener(new ClassRoomInfo(getActivity()));
        return theView;
    }
}
