package com.xy.demo.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


// 本地原始 文档 数据
@Entity(tableName = "sdFile")
public class SDPdfFileModel {

    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name = "uid")
    private int uid;


    @ColumnInfo(name = "file_name")
    public String name;
    @ColumnInfo(name = "create_time")
    public String createTime;


    @ColumnInfo(name = "edit_time")
    public String editTime;


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
    @ColumnInfo(name = "directory")
    public boolean directory = false;  //是否是内存文件目录


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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
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

    public boolean isDirectory() {
        return directory;
    }

    public void setDirectory(boolean directory) {
        this.directory = directory;
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
}
