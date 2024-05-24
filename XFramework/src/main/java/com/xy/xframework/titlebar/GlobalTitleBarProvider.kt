package com.xy.xframework.titlebar

import com.xy.xframework.R


/**
 * 设置和获取标题栏设置的工具类
 */
object GlobalTitleBarProvider {

    private var titleBarBuilder: TitleBarBuilder = TitleBarBuilder()
        .setTitleGravity(TitleBarBuilder.LEFT)  //标题靠左
        .setTitleBarBackgroundColorRes(R.color.TitleBarBackgroundColor)
        .setTitleBarLeftBackIcon(R.mipmap.nav_back)
    


    fun setTitleBarBuilder(titleBarBuilder: TitleBarBuilder) {
        GlobalTitleBarProvider.titleBarBuilder = titleBarBuilder
    }


    fun getTitleBarBuilder(): TitleBarBuilder {
        return titleBarBuilder
    }

}