package com.xy.demo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

//品牌列表
public class BrandListModel {

    @SerializedName("list")
    private List<BrandModel> list;

    public List<BrandModel> getList() {
        return list;
    }

    public void setList(List<BrandModel> list) {
        this.list = list;
    }


}
