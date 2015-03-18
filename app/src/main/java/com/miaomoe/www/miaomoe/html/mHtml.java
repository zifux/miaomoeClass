package com.miaomoe.www.miaomoe.html;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiuu on 2015/3/17 0017.
 */
public class mHtml {
    String Html="";
    ArrayList<Tag> tagList;
    HtmlTree rootTree;
    public mHtml(String html){
        this.Html=html;
        buildTree();
    }
    private void buildList(){
        tagList=new ArrayList<>();
        String tag ="";
        for(int k=0;k<Html.length();k++){
            char it=Html.charAt(k);
            if(it=='<'){
                if(tag.length()>1){
                    tagList.add(new Tag(tag));
                }
                tag="<";
            }else if(it=='>'){
                tag+=it;
                tagList.add(new Tag(tag));
                tag="";
            }else {
                tag+=it;
            }

        }
    }
    private void buildTree(){
        buildList();
        rootTree=new HtmlTree(tagList);
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
