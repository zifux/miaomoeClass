package com.miaomoe.www.miaomoe.html;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by xiuu on 2015/3/17 0017.
 */
public class mHtml {
    String Html="";
    ArrayList<HtmlTag> htmlTagList;
    HtmlTree rootTree;
    public mHtml(String html){
        this.Html=html;
        buildTree();
    }
    private void buildList(){
        htmlTagList =new ArrayList<>();
        String tag ="";
        for(int k=0;k<Html.length();k++){
            char it=Html.charAt(k);
            if(it=='<'){
                if(tag.length()>1){
                    htmlTagList.add(new HtmlTag(tag));
                }
                tag="<";
            }else if(it=='>'){
                tag+=it;
                htmlTagList.add(new HtmlTag(tag));
                tag="";
            }else {
                tag+=it;
            }

        }
    }
    private void buildTree(){
        buildList();
        rootTree=new HtmlTree(htmlTagList);
    }

    public String[] getContent(String path) {
        Log.i("mHtml:getContent:node", rootTree.find(path).getNodeName());
        return rootTree.find(path).getNames();
    }

    public void bianli(HtmlNode k){
        if(k.haveChild()){
            Log.i("htmlTree",k.getNodeName());
            String[] l=k.getNames();
            for(int m=0;m<l.length;m++){
                Log.i("TreeNames",l[m]);

            }
        }
    }
    public HtmlNode getRoot(){
        return rootTree.getRoot();
    }
}
