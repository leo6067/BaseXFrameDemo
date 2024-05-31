package com.xy.demo.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

//指令集
public class OrderListModel {


    @SerializedName("list")
    private List<OrderModel> list;

    public List<OrderModel> getList() {
        return list;
    }

    public void setList(List<OrderModel> list) {
        this.list = list;
    }

    public static class OrderModel {
        @SerializedName("ID")
        private String id;
        @SerializedName("RemoteCode")
        private String remoteCode;
        @SerializedName("RemoteKey")
        private String remoteKey;
        @SerializedName("Frequency")
        private String frequency;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
            return "OrderModel{" +
                    "id='" + id + '\'' +
                    ", remoteCode='" + remoteCode + '\'' +
                    ", remoteKey='" + remoteKey + '\'' +
                    ", frequency='" + frequency + '\'' +
                    '}';
        }
    }
}
