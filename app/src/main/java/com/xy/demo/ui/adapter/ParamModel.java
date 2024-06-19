package com.xy.demo.ui.adapter;

import android.graphics.Bitmap;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class ParamModel {

    public Bitmap bitmap;

    public boolean selectStatus;

    public ParamModel(Bitmap bitmap, boolean selectStatus) {
        this.bitmap = bitmap;
        this.selectStatus = selectStatus;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public boolean isSelectStatus() {
        return selectStatus;
    }

    public void setSelectStatus(boolean selectStatus) {
        this.selectStatus = selectStatus;
    }
}
