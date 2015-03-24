package com.miaomoe.www.miaomoe.table;

import android.util.Log;

import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiuu on 2015/3/18 0018.
 */
public class Table {
    private Map<String,String> tableInfo;
    private Map<String,Integer> rowInfoList;
    private List<List<String>> tab;
    private int index=0;
    private int deep=0;
    public Table(){
        this.tab=new ArrayList<>();
        this.tableInfo=new HashMap<>();
        this.rowInfoList=new HashMap<>();
    }
    public void addInfoRow(String rowInfo){
        rowInfoList.put(rowInfo, index);
        index++;
    }
    public void addOneAt(int Row,String data){
        if(Row>=deep){
            for(int i=deep;i<=Row;i++){
                tab.add(new ArrayList<String>());
            }
            deep=tab.size();
        }
        List<String> now=tab.get(Row);
        if(now.size()<=rowInfoList.size()){
            //Log.i("willadd:",data+":"+rowInfoList.get("教室")+":"+tab.size());
            now.add(data);
        }
    }
    public void addTableInfo(String info){
        if(info.isEmpty()){
            return;
        }
        String[] KV=info.split(":");
        if(KV.length==2){
            Log.i("即将添加tableinfo",KV[0]);
            tableInfo.put(KV[0],KV[1]);
        }
    }
    public String getTableInfo(String key){
        Log.i("即将取出信息",key+":"+tableInfo.get(key));
        return tableInfo.get(key);
    }
    public int getIndex(String name){
        return rowInfoList.get(name);
    }
    public String[] getRow(int Row){
       return  tab.get(Row).toArray(new String[index+1]);
    }
    public String getOne(int Row,String name){
        List<String> temp=tab.get(Row);
        if(temp==null){return null;}
        else{
            return  temp.get(rowInfoList.get(name));
        }
    }
    public String[] getItem(String s1,String s3,int s4,String s2){
        for(int m=0;m<tab.size();m++){
            String now=tab.get(m).get(rowInfoList.get(s1));
            if(now.equals(s3)){
                if(tab.get(m).get(s4).equals(s2)){
                    return getRow(m);
                }
            }
        }
        return null;
    }
    public int[] size(){
        return new int[]{rowInfoList.size(),tab.size()};
    }

    public static Table creatscoreTable(Elements dataList){
        Table scoreTable=new Table();
        for(int k=0;k<dataList.size()-2;k++){
            Elements tr=dataList.get(k).getElementsByTag("td");
            for(int i=0;i<tr.size();i++){
                String now=tr.get(i).text();
                //Log.i("Login:text",tr.get(i).text()+"\t\t\t\t\t\t\t -"+k+":"+i+":"+tr.get(i).text().length());
                if(!now.isEmpty()){
                    if(k==1||k==2){
                        scoreTable.addTableInfo(now.trim());
                    }else if(k==3){
                        scoreTable.addInfoRow(now.trim());
                    }else if(k>3){
                        scoreTable.addOneAt(k-4,now.trim());
                    }
                }

            }
        }
        return scoreTable;
    }
    public static Table creatClassTable(Elements dataList){
        Table classTable=new Table();
        int spik=0;
        Pattern p = Pattern.compile("通选|网络");
        String xingqi="一";
        for(int k=0;k<dataList.size();k++){
            Elements tr=dataList.get(k).getElementsByTag("td");
            if(!p.matcher(tr.get(1).text()).find()){
                for(int i=0;i<tr.size();i++){
                    String now=tr.get(i).text();
                    //Log.i("creatClassTable:text",tr.get(i).text()+"\t\t\t\t\t\t\t -"+k+":"+i+":"+tr.get(i).text().length());
                    if(k==0){
                        classTable.addInfoRow(now.trim());
                    }else{
                        if(i==0){
                            if(!now.isEmpty()&&!now.equals(xingqi)){
                                xingqi=now;
                            }
                            classTable.addOneAt(k-1-spik,xingqi.trim());
                        }else {
                            classTable.addOneAt(k-1-spik,now.trim());
                        }
                    }
                }
            }else{
                spik++;}
        }
        return classTable;
    }

}
