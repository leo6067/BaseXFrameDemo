package com.xy.demo.base
import com.xy.demo.BR
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding

import com.xy.xframework.base.XBaseFragment
import com.xy.xframework.base.XBaseViewModel

abstract class MBBaseFragment<T : ViewDataBinding, VM : XBaseViewModel> : XBaseFragment<T, VM>()
    {

    val TAG: String = this::class.java.simpleName



    override fun initVariableId(): Int = BR.viewModel



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

    }

    override fun onDestroy() {
        super.onDestroy()

    }
}