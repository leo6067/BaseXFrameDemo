package com.xy.demo.model;

import java.io.Serializable;

public class CompressModel implements Serializable {

    public String beforePath;
    public String afterPath;
    public String beforeSize;
    public String afterSize;


    public String compressSize;
    public String fileName;


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCompressSize() {
        return compressSize;
    }

    public void setCompressSize(String compressSize) {
        this.compressSize = compressSize;
    }

    public String getBeforePath() {
        return beforePath;
    }

    public void setBeforePath(String beforePath) {
        this.beforePath = beforePath;
    }

    public String getAfterPath() {
        return afterPath;
    }

    public void setAfterPath(String afterPath) {
        this.afterPath = afterPath;
    }

    public String getBeforeSize() {
        return beforeSize;
    }

    public void setBeforeSize(String beforeSize) {
        this.beforeSize = beforeSize;
    }

    public String getAfterSize() {
        return afterSize;
    }

    public void setAfterSize(String afterSize) {
        this.afterSize = afterSize;
    }
}
