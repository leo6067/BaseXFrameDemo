package com.xy.demo.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * author: Leo
 * createDate: 2023/11/21 16:17
 */
public class HomeItemModel implements MultiItemEntity {

    public int itemType;

    public HomeItemModel(int itemType) {
        this.itemType = itemType;
    }


    @Override
    public int getItemType() {
        return itemType;
    }
}
