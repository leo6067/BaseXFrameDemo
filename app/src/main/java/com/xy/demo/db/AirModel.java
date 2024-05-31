package com.xy.demo.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "air")
public class AirModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name = "uid")
    private int id;


    //品牌
    @ColumnInfo(name = "brandName")
    private String brandName;


//    //品牌
//    @ColumnInfo(name = "brandId")
//    private String brandId;
//
//
////    // 场景   卧室 客厅
////    @ColumnInfo(name = "location")
////    private String location = "1";
////
////
////    //类型  1 红外 2 wifi
////    @ColumnInfo(name = "type")
////    private int type= 1;
////
////    // order指令
////    @ColumnInfo(name = "orderStr")
////    private String orderStr;
////
////
////    //备用1
////    @ColumnInfo(name = "parameter")
////    private String parameter;


    public AirModel( ) {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }


}
