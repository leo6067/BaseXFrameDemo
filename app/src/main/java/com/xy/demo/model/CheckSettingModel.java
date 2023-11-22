package com.xy.demo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * author: Leo
 * createDate: 2023/11/22 11:56
 * {
 * 		"system_setting": {
 * 			"currencyUnit": " Coins",
 * 			"subUnit": " Coins",
 * 			"check_status": 0,
 * 			"site_type": [1, 4],
 * 			"novel_reward_switch": 1,
 * 			"site_content_comic_version": "1.0.1,1.1.1,1.0.3",
 * 			"email_login_switch": 1,
 * 			"is_read": 1
 *                },
 * 		"version_update": {
 * 			"status": 0,
 * 			"msg": "",
 * 			"url": ""
 *        },
 * 		"start_page": {
 * 			"skip_type": 1,
 * 			"image": "http://hireadnovel-test.oss-us-west-1.aliyuncs.com/start-page/59a0758ccd43f0fb2c6bcb96af05689a.jpg?x-oss-process=image%2Fresize%2Cw_1242%2Ch_2208%2Cm_lfit",
 * 			"title": "启动广告测试测试",
 * 			"skip_content": "491",
 * 			"ad_key": "",
 * 			"ad_android_key": ""
 *        },
 * 		"ad_status_setting": {
 * 			"chapter_read_end": 0,
 * 			"chapter_read_bottom": 0,
 * 			"comic_read_end": 0,
 * 			"video_ad_switch": 0,
 * 			"book_read_gap": 5
 *        },
 * 		"protocol_list": {
 * 			"notify": "http://devpage.hdnovel.cn/site/notify?id=1&language=en&os_type=2",
 * 			"privacy": "http://devpage.hdnovel.cn/site/privacy-policy?id=1&language=en&os_type=2",
 * 			"logoff": "http://devpage.hdnovel.cn/site/logoff-protocol?id=1&language=en&os_type=2",
 * 			"user": "http://devpage.hdnovel.cn/site/user-agreement?id=1&language=en&os_type=2"
 *        },
 * 		"switch_info": {
 * 			"vip_switch": 0,
 * 			"recharge_switch": 1,
 * 			"email_login_switch": 1,
 * 			"unit_switch": 0,
 * 			"vip_read_all": 0
 *        },
 * 		"readadinfo": [],
 * 		"online_service": "https://m.me/137238952795672"* 	}
 */
public class CheckSettingModel {


    @SerializedName("system_setting")
    private SystemSettingDTO systemSetting;
    @SerializedName("version_update")
    private VersionUpdateDTO versionUpdate;
    @SerializedName("start_page")
    private StartPageDTO startPage;
    @SerializedName("ad_status_setting")
    private AdStatusSettingDTO adStatusSetting;
    @SerializedName("protocol_list")
    private ProtocolListDTO protocolList;
    @SerializedName("switch_info")
    private SwitchInfoDTO switchInfo;
    @SerializedName("readadinfo")
    private List<?> readadinfo;
    @SerializedName("online_service")
    private String onlineService;

    public SystemSettingDTO getSystemSetting() {
        return systemSetting;
    }

    public void setSystemSetting(SystemSettingDTO systemSetting) {
        this.systemSetting = systemSetting;
    }

    public VersionUpdateDTO getVersionUpdate() {
        return versionUpdate;
    }

    public void setVersionUpdate(VersionUpdateDTO versionUpdate) {
        this.versionUpdate = versionUpdate;
    }

    public StartPageDTO getStartPage() {
        return startPage;
    }

    public void setStartPage(StartPageDTO startPage) {
        this.startPage = startPage;
    }

    public AdStatusSettingDTO getAdStatusSetting() {
        return adStatusSetting;
    }

    public void setAdStatusSetting(AdStatusSettingDTO adStatusSetting) {
        this.adStatusSetting = adStatusSetting;
    }

    public ProtocolListDTO getProtocolList() {
        return protocolList;
    }

    public void setProtocolList(ProtocolListDTO protocolList) {
        this.protocolList = protocolList;
    }

    public SwitchInfoDTO getSwitchInfo() {
        return switchInfo;
    }

    public void setSwitchInfo(SwitchInfoDTO switchInfo) {
        this.switchInfo = switchInfo;
    }

    public List<?> getReadadinfo() {
        return readadinfo;
    }

    public void setReadadinfo(List<?> readadinfo) {
        this.readadinfo = readadinfo;
    }

    public String getOnlineService() {
        return onlineService;
    }

    public void setOnlineService(String onlineService) {
        this.onlineService = onlineService;
    }

    public static class SystemSettingDTO {
        @SerializedName("currencyUnit")
        private String currencyUnit;
        @SerializedName("subUnit")
        private String subUnit;
        @SerializedName("check_status")
        private Integer checkStatus;
        @SerializedName("site_type")
        private List<Integer> siteType;
        @SerializedName("novel_reward_switch")
        private Integer novelRewardSwitch;
        @SerializedName("site_content_comic_version")
        private String siteContentComicVersion;
        @SerializedName("email_login_switch")
        private Integer emailLoginSwitch;
        @SerializedName("is_read")
        private Integer isRead;

        public String getCurrencyUnit() {
            return currencyUnit;
        }

        public void setCurrencyUnit(String currencyUnit) {
            this.currencyUnit = currencyUnit;
        }

        public String getSubUnit() {
            return subUnit;
        }

        public void setSubUnit(String subUnit) {
            this.subUnit = subUnit;
        }

        public Integer getCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(Integer checkStatus) {
            this.checkStatus = checkStatus;
        }

        public List<Integer> getSiteType() {
            return siteType;
        }

        public void setSiteType(List<Integer> siteType) {
            this.siteType = siteType;
        }

        public Integer getNovelRewardSwitch() {
            return novelRewardSwitch;
        }

        public void setNovelRewardSwitch(Integer novelRewardSwitch) {
            this.novelRewardSwitch = novelRewardSwitch;
        }

        public String getSiteContentComicVersion() {
            return siteContentComicVersion;
        }

        public void setSiteContentComicVersion(String siteContentComicVersion) {
            this.siteContentComicVersion = siteContentComicVersion;
        }

        public Integer getEmailLoginSwitch() {
            return emailLoginSwitch;
        }

        public void setEmailLoginSwitch(Integer emailLoginSwitch) {
            this.emailLoginSwitch = emailLoginSwitch;
        }

        public Integer getIsRead() {
            return isRead;
        }

        public void setIsRead(Integer isRead) {
            this.isRead = isRead;
        }
    }

    public static class VersionUpdateDTO {
        @SerializedName("status")
        private Integer status;
        @SerializedName("msg")
        private String msg;
        @SerializedName("url")
        private String url;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class StartPageDTO {
        @SerializedName("skip_type")
        private Integer skipType;
        @SerializedName("image")
        private String image;
        @SerializedName("title")
        private String title;
        @SerializedName("skip_content")
        private String skipContent;
        @SerializedName("ad_key")
        private String adKey;
        @SerializedName("ad_android_key")
        private String adAndroidKey;

        public Integer getSkipType() {
            return skipType;
        }

        public void setSkipType(Integer skipType) {
            this.skipType = skipType;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSkipContent() {
            return skipContent;
        }

        public void setSkipContent(String skipContent) {
            this.skipContent = skipContent;
        }

        public String getAdKey() {
            return adKey;
        }

        public void setAdKey(String adKey) {
            this.adKey = adKey;
        }

        public String getAdAndroidKey() {
            return adAndroidKey;
        }

        public void setAdAndroidKey(String adAndroidKey) {
            this.adAndroidKey = adAndroidKey;
        }
    }

    public static class AdStatusSettingDTO {
        @SerializedName("chapter_read_end")
        private Integer chapterReadEnd;
        @SerializedName("chapter_read_bottom")
        private Integer chapterReadBottom;
        @SerializedName("comic_read_end")
        private Integer comicReadEnd;
        @SerializedName("video_ad_switch")
        private Integer videoAdSwitch;
        @SerializedName("book_read_gap")
        private Integer bookReadGap;

        public Integer getChapterReadEnd() {
            return chapterReadEnd;
        }

        public void setChapterReadEnd(Integer chapterReadEnd) {
            this.chapterReadEnd = chapterReadEnd;
        }

        public Integer getChapterReadBottom() {
            return chapterReadBottom;
        }

        public void setChapterReadBottom(Integer chapterReadBottom) {
            this.chapterReadBottom = chapterReadBottom;
        }

        public Integer getComicReadEnd() {
            return comicReadEnd;
        }

        public void setComicReadEnd(Integer comicReadEnd) {
            this.comicReadEnd = comicReadEnd;
        }

        public Integer getVideoAdSwitch() {
            return videoAdSwitch;
        }

        public void setVideoAdSwitch(Integer videoAdSwitch) {
            this.videoAdSwitch = videoAdSwitch;
        }

        public Integer getBookReadGap() {
            return bookReadGap;
        }

        public void setBookReadGap(Integer bookReadGap) {
            this.bookReadGap = bookReadGap;
        }
    }

    public static class ProtocolListDTO {
        @SerializedName("notify")
        private String notify;
        @SerializedName("privacy")
        private String privacy;
        @SerializedName("logoff")
        private String logoff;
        @SerializedName("user")
        private String user;

        public String getNotify() {
            return notify;
        }

        public void setNotify(String notify) {
            this.notify = notify;
        }

        public String getPrivacy() {
            return privacy;
        }

        public void setPrivacy(String privacy) {
            this.privacy = privacy;
        }

        public String getLogoff() {
            return logoff;
        }

        public void setLogoff(String logoff) {
            this.logoff = logoff;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }
    }

    public static class SwitchInfoDTO {
        @SerializedName("vip_switch")
        private Integer vipSwitch;
        @SerializedName("recharge_switch")
        private Integer rechargeSwitch;
        @SerializedName("email_login_switch")
        private Integer emailLoginSwitch;
        @SerializedName("unit_switch")
        private Integer unitSwitch;
        @SerializedName("vip_read_all")
        private Integer vipReadAll;

        public Integer getVipSwitch() {
            return vipSwitch;
        }

        public void setVipSwitch(Integer vipSwitch) {
            this.vipSwitch = vipSwitch;
        }

        public Integer getRechargeSwitch() {
            return rechargeSwitch;
        }

        public void setRechargeSwitch(Integer rechargeSwitch) {
            this.rechargeSwitch = rechargeSwitch;
        }

        public Integer getEmailLoginSwitch() {
            return emailLoginSwitch;
        }

        public void setEmailLoginSwitch(Integer emailLoginSwitch) {
            this.emailLoginSwitch = emailLoginSwitch;
        }

        public Integer getUnitSwitch() {
            return unitSwitch;
        }

        public void setUnitSwitch(Integer unitSwitch) {
            this.unitSwitch = unitSwitch;
        }

        public Integer getVipReadAll() {
            return vipReadAll;
        }

        public void setVipReadAll(Integer vipReadAll) {
            this.vipReadAll = vipReadAll;
        }

        @Override
        public String toString() {
            return "{" +
                    "\"vipSwitch\":" + vipSwitch +
                    ", \"rechargeSwitch\":" + rechargeSwitch +
                    ", \"emailLoginSwitch\":" + emailLoginSwitch +
                    ", \"unitSwitch\":" + unitSwitch +
                    ", \"vipReadAll\":" + vipReadAll +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "{" +
                "\"systemSetting\":" + systemSetting +
                ", \"versionUpdate\":" + versionUpdate +
                ", \"startPage\":" + startPage +
                ", \"adStatusSetting\":" + adStatusSetting +
                ", \"protocolList\":" + protocolList +
                ", \"switchInfo\":" + switchInfo +
                ", \"readadinfo\":" + readadinfo +
                ", \"onlineService\":\'" + onlineService + "\'" +
                '}';
    }
}
