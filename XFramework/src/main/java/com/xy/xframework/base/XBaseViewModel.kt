package com.xy.xframework.base

import android.app.Application
import android.content.DialogInterface
import android.renderscript.ScriptGroup.Binding
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.AndroidViewModel
import com.xy.xframework.base.XBaseApplication.Companion.application
import com.xy.xframework.dialog.loading.ShowDialogEntity

open class XBaseViewModel(application: Application) : AndroidViewModel(application) {


    val mUiEvent by lazy { UIChangeEvent() }

    /**
     * 关闭界面
     */
    open fun finish() {
        mUiEvent.finishEvent.call()
    }

    /**
     * 返回
     */
    open fun onBackPressed() {
        mUiEvent.onBackPressedEvent.call()
    }

    /**
     * 隐藏弹窗
     */
    fun dismissLoading() {
        mUiEvent.dismissDialogEvent.postValue(null)
    }

    /**
     * 加载弹窗
     */
    fun showLoading(title: String? = null) {
        showLoading(title, isCancelable = true, isCancelOutside = false) {
            finish()
        }
    }
    /**
     * 加载弹窗
     */
    fun showLoading(
        title: String? = null,
        isCancelable: Boolean,
        isCancelOutside: Boolean,
        onCancelListener: DialogInterface.OnCancelListener?
    ) {
        mUiEvent.showDialogEvent.postValue(
            ShowDialogEntity(
                title,
                isCancelable,
                isCancelOutside,
                onCancelListener
            )
        )
    }
}