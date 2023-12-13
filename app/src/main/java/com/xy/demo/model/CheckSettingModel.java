package com.xy.demo.model;

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


    private SystemSetting system_setting;
    private VersionUpdate version_update;
    private StartPage start_page;
    private ProtocolList protocol_list;
    private SwitchInfo switch_info;
    private List<Readadinfo> readadinfo;
    private String online_service;
    private AdvertMob advert_mob;

    public SystemSetting getSystem_setting() {
        return system_setting;
    }

    public void setSystem_setting(SystemSetting system_setting) {
        this.system_setting = system_setting;
    }

    public VersionUpdate getVersion_update() {
        return version_update;
    }

    public void setVersion_update(VersionUpdate version_update) {
        this.version_update = version_update;
    }

    public StartPage getStart_page() {
        return start_page;
    }

    public void setStart_page(StartPage start_page) {
        this.start_page = start_page;
    }

    public ProtocolList getProtocol_list() {
        return protocol_list;
    }

    public void setProtocol_list(ProtocolList protocol_list) {
        this.protocol_list = protocol_list;
    }

    public SwitchInfo getSwitch_info() {
        return switch_info;
    }

    public void setSwitch_info(SwitchInfo switch_info) {
        this.switch_info = switch_info;
    }

    public List<Readadinfo> getReadadinfo() {
        return readadinfo;
    }

    public void setReadadinfo(List<Readadinfo> readadinfo) {
        this.readadinfo = readadinfo;
    }

    public String getOnline_service() {
        return online_service;
    }

    public void setOnline_service(String online_service) {
        this.online_service = online_service;
    }

    public AdvertMob getAdvert_mob() {
        return advert_mob;
    }

    public void setAdvert_mob(AdvertMob advert_mob) {
        this.advert_mob = advert_mob;
    }

    public static class SystemSetting {
        private String currencyUnit;
        private String subUnit;
        private int check_status;
        private List<Integer> site_type;
        private int novel_reward_switch;
        private String site_content_comic_version;
        private int email_login_switch;
        private int limit_switch;
        private int is_read;

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

        public int getCheck_status() {
            return check_status;
        }

        public void setCheck_status(int check_status) {
            this.check_status = check_status;
        }

        public List<Integer> getSite_type() {
            return site_type;
        }

        public void setSite_type(List<Integer> site_type) {
            this.site_type = site_type;
        }

        public int getNovel_reward_switch() {
            return novel_reward_switch;
        }

        public void setNovel_reward_switch(int novel_reward_switch) {
            this.novel_reward_switch = novel_reward_switch;
        }

        public String getSite_content_comic_version() {
            return site_content_comic_version;
        }

        public void setSite_content_comic_version(String site_content_comic_version) {
            this.site_content_comic_version = site_content_comic_version;
        }

        public int getEmail_login_switch() {
            return email_login_switch;
        }

        public void setEmail_login_switch(int email_login_switch) {
            this.email_login_switch = email_login_switch;
        }

        public int getLimit_switch() {
            return limit_switch;
        }

        public void setLimit_switch(int limit_switch) {
            this.limit_switch = limit_switch;
        }

        public int getIs_read() {
            return is_read;
        }

        public void setIs_read(int is_read) {
            this.is_read = is_read;
        }
    }

    public static class VersionUpdate {
        private int status;
        private String msg;
        private String url;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
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

    public static class StartPage {
        private int skip_type;
        private String image;
        private String title;
        private String skip_content;
        private String ad_key;
        private String ad_android_key;

        public int getSkip_type() {
            return skip_type;
        }

        public void setSkip_type(int skip_type) {
            this.skip_type = skip_type;
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

        public String getSkip_content() {
            return skip_content;
        }

        public void setSkip_content(String skip_content) {
            this.skip_content = skip_content;
        }

        public String getAd_key() {
            return ad_key;
        }

        public void setAd_key(String ad_key) {
            this.ad_key = ad_key;
        }

        public String getAd_android_key() {
            return ad_android_key;
        }

        public void setAd_android_key(String ad_android_key) {
            this.ad_android_key = ad_android_key;
        }
    }

    public static class ProtocolList {
        private String notify;
        private String privacy;
        private String logoff;
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

    public static class SwitchInfo {
        private int vip_switch;
        private int recharge_switch;
        private int email_login_switch;
        private int unit_switch;
        private int vip_read_all;

        public int getVip_switch() {
            return vip_switch;
        }

        public void setVip_switch(int vip_switch) {
            this.vip_switch = vip_switch;
        }

        public int getRecharge_switch() {
            return recharge_switch;
        }

        public void setRecharge_switch(int recharge_switch) {
            this.recharge_switch = recharge_switch;
        }

        public int getEmail_login_switch() {
            return email_login_switch;
        }

        public void setEmail_login_switch(int email_login_switch) {
            this.email_login_switch = email_login_switch;
        }

        public int getUnit_switch() {
            return unit_switch;
        }

        public void setUnit_switch(int unit_switch) {
            this.unit_switch = unit_switch;
        }

        public int getVip_read_all() {
            return vip_read_all;
        }

        public void setVip_read_all(int vip_read_all) {
            this.vip_read_all = vip_read_all;
        }
    }

    public static class AdvertMob {
        private int advert_id;
        private AdKey ad_key;
        private AdAndroidKey ad_android_key;

        public int getAdvert_id() {
            return advert_id;
        }

        public void setAdvert_id(int advert_id) {
            this.advert_id = advert_id;
        }

        public AdKey getAd_key() {
            return ad_key;
        }

        public void setAd_key(AdKey ad_key) {
            this.ad_key = ad_key;
        }

        public AdAndroidKey getAd_android_key() {
            return ad_android_key;
        }

        public void setAd_android_key(AdAndroidKey ad_android_key) {
            this.ad_android_key = ad_android_key;
        }

        public static class AdKey {
            private Recharge recharge;
            private String center;
            private String bottom;
            private String back;

            public Recharge getRecharge() {
                return recharge;
            }

            public void setRecharge(Recharge recharge) {
                this.recharge = recharge;
            }

            public String getCenter() {
                return center;
            }

            public void setCenter(String center) {
                this.center = center;
            }

            public String getBottom() {
                return bottom;
            }

            public void setBottom(String bottom) {
                this.bottom = bottom;
            }

            public String getBack() {
                return back;
            }

            public void setBack(String back) {
                this.back = back;
            }

            public static class Recharge {
                private String id;
                private String num;
                private String val;
                private String day_num;
                private String text;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getNum() {
                    return num;
                }

                public void setNum(String num) {
                    this.num = num;
                }

                public String getVal() {
                    return val;
                }

                public void setVal(String val) {
                    this.val = val;
                }

                public String getDay_num() {
                    return day_num;
                }

                public void setDay_num(String day_num) {
                    this.day_num = day_num;
                }

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }
            }
        }

        public static class AdAndroidKey {
            private AdKey.Recharge recharge;
            private String center;
            private String bottom;
            private String back;

            public AdKey.Recharge getRecharge() {
                return recharge;
            }

            public void setRecharge(AdKey.Recharge recharge) {
                this.recharge = recharge;
            }

            public String getCenter() {
                return center;
            }

            public void setCenter(String center) {
                this.center = center;
            }

            public String getBottom() {
                return bottom;
            }

            public void setBottom(String bottom) {
                this.bottom = bottom;
            }

            public String getBack() {
                return back;
            }

            public void setBack(String back) {
                this.back = back;
            }
        }
    }

    public static class Readadinfo {
        private String title;
        private String img;
        private int skip_type;
        private Object skip_content;
        private int type;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getSkip_type() {
            return skip_type;
        }

        public void setSkip_type(int skip_type) {
            this.skip_type = skip_type;
        }

        public Object getSkip_content() {
            return skip_content;
        }

        public void setSkip_content(Object skip_content) {
            this.skip_content = skip_content;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
