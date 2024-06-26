package com.xy.xframework.dialog.loading

import android.app.Dialog
import android.content.Context

/**
 * 创建加载中弹框接口  后续各自的业务有需要自定义baseActivity里面的加载对话框，可是通过这个进行创建
 */
interface LoadingDialogCreate {

    /**
     * 创建加载中弹框
     * <p>
     * @param context Context 上下文
     * @param title String? 标题
     * @return Dialog
     */
    fun createLoadingDialog(context: Context, title: String?): Dialog

}

