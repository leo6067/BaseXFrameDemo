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

    //品牌 id
    @ColumnInfo(name = "modelId")
    private String modelId;


    //本地用户 遥控器 取名
    @ColumnInfo(name = "name")
    private String name;

    // 场景   卧室 客厅
    @ColumnInfo(name = "location")
    private String location;

    // 颜色 蓝色 红 粉
    @ColumnInfo(name = "color")
    private int color = 1;

    //类型  1 红外 2 wifi
    @ColumnInfo(name = "type")
    private int type=1;

    // order指令
    @ColumnInfo(name = "orderStr")
    private String orderStr;



    //备用1
    @ColumnInfo(name = "parameter")
    private String parameter;

    //备用2
    @ColumnInfo(name = "parameterB")
    private String parameterB;

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

    @Override
    public String toString() {
        return "RemoteModel{" +
                "uid=" + uid +
                ", brandName='" + brandName + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", color=" + color +
                ", type=" + type +
                ", brandId=" + brandId +
                ", modelId=" + modelId +
                ", parameter=" + parameter +
                ", modelId=" + modelId +
                ", orderStr='" + orderStr + '\'' +
                '}';
    }
}
