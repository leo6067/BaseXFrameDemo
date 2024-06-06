package com.xy.demo.model;

public class ToolsModel {


    public int itemSrc;
    public String itemName;


    public ToolsModel(int itemSrc, String itemName) {
        this.itemSrc = itemSrc;
        this.itemName = itemName;
    }

    public int getItemSrc() {
        return itemSrc;
    }

    public void setItemSrc(int itemSrc) {
        this.itemSrc = itemSrc;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
