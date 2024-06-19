package com.xy.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.tom_roush.pdfbox.pdmodel.common.PDRectangle;

import java.io.Serializable;
import java.util.ArrayList;

public class ImagePdfParam implements Serializable {

    public ArrayList<String> imgPathList;

    public String name;
    public String pwd="";
    public int direction =1;  //方向 :1 纵向  2 横向

    public int pageSize = 0;
    public boolean encryptStatus = false;

    public String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isEncryptStatus() {
        return encryptStatus;
    }

    public void setEncryptStatus(boolean encryptStatus) {
        this.encryptStatus = encryptStatus;
    }

    public ArrayList<String> getImgPathList() {
        return imgPathList;
    }

    public void setImgPathList(ArrayList<String> imgPathList) {
        this.imgPathList = imgPathList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
