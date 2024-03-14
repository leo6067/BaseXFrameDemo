package com.xy.demo.model;

public class SettingModel {

    public int drawableId;

    public String titleStr;



    public SettingModel(int drawableId, String titleStr) {
        this.drawableId = drawableId;
        this.titleStr = titleStr;

    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public String getTitleStr() {
        return titleStr;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }


}
