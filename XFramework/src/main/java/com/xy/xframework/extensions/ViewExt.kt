package com.xy.xframework.extensions

import android.view.View


/**
 * 扩展方法：设置点击事件，防止快速点击
 * @receiver View
 * @param onclick Function1<View, Unit>
 * @param ignoreTime Int
 */
fun View?.setSingleClick(ignoreTime: Int = 1000, onclick: (View) -> Unit) {
    var mLastClickMills = 0L
    this?.setOnClickListener {
        try {
            val currentTimeMillis = System.currentTimeMillis()
            if (currentTimeMillis - mLastClickMills >= ignoreTime) {
                mLastClickMills = currentTimeMillis
                onclick.invoke(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
