package com.miaomoe.www.miaomoe;

/**
 * Created by xiuu on 2015/3/19 0019.
 */
public class mRes {
    private Object object;
    private int name;
    public mRes(){
    }
    public mRes put(int name,Object object){
        this.object=object;
        this.name=name;
        return this;
    }
    public Object get(){
        return object;
    }
    public int name(){
        return name;
    }


}
