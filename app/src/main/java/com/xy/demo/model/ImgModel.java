package com.xy.demo.model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class ImgModel implements Serializable {


    public String imgPath;
    public Bitmap bitmapImg;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Bitmap getBitmapImg() {
        return bitmapImg;
    }

    public void setBitmapImg(Bitmap bitmapImg) {
        this.bitmapImg = bitmapImg;
    }
}
