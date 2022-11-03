package com.xy.xframework.extensions

import com.xy.xframework.utils.ResourceUtils


/**
 * 资源扩展类
 */

/**
 * Float 转dp   返回值为float类型
 */
fun Float.dp(): Float {
    return ResourceUtils.dp2px(this)
}

/**
 * int 类型转dp  返回值为float类型
 */
fun Int.dp() = toFloat().dp()

/**
 * Float 转dp   返回值为int类型
 */
fun Float.idp() = dp().toInt()

/**
 * int 类型转dp  返回值为int类型
 */
fun Int.idp() = dp().toInt()

/**
 * Float 转sp   返回值为float类型
 */
fun Float.sp(): Float {
    return ResourceUtils.dp2px(this)
}

/**
 * Float 转sp   返回值为float类型
 */
fun Float.isp(): Int {
    return ResourceUtils.dp2px(this).toInt()
}

/**
 * int 转sp   返回值为float类型
 */
fun Int.sp() = toFloat().sp()


/**
 * int 转sp   返回值为float类型
 */
fun Int.isp() = toFloat().isp()

/**
 * int 转color
 * eg. R.color.color_FFFFFF.color()
 */
fun Int.color() = ResourceUtils.getColorResource(this)

/**
 * int 转string
 */
fun Int.string(): String = ResourceUtils.getStringResource(this)

/**
 * int 转string  带占位符的字符串资源
 */
fun Int.string(vararg args: Any?): String = ResourceUtils.getStringResource(this).format(*args)

/**
 * 图片资源获取
 */
fun Int.drawable() = ResourceUtils.getDrawable(this)