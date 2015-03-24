package com.miaomoe.www.miaomoe;

import android.content.SharedPreferences;

import com.miaomoe.www.miaomoe.table.Table;

/**
 * Created by xiuu on 2015/3/20 0020.
 */
public class My {
    private MainActivity f;
    private Table data;
    public My(MainActivity a,Table k){
        f=a;
        data=k;
    }
    public void setMyInfo(){
        if(data==null){
            return;
        }
        SharedPreferences shared=f.getSharedPreferences("user",0);
        SharedPreferences.Editor editor=shared.edit();
        editor.putString("姓名",data.getTableInfo("姓名"));
        editor.putString("班级",data.getTableInfo("班级"));
        editor.putString("院系",data.getTableInfo("院系"));
        editor.putString("学号",data.getTableInfo("学号"));
        editor.putString("专业",data.getTableInfo("专业"));
        editor.commit();
        f.my_xingming.setText(shared.getString("姓名","无"));
        f.my_banji.setText(shared.getString("班级","无"));
        f.my_yuanxi.setText(shared.getString("院系","无"));
        f.my_xuehao.setText(shared.getString("学号","无"));
        f.my_zhuanye.setText(shared.getString("专业","无"));


    }
}
