package com.xy.demo.model;

import com.flyco.tablayout.listener.CustomTabEntity;

public class TabEntity implements CustomTabEntity {

    public String titleName;
    public int selectIcon;
    public int unSelectIcon;

    public TabEntity(String titleName, int selectIcon, int unSelectIcon) {
        this.titleName = titleName;
        this.selectIcon = selectIcon;
        this.unSelectIcon = unSelectIcon;
    }

    @Override
    public String getTabTitle() {
        return titleName;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectIcon;
    }
}
