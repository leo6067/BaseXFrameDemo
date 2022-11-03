package com.xy.xframework.web

import com.xy.xframework.command.SingleLiveEvent

class WebViewUIChangeEvent {

    /**
     * 重新加载H5页面
     */
    val reloadH5Event = SingleLiveEvent<Any>()

    /**
     * 有回退就回退，没回退就关闭
     */
    val goBackEvent = SingleLiveEvent<Any>()

    /**
     * 拦截物理返回按钮
     */
    val interceptBackEvent = SingleLiveEvent<Int>()

    /**
     * 关闭页面
     */
    val closeWeb = SingleLiveEvent<Int>()

    /**
     * 打开另外一个页面
     */
    val secondaryPage = SingleLiveEvent<Map<String, String>>()

    /**
     * 埋点上报
     */
    val postPointInfo = SingleLiveEvent<Map<String, String>>()

    /**
     * 路由跳转
     */
    val routerPath = SingleLiveEvent<String>()

    /**
     * 提示语
     */
    val toast = SingleLiveEvent<String>()

}