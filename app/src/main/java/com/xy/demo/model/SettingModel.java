package com.xy.demo.model;

public class SettingModel {

    public int drawableId;

    public String titleStr;

    public String srcStr;

    public SettingModel(int drawableId, String titleStr, String srcStr) {
        this.drawableId = drawableId;
        this.titleStr = titleStr;
        this.srcStr = srcStr;
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

    public String getSrcStr() {
        return srcStr;
    }

    public void setSrcStr(String srcStr) {
        this.srcStr = srcStr;
    }
}
