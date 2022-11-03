package com.xy.xframework.dialog.loading

import android.content.DialogInterface

/**
 * 展示 loading dialog 所需参数
 * @param title String? 标题
 * @param isCancelable Boolean  是否阻塞页面
 * @param isCancelOutside Boolean 是否可以点击外部销毁
 * @param onCancelListener OnCancelListener? 取消加载监听
 */
data class ShowDialogEntity(
    var title: String? = null,
    var isCancelable: Boolean,
    var isCancelOutside: Boolean,
    var onCancelListener: DialogInterface.OnCancelListener?
)