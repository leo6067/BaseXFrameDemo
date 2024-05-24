package com.xy.xframework.titlebar

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import com.xy.xframework.R
import com.xy.xframework.extensions.color
import com.xy.xframework.extensions.drawable

/**
 * 标题栏构建 用于处理不同app的不同样式，这个是全局通用设置，如果有特殊的定制化则在activity里面进行自定义处理
 */
class TitleBarBuilder {

    companion object {

        /**
         * 标题左对其
         */
        const val LEFT = 0

        /**
         * 标题居中对其
         */
        const val CENTER = 1
    }

    /**
     * 标题对其方式
     */
    private var mTitleGravity: Int = CENTER

    /**
     * 标题颜色
     */
    private var mTitleTextColor: Int = R.color.TitleTextColor.color()

    /**
     * 标题是否加粗
     */
    private var mTitleTextBold: Boolean = true

    /**
     * 标题栏的背景颜色
     */
    private var mTitleBarBackgroundColor: Int = R.color.TitleBarBackgroundColor.color()

    /**
     * 标题栏左边的返回图标
     */
    private var mTitleBarLeftBackIcon: Drawable? = R.mipmap.nav_back.drawable()

    /**
     * 状态栏颜色
     */
    private var mStatusBarColor: Int = R.color.StatusBarColor.color()

    /**
     * 点击标题返回
     */
    var clickTitleToBack = false

    /**
     * 设置标题栏的标题对其方式   目前只支持设置居中和居左
     */
    fun setTitleGravity(gravity: Int): TitleBarBuilder {
        if (gravity in LEFT..CENTER) {
            this.mTitleGravity = gravity
        }
        return this
    }

    fun getTitleGravity(): Int = mTitleGravity

    /**
     * 设置标题的颜色
     */
    fun setTitleTextColor(@ColorInt color: Int): TitleBarBuilder {
        this.mTitleTextColor = color
        return this
    }

    /**
     * 设置标题颜色
     */
    fun setTitleTextColorRes(@ColorRes color: Int): TitleBarBuilder {
        this.mTitleTextColor = color.color()
        return this
    }


    /**
     * 获取标题栏的颜色
     */
    fun getTitleTextColor(): Int = mTitleTextColor

    /**
     * 设置标题是否加粗，默认是加粗
     */
    fun setTitleTextBold(bold: Boolean): TitleBarBuilder {
        this.mTitleTextBold = bold
        return this
    }

    fun getTitleTextBold(): Boolean = mTitleTextBold

    /**
     * 设置标题栏的背景颜色
     */
    fun setTitleBarBackgroundColor(@ColorInt color: Int): TitleBarBuilder {
        this.mTitleBarBackgroundColor = color
        return this
    }

    /**
     * 设置标题栏的背景颜色
     */
    fun setTitleBarBackgroundColorRes(@ColorRes res: Int): TitleBarBuilder {
        this.mTitleBarBackgroundColor = res.color()
        return this
    }

    /**
     * 获取标题栏背景颜色
     */
    fun getTitleBarBackgroundColor(): Int = mTitleBarBackgroundColor

    /**
     * 设置标题栏左边返回键的图标
     */
    fun setTitleBarLeftBackIcon(icon: Drawable): TitleBarBuilder {
        this.mTitleBarLeftBackIcon = icon
        return this
    }

    /**
     * 设置标题栏左边返回键的图标
     */
    fun setTitleBarLeftBackIcon(iconRes: Int): TitleBarBuilder {
        this.mTitleBarLeftBackIcon = iconRes.drawable()
        return this
    }

    /**
     * 获取标题栏坐标的返回按钮
     */
    fun getTitleBarLeftBackIcon(): Drawable? = mTitleBarLeftBackIcon


    /**
     * 设置标题栏左边返回键的图标
     */
    fun setStatusBarColor(@ColorInt color: Int): TitleBarBuilder {
        this.mStatusBarColor = color
        return this
    }

    /**
     * 设置标题栏左边返回键的图标
     */
    fun setStatusBarColorRes(@ColorRes colorRes: Int): TitleBarBuilder {
        this.mStatusBarColor = colorRes.color()
        return this
    }

    /**
     * 获取标题栏坐标的返回按钮
     */
    fun getStatusBarColor(): Int = mStatusBarColor

    /**
     * 设置点击标题返回
     */
    fun setClickTitleToBack(back: Boolean): TitleBarBuilder {
        this.clickTitleToBack = back
        return this
    }


}