package com.xy.demo.model;

import java.io.File;
import java.util.List;


public class SecondModel {
    private boolean isCheck;
    private String title;

    private String fileTime;
    private long fileSize;

    private String fileSizeStr;
    private File file;


    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileSizeStr() {
        return fileSizeStr;
    }

    public void setFileSizeStr(String fileSizeStr) {
        this.fileSizeStr = fileSizeStr;
    }

    private List<ThirdModel> listThirdModel;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ThirdModel> getListThirdModel() {
        return listThirdModel;
    }

    public void setListThirdModel(List<ThirdModel> listThirdModel) {
        this.listThirdModel = listThirdModel;
    }

    public String getFileTime() {
        return fileTime;
    }

    public void setFileTime(String fileTime) {
        this.fileTime = fileTime;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return "SecondModel{" +
                "isCheck=" + isCheck +
                ", title='" + title + '\'' +
                ", fileTime='" + fileTime + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", listThirdModel=" + listThirdModel +
                '}';
    }
}
