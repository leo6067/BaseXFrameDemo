package com.xy.demo.model;

public class LanguageModel {

    public String languageName;
    public String languageCode;

    public boolean isCheck;

    public int flagImg;


    public LanguageModel(String languageName, String languageCode, int flagImg, boolean isCheck ) {
        this.languageName = languageName;
        this.languageCode = languageCode;
        this.isCheck = isCheck;
        this.flagImg = flagImg;
    }

    public int getFlagImg() {
        return flagImg;
    }

    public void setFlagImg(int flagImg) {
        this.flagImg = flagImg;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
