package com.miaomoe.www.miaomoe.html;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiuu on 2015/3/17 0017.
 */
public class HtmlNode {
    private Tag mine;
    private List<HtmlNode> children;
    private String name;
    private int deep;
    private int childDeep;
    HtmlNode(Tag mine){
        this.name=mine.getTagName();
        this.children=new ArrayList<>();
        this.mine=mine;
        this.deep=0;
    }

    public void addChild(HtmlNode a) {
        if(childDeep<a.getDeep()){
            childDeep = a.getDeep();
        }
        deep=childDeep+1;
        children.add(a);
    }

    public HtmlNode getChild(int i){
        return children.get(i);
    }

    public List<HtmlNode> getChilds(){return children;}

    public Tag get(){
        return mine;
    }

    public int getDeep() {
        return deep;
    }
    public HtmlNode findNode(String nodeName){
        for(int k=0;k<children.size();k++){
            Log.i("HtmlNode:findNodes",children.get(k).getNodeName());
            if(children.get(k).getNodeName().equals(nodeName)){
                return children.get(k);
            }
        }
        return new HtmlNode(new Tag("NULL"));
    }
    public String[] getNames(){
        String[] res=new String[children.size()];
        for(int k=0;k<res.length;k++){
           res[k]=children.get(k).getNodeName();
        }
        return res;
    }
    public String getNodeName(){
        return name;
    }
    public Boolean haveChild(){
       return (children.size()>0);
    }
}
