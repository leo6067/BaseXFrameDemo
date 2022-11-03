package com.xy.xframework.dialog.loading

import android.app.Dialog
import android.content.Context

/**
 * Desc: 默认加载中弹框
 */
class DefaultLoadingDialog : LoadingDialogCreate {

    override fun createLoadingDialog(context: Context, title: String?): Dialog {
        return LoadingDialog.Builder(context)
            .setMessage(title)
            .setShowMessage(!title.isNullOrBlank())
            .create()
    }
}