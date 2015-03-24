package com.miaomoe.www.miaomoe.html;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by xiuu on 2015/3/17 0017.
 */
public class HtmlTree {
    private HtmlNode root;
    HtmlTree(ArrayList<HtmlTag> data){
        Stack<HtmlNode> stack=new Stack<>();
        for(int i=0;i<data.size();i++){
            HtmlTag is=data.get(i);
            if(is.getTagType()!=1){
                stack.push(new HtmlNode(is));
            }else{
                Stack<HtmlNode> stack2=new Stack<>();
                for(int s=stack.size()-1;s>0;s--){
                    if(stack.peek().getNodeName().equals(is.getTagName())){
                        while (!stack2.empty()){
                            stack.peek().addChild(stack2.pop());
                        }
                        break;
                    }else {
                        stack2.push(stack.pop());
                    }
                }//查找闭合标签
            }
        }//遍历结束
        root=new HtmlNode(new HtmlTag("root"));
        while (!stack.empty()){
            root.addChild(stack.pop());
        }
        //闭合树
    }

    public HtmlNode find(String path){
        String[] p=path.split("/");
        if(p.length>root.getDeep()){
            throw new Error("路径不存在");
        }
        HtmlNode now=root;
        for(int k=0;k<p.length;k++){
            now=now.findNode(p[k]);
            if(now.get().getTagType()==3){
                throw new Error("路径不存在");
            }
        }
        return now;
    }
    public HtmlNode getRoot(){
        return root;
    }
}
