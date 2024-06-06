package com.xy.demo.db;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Set;


@Entity(tableName = "pdfFile")
public class PdfFileModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name = "uid")
    private int uid;


    @ColumnInfo(name = "file_name")
    public String name;
    @ColumnInfo(name = "last_time")
    public String lastTime;

    @ColumnInfo(name = "total_pages")
    public String totalPages;  //页数

    @ColumnInfo(name = "file_path")
    public String path;
    @ColumnInfo(name = "isCollect")
    public String isCollect;
    @ColumnInfo(name = "file_size")
    public String size;
    @ColumnInfo(name = "bitmap")
    public byte[] bitmap;  //缩略图



    //文件操作处理类型 ---如：pdf 转 图片
    @ColumnInfo(name = "deal_type")
    public int dealType;


    @ColumnInfo(name = "select_status")
    public boolean selectStatus;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(String isCollect) {
        this.isCollect = isCollect;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public byte[] getBitmap() {
        return bitmap;
    }

    public void setBitmap(byte[] bitmap) {
        this.bitmap = bitmap;
    }


    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public int getDealType() {
        return dealType;
    }

    public void setDealType(int dealType) {
        this.dealType = dealType;
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

    public boolean isSelectStatus() {
        return selectStatus;
    }

    public void setSelectStatus(boolean selectStatus) {
        this.selectStatus = selectStatus;
    }
}
