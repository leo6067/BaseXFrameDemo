package com.xy.demo.base

import androidx.databinding.ViewDataBinding
import com.xy.xframework.base.XDialogFragment

abstract class MBBaseDialogFragment<VB : ViewDataBinding> : XDialogFragment<VB>() {

    override fun cancelable(): Boolean = true

}