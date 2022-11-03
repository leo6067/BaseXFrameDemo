package com.xy.xframework.base

import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

abstract class BaseAdapter<T, D : ViewDataBinding>(layoutResId: Int, data: MutableList<T>? = null) :
    BaseQuickAdapter<T, BaseDataBindingHolder<D>>(layoutResId, data)