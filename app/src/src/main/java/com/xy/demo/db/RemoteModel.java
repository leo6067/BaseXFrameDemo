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
@Entity(tableName = "remote")
public class RemoteModel implements Serializable {


    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name = "uid")
    private int uid;


    //品牌
    @ColumnInfo(name = "brandName")
    private String brandName;
    //品牌 id
    @ColumnInfo(name = "brandId")
    private String brandId;

    //系列 id
    @ColumnInfo(name = "modelId")
    private String modelId;


    //本地用户 遥控器 取名
    @ColumnInfo(name = "name")
    private String name;

    // 场景   卧室 客厅
    @ColumnInfo(name = "location")
    private String location = "1";

    // 颜色 蓝色 红 粉
    @ColumnInfo(name = "color")
    private int color = 1;

    //类型  1 红外电视遥控器 2 wifi  3 红外空调遥控器
    @ColumnInfo(name = "type")
    private int type = 1;

    // order指令
    @ColumnInfo(name = "orderStr")
    private String orderStr;


    //温度 风速 扫风
    @ColumnInfo(name = "tcInt")
    private int tcInt = 26;
    @ColumnInfo(name = "speedInt")
    private int speedInt;

    @ColumnInfo(name = "swingInt")
    private int swingInt;


    //制冷模式
    @ColumnInfo(name = "modeInt")
    private int modeInt;

    @ColumnInfo(name = "isOpen")
    private int isOpen;
    @ColumnInfo(name = "isNewAc")
    private int isNewAc;


    //备用1
    @ColumnInfo(name = "parameter")
    private String parameter;

    //备用2
    @ColumnInfo(name = "parameterB")
    private String parameterB;


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public int getTcInt() {
        return tcInt;
    }

    public void setTcInt(int tcInt) {
        this.tcInt = tcInt;
    }

    public int getSpeedInt() {
        return speedInt;
    }

    public void setSpeedInt(int speedInt) {
        this.speedInt = speedInt;
    }

    public int getSwingInt() {
        return swingInt;
    }

    public void setSwingInt(int swingInt) {
        this.swingInt = swingInt;
    }

    public int getModeInt() {
        return modeInt;
    }

    public void setModeInt(int modeInt) {
        this.modeInt = modeInt;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    public int getIsNewAc() {
        return isNewAc;
    }

    public void setIsNewAc(int isNewAc) {
        this.isNewAc = isNewAc;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getParameterB() {
        return parameterB;
    }

    public void setParameterB(String parameterB) {
        this.parameterB = parameterB;
    }


    @Override
    public String toString() {
        return "RemoteModel{" +
                "uid=" + uid +
                ", brandName='" + brandName + '\'' +
                ", brandId='" + brandId + '\'' +
                ", modelId='" + modelId + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", color=" + color +
                ", type=" + type +
                ", orderStr='" + orderStr + '\'' +
                ", tcInt=" + tcInt +
                ", speedInt=" + speedInt +
                ", swingInt=" + swingInt +
                ", modeInt=" + modeInt +
                ", isOpen=" + isOpen +
                ", isNewAc=" + isNewAc +
                ", parameter='" + parameter + '\'' +
                ", parameterB='" + parameterB + '\'' +
                '}';
    }
}
