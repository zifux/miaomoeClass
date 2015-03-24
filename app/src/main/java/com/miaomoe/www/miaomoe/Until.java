package com.miaomoe.www.miaomoe;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiuu on 2015/3/16 0016.
 */
public class Until {
    public static Map<String,Object> parseSid(String Sid){
        Map<String,Object> result =new HashMap<>();

        //1305010108
        result.put("YearTermNO",16);
        result.put("DeptNO", Sid.substring(2, 4));
        result.put("ComeYear","20"+ Sid.substring(0, 2));
        result.put("MajorNO", Sid.substring(2, 6));
        result.put("class", Sid.substring(6, 8));
        result.put("number", Sid.substring(8, 10));
        return result;
    }
}
