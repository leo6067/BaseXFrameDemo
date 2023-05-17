package com.xy.xframework.titlebar

import com.xy.xframework.R


/**
 * 设置和获取标题栏设置的工具类
 */
object GlobalTitleBarProvider {

    private var titleBarBuilder: TitleBarBuilder = TitleBarBuilder()
        .setTitleGravity(TitleBarBuilder.CENTER)
        .setTitleBarBackgroundColorRes(R.color.white)
        .setTitleBarLeftBackIcon(R.mipmap.nav_back)


    fun setTitleBarBuilder(titleBarBuilder: TitleBarBuilder) {
        GlobalTitleBarProvider.titleBarBuilder = titleBarBuilder
    }


    fun getTitleBarBuilder(): TitleBarBuilder {
        return titleBarBuilder
    }

}