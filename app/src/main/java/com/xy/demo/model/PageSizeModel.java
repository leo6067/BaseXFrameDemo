package com.xy.demo.model;

public class PageSizeModel {

    public String name;
    public String content;
    public boolean checkStatus;


    public PageSizeModel(String name, String content, boolean checkStatus) {
        this.name = name;
        this.content = content;
        this.checkStatus = checkStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(boolean checkStatus) {
        this.checkStatus = checkStatus;
    }
}
