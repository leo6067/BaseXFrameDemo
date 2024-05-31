package com.xy.demo.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
//SubBrandModel
public class SubBrandListModel implements Serializable {


    @SerializedName("list")
    private List<SubBrandModel> list;

    public List<SubBrandModel> getList() {
        return list;
    }

    public void setList(List<SubBrandModel> list) {
        this.list = list;
    }

    public static class SubBrandModel {
        @SerializedName("ModelId")
        private String modelId;
        @SerializedName("BrandId")
        private String brandId;
        @SerializedName("BrandIdStr")
        private String brandIdStr;
        @SerializedName("BrandName")
        private String brandName;
        @SerializedName("ModelName")
        private String modelName;
        @SerializedName("DeviceTypeId")
        private String deviceTypeId;
        @SerializedName("DeviceTypeName")
        private String deviceTypeName;
        @SerializedName("FirstKeyType")
        private String firstKeyType;
        @SerializedName("AppName")
        private String appName;
        @SerializedName("RemoteCode")
        private String remoteCode;
        @SerializedName("RemoteKey")
        private String remoteKey;
        @SerializedName("Frequency")
        private String frequency;

        public String getModelId() {
            return modelId;
        }

        public void setModelId(String modelId) {
            this.modelId = modelId;
        }

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
        }

        public String getBrandIdStr() {
            return brandIdStr;
        }

        public void setBrandIdStr(String brandIdStr) {
            this.brandIdStr = brandIdStr;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getModelName() {
            return modelName;
        }

        public void setModelName(String modelName) {
            this.modelName = modelName;
        }

        public String getDeviceTypeId() {
            return deviceTypeId;
        }

        public void setDeviceTypeId(String deviceTypeId) {
            this.deviceTypeId = deviceTypeId;
        }

        public String getDeviceTypeName() {
            return deviceTypeName;
        }

        public void setDeviceTypeName(String deviceTypeName) {
            this.deviceTypeName = deviceTypeName;
        }

        public String getFirstKeyType() {
            return firstKeyType;
        }

        public void setFirstKeyType(String firstKeyType) {
            this.firstKeyType = firstKeyType;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getRemoteCode() {
            return remoteCode;
        }

        public void setRemoteCode(String remoteCode) {
            this.remoteCode = remoteCode;
        }

        public String getRemoteKey() {
            return remoteKey;
        }

        public void setRemoteKey(String remoteKey) {
            this.remoteKey = remoteKey;
        }

        public String getFrequency() {
            return frequency;
        }

        public void setFrequency(String frequency) {
            this.frequency = frequency;
        }


        @Override
        public String toString() {
            return "SubBrandModel{" +
                    "modelId='" + modelId + '\'' +
                    ", brandId='" + brandId + '\'' +
                    ", brandIdStr='" + brandIdStr + '\'' +
                    ", brandName='" + brandName + '\'' +
                    ", modelName='" + modelName + '\'' +
                    ", deviceTypeId='" + deviceTypeId + '\'' +
                    ", deviceTypeName='" + deviceTypeName + '\'' +
                    ", firstKeyType='" + firstKeyType + '\'' +
                    ", appName='" + appName + '\'' +
                    ", remoteCode='" + remoteCode + '\'' +
                    ", remoteKey='" + remoteKey + '\'' +
                    ", frequency='" + frequency + '\'' +
                    '}';
        }
    }
}
