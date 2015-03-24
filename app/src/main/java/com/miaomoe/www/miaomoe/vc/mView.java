package com.miaomoe.www.miaomoe.vc;

import android.app.Activity;
import android.content.Intent;

import org.jsoup.select.Evaluator;

/**
 * Created by xiuu on 2015/3/21 0021.
 */
public class mView {
    private Class<?> ma=null;
    private Activity memiter=null;
    private int mreqCode;

    mView(Class<?> f){
        ma=f;
    }
    public mView setEmiter(Activity emiter,int reqCode){
        memiter=emiter;
        mreqCode=reqCode;
        return this;
    }
    public void start(){
        Intent willStart=new Intent(memiter,ma);
        memiter.startActivityForResult(willStart,mreqCode);
    }
}
