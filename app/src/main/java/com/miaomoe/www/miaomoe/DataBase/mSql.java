package com.miaomoe.www.miaomoe.DataBase;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.miaomoe.www.miaomoe.table.Table;

/**
 * Created by xiuu on 2015/3/19 0019.
 */
public class mSql {
    SQLiteDatabase mdb=null;
    public mSql(Context context, String name){
        if(context==null||name==null){
            throw new Error("不存在context或name");
        }
        mdb=new sql(context,name).getWritableDatabase();
    }
    public mSql logOut(String name){
        mdb.delete(name,null,null);
        return this;
    }
    public mSql addClassTable(Table classtable){
        ContentValues values=new ContentValues();
        for(int k=0;k<classtable.size()[1];k++){
            values.clear();
            //xingqi not null,name char(80) not null,jie char(4) not null,classroom
            values.put("xingqi",classtable.getOne(k,"星期"));
            values.put("name",classtable.getOne(k,"课程名称"));
            values.put("jie",classtable.getOne(k,"节次"));
            values.put("classroom",classtable.getOne(k,"教室"));
            mdb.insert("classtable",null,values);
        }
        return this;
    }
    public Table getClassTable(){
        Table table=new Table();
        table.addInfoRow("星期");
        table.addInfoRow("课程名称");
        table.addInfoRow("节次");
        table.addInfoRow("教室");
        Cursor tab=mdb.query("classtable",null,null,null,null,null,null);
        int row=0;
        while (tab.moveToNext()){
            //xingqi not null,name char(80) not null,jie char(4) not null,classroom
            table.addOneAt(row,tab.getString(tab.getColumnIndex("xingqi")));
            table.addOneAt(row, tab.getString(tab.getColumnIndex("name")));
            table.addOneAt(row, tab.getString(tab.getColumnIndex("jie")));
            table.addOneAt(row, tab.getString(tab.getColumnIndex("classroom")));
            Log.i("msql","正在获得"+row+":"+tab.getString(1));
            row++;
        }
        tab.close();
        return table;
    }
}
