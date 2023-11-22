package com.xy.demo.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xy.demo.BR
import com.xy.demo.network.params.ReaderParams
import com.xy.xframework.base.XBaseFragment
import com.xy.xframework.base.XBaseViewModel

abstract class MBBaseFragment<T : ViewDataBinding, VM : XBaseViewModel> : XBaseFragment<T, VM>() {

    val TAG: String = this::class.java.simpleName


    override fun initVariableId(): Int = BR.viewModel


    var readerParams: ReaderParams ?=null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        readerParams = ReaderParams(activity)
    }

    protected open fun initRecyclerView(
        recyclerView: RecyclerView,
        orientation: Int,
        GridLayoutManager_spanCount: Int
    ) {

        if (GridLayoutManager_spanCount == 0) {
            var layoutManager = LinearLayoutManager(activity)
            layoutManager.setOrientation(orientation)
            recyclerView.layoutManager = layoutManager
        } else {
            val gridLayoutManager = GridLayoutManager(activity, GridLayoutManager_spanCount)
            gridLayoutManager.orientation = orientation
            recyclerView.layoutManager = gridLayoutManager
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        try {
//            EventBus.getDefault().unregister(this)
            if (readerParams != null) readerParams?.destroy()
        } catch (e: Throwable) {
        }
    }


}