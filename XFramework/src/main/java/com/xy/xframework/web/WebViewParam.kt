package com.xy.xframework.web

class WebViewParam {
    /**
     * 应用key
     */
    var appKey: String? = null

    /**
     * 应用包名
     */
    var appPackage: String? = null

    /**
     * 平台 android
     */
    var platform: String? = null

    /**
     * 应用版本
     */
    var appVersion: String? = null

    /**
     *  渠道
     */
    var channel: String? = null

    /**
     * 系统版本号
     */
    var sysVersion: String? = null

    /**
     * 用户的token
     */
    var authorization: String? = null

    /**
     * 设备ID
     */
    var deviceId: String? = null

    /**
     * 应用的Secret
     */
    var appSecret: String? = null

    /**
     * 状态栏高度--安装沉浸式用
     */
    var statusBarHeight: Int? = null

    override fun toString(): String {
        return "appKey = $appKey , appPackage = $appPackage , platform = $platform , " +
                "appVersion = $appVersion , channel = $channel , sysVersion = $sysVersion , " +
                "authorization = $authorization , deviceId = $deviceId ，appSecret = $appSecret"+
                "statusBarHeight = $statusBarHeight"
    }
}