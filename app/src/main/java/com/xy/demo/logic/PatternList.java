package com.xy.demo.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangr on 2019/8/29.
 * ClassName  : PatternList
 * Description  : 封装list和totalTime，方便统计和获取list元素中数据之和
 */

public class PatternList {
    private List<Integer> list;
    private int totalTime;

    public PatternList() {
        list = new ArrayList<>();
        totalTime = 0;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public void clear(){
        list.clear();
        totalTime = 0;
    }

    public void add(Integer Integer) {
        list.add(Integer);
        totalTime += Integer;
    }

    public Integer get(int index) {
        return list.get(index);
    }

    public int size(){
        return list.size();
    }

    public String toString(){
        return "" + list;
    }

}
