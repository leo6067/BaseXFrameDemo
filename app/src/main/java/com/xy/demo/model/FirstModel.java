package com.xy.demo.model;

import java.util.List;


public class FirstModel {
    private boolean isCheck;
    private String title;


    private int icon;


    private long size;


    private List<SecondModel> listSecondModel;

    public FirstModel() {
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public List<SecondModel> getListSecondModel() {
        return listSecondModel;
    }

    public void setListSecondModel(List<SecondModel> listSecondModel) {
        this.listSecondModel = listSecondModel;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public FirstModel(boolean isCheck, String title, List<SecondModel> listSecondModel) {

        this.isCheck = isCheck;
        this.title = title;
        this.listSecondModel = listSecondModel;
    }
}
