package com.xy.xframework.utils


import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager
import java.math.BigDecimal
import kotlin.math.pow
import kotlin.math.sqrt

object ScreenUtils {
    fun spToPx(context: Context, spValue: Float): Int {
        val metrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, metrics).toInt()
    }

    fun getWidthPixels(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    fun dpToPx(context: Context, dipValue: Float): Int {
        val metrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics).toInt()
    }

    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
    fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }
    /**
     * 获得屏幕宽度
     *
     * @return
     */
    @JvmStatic
    fun getScreenWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }
//    fun getScreenWidthMargin(context: Context,margin:): Int {
//        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        val outMetrics = DisplayMetrics()
//        wm.defaultDisplay.getMetrics(outMetrics)
//        return outMetrics.widthPixels
//    }

    /**
     * 获得屏幕高度
     *
     * @return
     */
    @JvmStatic
    fun getScreenHeight(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.heightPixels
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    fun getStatusHeight(context: Context): Int {
        var statusHeight = -1
        try {
            val clazz =
                Class.forName("com.android.internal.R\$dimen.xml")
            val `object` = clazz.newInstance()
            val height = clazz.getField("status_bar_height")[`object`].toString().toInt()
            statusHeight =
                context.applicationContext.resources.getDimensionPixelSize(height)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return statusHeight
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    fun snapShotWithStatusBar(activity: Activity): Bitmap? {
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val bmp = view.drawingCache
        val width = getScreenWidth(activity)
        val height = getScreenHeight(activity)
        var bp: Bitmap? = null
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height)
        view.destroyDrawingCache()
        return bp
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    fun snapShotWithoutStatusBar(activity: Activity): Bitmap? {
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val bmp = view.drawingCache
        val frame = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(frame)
        val statusBarHeight = frame.top
        val width = getScreenWidth(activity)
        val height = getScreenHeight(activity)
        var bp: Bitmap? = null
        bp = Bitmap.createBitmap(
            bmp, 0, statusBarHeight, width, height
                    - statusBarHeight
        )
        view.destroyDrawingCache()
        return bp
    }

    /**
     * 获取当前屏幕的尺寸大小
     *
     * @return
     */
    fun getPingMuSize(context: Context): Double {
        return try {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            val dm = DisplayMetrics()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                display.getRealMetrics(dm)
            } else {
                display.getMetrics(dm)
            }
            val x = (dm.widthPixels / dm.xdpi.toDouble()).pow(2.0)
            val y = (dm.heightPixels / dm.ydpi.toDouble()).pow(2.0)
            // 屏幕尺寸
            var decimal = BigDecimal(sqrt(x + y))
            decimal = decimal.setScale(1, BigDecimal.ROUND_UP)
            decimal.toDouble()
        } catch (e: Exception) {
            e.printStackTrace()
            0.00
        }
    }

    /**
     * 根据手机尺寸设置 适配框架 对应的宽,这里默认是拿宽,之前是在AndroidManifest的<meta-data>里面配置的,发现平板和手机因为尺寸差别太大无法只设置一个宽.
     * Nexus 5x Api28--->当前手机尺寸为:5.3
     * 其它信息:DisplayMetrics{density=2.625, width=1080, height=1794, scaledDensity=2.625, xdpi=420.0, ydpi=420.0}
     * 375:667
     *
     *
     * Raindi ITAB-01 Api22--->当前手机尺寸为:7.5
     * 其它信息:DisplayMetrics{density=1.0, width=600, height=976, scaledDensity=1.0, xdpi=160.0, ydpi=160.0}
     * 482  820
     *
     *
     * KTE X20 Api26--->9.5
     * 其它信息:DisplayMetrics{density=2.0, width=1600, height=2464, scaledDensity=2.0, xdpi=320.0, ydpi=320.0}
     *
     *
     * 580  960
     *
     * @return 返回不同尺寸终端适应的宽，注意，你们自己需要匹配的平板的数值自己去尝试，这里只是参考。
    </meta-data> */
    fun getAutoSizeWidth(context: Context): Int {
        // 580是9.5寸的商务数据终端的适配值.
        var width = 580
        try {
            val size = getPingMuSize(context)
            width = if (size < 7) {
                360 // height = 667
            } else if (size >= 7 && size < 8) {
                482 // height = 820
            } else {
                580 // height = 960
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            return width
        }
    }

    fun getAutoSizeLandscapeWidth(context: Context): Int {
        // 580是9.5寸的商务数据终端的适配值.
//        int width = 580;
        var width = 960
        try {
            val size = getPingMuSize(context)
            width = if (size < 6) {
                640
            } else if (size >= 6 && size < 7) {
                720 // height = 667
            } else if (size >= 7 && size < 8) {
                820 // height = 820
            } else {
                960 // height = 960
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            return width
        }
    }

    /**
     * 获取当前屏幕的尺寸大小
     *
     * @return
     */
    fun getMetrics(context: Context): DisplayMetrics? {
        val metrics = DisplayMetrics()
        val manager = context.applicationContext
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        manager.defaultDisplay.getMetrics(metrics)
        return metrics
    }

    fun isLandscape(context: Context): Boolean {
        val configuration: Configuration = context.resources.configuration
        return configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    fun isPortrait(context: Context): Boolean {
        val configuration: Configuration = context.resources.configuration
        return configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }

}