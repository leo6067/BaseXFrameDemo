package com.xy.xframework.utils

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.util.TypedValue
import androidx.annotation.*
import androidx.core.content.ContextCompat
import com.xy.xframework.base.XBaseApplication

/**
 * 资源工具类
 */
object ResourceUtils {

    /**
     * 获取资源字符串
     */
    fun getStringResource(@StringRes resId: Int): String {
        return XBaseApplication.application.resources.getString(resId)
    }

    /**
     * 获取资源字符串
     */
    fun getStringResource(@StringRes resId: Int,vararg args: Any?): String {
        return XBaseApplication.application.resources.getString(resId).format(*args)
    }
    

    /**
     * 获取boolean资源
     */
    fun getBooleanResource(@BoolRes resId: Int): Boolean {
        return XBaseApplication.application.resources.getBoolean(resId)
    }

    /**
     * 获取颜色值方法
     */
    fun getColorResource(@ColorRes resId: Int): Int {
        return ContextCompat.getColor(XBaseApplication.application, resId)
    }

    /**
     * 获取字符串数组
     */
    fun getStringResourceArray(@ArrayRes resId: Int): Array<String?>? {
        return XBaseApplication.application.resources.getStringArray(resId)
    }

    /**
     * 获取图片资源
     */
    fun getDrawable(@DrawableRes resId: Int): Drawable? {
        return ContextCompat.getDrawable(XBaseApplication.application, resId)
    }

    /**
     * sp 转 px
     */
    @JvmStatic
    fun sp2px(spVal: Float): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, Resources.getSystem().displayMetrics)

    /**
     * dp 转 px
     */
    @JvmStatic
    fun dp2px(dpVal: Float): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, Resources.getSystem().displayMetrics) + 0.5f

    /**
     * px 转 dp
     */
    @JvmStatic
    fun px2dp(pxValue: Float): Int {
        val scale: Float = XBaseApplication.application.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }
    

    /**
     * 根据颜色及圆角创建ShapeDrawable
     */
    fun createRoundDrawable(color: Int, l: Float = 0f, t: Float = 0f, r: Float = 0f, b: Float = 0f): Drawable {
        val arrayOf = floatArrayOf(l, l, t, t, r, r, b, b)
        val drawable = ShapeDrawable(RoundRectShape(arrayOf, null, null))
        drawable.paint.color = color
        return drawable
    }
    

}