package com.xy.demo.db;


//遥控器 实体


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * @PrimaryKey(autoGenerate = true)表示主键自增，参数autoGenerate表示主键可以由数据库自动生成
 * @ColumnInfo(name = "id"）表示列的名字为id
 * @Ignore表示会忽略这个字段，不进行记录 https://zhuanlan.zhihu.com/p/629332938
 */
@Entity(tableName = "junk")
public class JunkModel implements Serializable {


    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name = "uid")
    private int id;



    //垃圾类型   缓存垃圾  应用垃圾

    //此处兼容 多语言 1：文件垃圾 2：缓存垃圾
    @ColumnInfo(name = "junkName")
    public String junkName;


    // 垃圾回收时间
    @ColumnInfo(name = "recycleTime")
    private long recycleTime;


    // 垃圾大小
    @ColumnInfo(name = "junkSize")
    public Long junkSize;




    // 垃圾
    @ColumnInfo(name = "junkIcon")
    public int junkIcon;



    // 备用参数
    @ColumnInfo(name = "paramStr")
    private String paramStr;


    @ColumnInfo(name = "isCheck")
    public boolean isCheck;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJunkName() {
        return junkName;
    }

    public void setJunkName(String junkName) {
        this.junkName = junkName;
    }

    public long getRecycleTime() {
        return recycleTime;
    }

    public void setRecycleTime(long recycleTime) {
        this.recycleTime = recycleTime;
    }

    public Long getJunkSize() {
        return junkSize;
    }

    public void setJunkSize(Long junkSize) {
        this.junkSize = junkSize;
    }

    public int getJunkIcon() {
        return junkIcon;
    }

    public void setJunkIcon(int junkIcon) {
        this.junkIcon = junkIcon;
    }

    public String getParamStr() {
        return paramStr;
    }

    public void setParamStr(String paramStr) {
        this.paramStr = paramStr;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }



}
