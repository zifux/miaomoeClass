package com.miaomoe.www.miaomoe;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiuu on 2015/3/16 0016.
 */
public class Until {
    public static Map<String,Object> parseSid(int Sid){
        Map<String,Object> result =new HashMap<>();
        String id=String.valueOf(Sid);
        //1305010108
        result.put("YearTermNO",16);
        result.put("DeptNO",id.substring(3,4));
        result.put("ComeYear","20"+id.substring(1,2));
        result.put("MajorNO",id.substring(3,6));
        result.put("class",id.substring(7,8));
        result.put("number",id.substring(9,10));
        return result;
    }
}
