package com.xy.demo.model;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

//品牌列表   ---遥控器model 保存db
public class BrandModel {

    @SerializedName("BrandName")
    private String brandName;
    @SerializedName("DeviceTypeName")
    private String deviceTypeName;
    @SerializedName("AppName")
    private String appName;
    @SerializedName("DeviceTypeId")
    private String deviceTypeId;
    @SerializedName("BrandCnName")
    private String brandCnName;
    @SerializedName("BrandId")
    private String brandId;
    @SerializedName("PinYin")
    private String pinYin;
    @SerializedName("FirstKeyType")
    private String firstKeyType;


    public String getPinYin() {
        return pinYin;
    }

    public void setPinYin(String pinYin) {
        this.pinYin = pinYin;
    }



    public String getBrandName() {
        return capitalizeFirstLetter(brandName) ;
    }


    public String capitalizeFirstLetter(String input) {
        if (input == null || input.length() == 0) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(String deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public String getBrandCnName() {
        return brandCnName;
    }

    public void setBrandCnName(String brandCnName) {
        this.brandCnName = brandCnName;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getFirstKeyType() {
        return firstKeyType;
    }

    public void setFirstKeyType(String firstKeyType) {
        this.firstKeyType = firstKeyType;
    }

    @Override
    public String toString() {
        return "BrandModel{" +

                "brandName='" + brandName + '\'' +
                ", deviceTypeName='" + deviceTypeName + '\'' +
                ", appName='" + appName + '\'' +
                ", deviceTypeId='" + deviceTypeId + '\'' +
                ", brandCnName='" + brandCnName + '\'' +
                ", brandId='" + brandId + '\'' +
                ", pinYin='" + pinYin + '\'' +
                ", firstKeyType='" + firstKeyType + '\'' +
                '}';
    }
}
