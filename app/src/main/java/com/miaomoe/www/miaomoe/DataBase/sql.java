package com.miaomoe.www.miaomoe.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class sql extends SQLiteOpenHelper {
    public sql(Context context, String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `classtable` (\n" +
                "  `id` INTEGER PRIMARY KEY AUTOINCREMENT ,\n" +
                "  `xingqi` char(16) NOT NULL,\n" +
                "  `name` char(80) NOT NULL,\n" +
                "  `jie` char(4) NOT NULL,\n" +
                "  `classroom` char(16) NOT NULL DEFAULT '未知'\n" +

                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
