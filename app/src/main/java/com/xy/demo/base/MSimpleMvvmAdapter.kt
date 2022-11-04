package com.xy.demo.base
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

open class MSimpleMvvmAdapter<T, BD : ViewDataBinding>(
    var beanId: Int,
    layoutId: Int,
    data: MutableList<T>? = null
) :
    BaseQuickAdapter<T, BaseDataBindingHolder<BD>>(layoutId, data) {
    override fun convert(holder: BaseDataBindingHolder<BD>, item: T) {
        holder.dataBinding?.setVariable(beanId, item)
        holder.dataBinding?.executePendingBindings()

    }
}