package com.miaomoe.www.miaomoe.html;

import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiuu on 2015/3/17 0017.
 */
public class HtmlTag {
    private String name;
    private Map<String,Object> attribute;/*暂时不实现*/
    private int type=2; /*0 开始 1 结束 2内容 3空*/
    HtmlTag(String info){

/*        info=info.replaceAll(" .+ ","");*/
        Log.i("info,reformat",info.length()+":"+info);
        if(info.length()<3){
            type=3;
        }
        if(info.charAt(0)=='<'){
            if(info.charAt(1)=='/'){
                type=1;
            }else {
                type=0;
            }
        }else {
            type=2;
        }
        if(type!=2){
            Pattern p=Pattern.compile("^<[/!\\s]?(\\S+).*>");
            Matcher mat=p.matcher(info);
            if(mat.find()){
                name=mat.group(1);
            }else{
                type=3;
                name=info;
            }
            Log.i("name,group1",name);
        }else {
            name=info;
        }

    }

    public int getTagType(){
        return type;
    }

    public String getTagName(){
        return name;
    }
}

