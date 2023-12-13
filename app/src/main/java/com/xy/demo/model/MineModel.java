package com.xy.demo.model;

import java.util.List;

/**
 * author: Leo
 * createDate: 2023/12/6 11:04
 */
public class MineModel {


    private String unit;
    private String subUnit;
    private List<AssetItems> asset_items;
    private List<List<PanelList>> panel_list;
    private int vip_switch;
    private VipInfo vip_info;
    private int is_read;
    private int uid;
    private String nickname;
    private String user_token;
    private int gender;
    private String avatar;
    private int auto_sub;
    private int status;
    private String email;
    private String udid;
    private int is_vip;
    private int goldRemain;
    private int silverRemain;
    private int totalRemain;
    private int remain;
    private int gold_remain;
    private int silver_remain;
    private Advert advert;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSubUnit() {
        return subUnit;
    }

    public void setSubUnit(String subUnit) {
        this.subUnit = subUnit;
    }

    public List<AssetItems> getAsset_items() {
        return asset_items;
    }

    public void setAsset_items(List<AssetItems> asset_items) {
        this.asset_items = asset_items;
    }

    public List<List<PanelList>> getPanel_list() {
        return panel_list;
    }

    public void setPanel_list(List<List<PanelList>> panel_list) {
        this.panel_list = panel_list;
    }

    public int getVip_switch() {
        return vip_switch;
    }

    public void setVip_switch(int vip_switch) {
        this.vip_switch = vip_switch;
    }

    public VipInfo getVip_info() {
        return vip_info;
    }

    public void setVip_info(VipInfo vip_info) {
        this.vip_info = vip_info;
    }

    public int getIs_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getAuto_sub() {
        return auto_sub;
    }

    public void setAuto_sub(int auto_sub) {
        this.auto_sub = auto_sub;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public int getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(int is_vip) {
        this.is_vip = is_vip;
    }

    public int getGoldRemain() {
        return goldRemain;
    }

    public void setGoldRemain(int goldRemain) {
        this.goldRemain = goldRemain;
    }

    public int getSilverRemain() {
        return silverRemain;
    }

    public void setSilverRemain(int silverRemain) {
        this.silverRemain = silverRemain;
    }

    public int getTotalRemain() {
        return totalRemain;
    }

    public void setTotalRemain(int totalRemain) {
        this.totalRemain = totalRemain;
    }

    public int getRemain() {
        return remain;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }

    public int getGold_remain() {
        return gold_remain;
    }

    public void setGold_remain(int gold_remain) {
        this.gold_remain = gold_remain;
    }

    public int getSilver_remain() {
        return silver_remain;
    }

    public void setSilver_remain(int silver_remain) {
        this.silver_remain = silver_remain;
    }

    public Advert getAdvert() {
        return advert;
    }

    public void setAdvert(Advert advert) {
        this.advert = advert;
    }

    public static class VipInfo {
    }

    public static class Advert {
    }

    public static class AssetItems {
        private String title;
        private int value;
        private String action;
        private int skip_type;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public int getSkip_type() {
            return skip_type;
        }

        public void setSkip_type(int skip_type) {
            this.skip_type = skip_type;
        }
    }

    public static class PanelList {
        private String title;
        private String desc;
        private String title_color;
        private String desc_color;
        private String icon;
        private String icon_dark;
        private int is_click;
        private String action;
        private String content;
        private int group_id;
        private int skip_type;
        private String skip_content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getTitle_color() {
            return title_color;
        }

        public void setTitle_color(String title_color) {
            this.title_color = title_color;
        }

        public String getDesc_color() {
            return desc_color;
        }

        public void setDesc_color(String desc_color) {
            this.desc_color = desc_color;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getIcon_dark() {
            return icon_dark;
        }

        public void setIcon_dark(String icon_dark) {
            this.icon_dark = icon_dark;
        }

        public int getIs_click() {
            return is_click;
        }

        public void setIs_click(int is_click) {
            this.is_click = is_click;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }

        public int getSkip_type() {
            return skip_type;
        }

        public void setSkip_type(int skip_type) {
            this.skip_type = skip_type;
        }

        public String getSkip_content() {
            return skip_content;
        }

        public void setSkip_content(String skip_content) {
            this.skip_content = skip_content;
        }
    }
}
