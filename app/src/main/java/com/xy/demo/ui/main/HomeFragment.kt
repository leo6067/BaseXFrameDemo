package com.xy.demo.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.xy.demo.R
import com.xy.demo.base.MBBaseFragment
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.FragmentHomeBinding
import com.xy.demo.network.Globals
import com.xy.demo.network.NetLaunchManager
import com.xy.demo.network.NetManager
import com.xy.demo.ui.main.adapter.HomeAdapter
import com.xy.demo.ui.main.viewModel.MainViewModel
import java.util.zip.Inflater

class HomeFragment : MBBaseFragment<FragmentHomeBinding, MainViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_home;
    }

    override fun initView() {

        initRecyclerView(binding.recyclerView, LinearLayoutManager.VERTICAL, 0)
        val homeAdapter = HomeAdapter()
        binding.recyclerView.adapter = homeAdapter

        val bannerView = layoutInflater.inflate(R.layout.include_banner, null)
        homeAdapter.addHeaderView(bannerView)

        binding.refreshView.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {

            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {

            }
        })
    }


    override fun initArgument() {
        super.initArgument()
        readerParams?.let { viewModel.getCheckSetting(it) }
        readerParams?.let { viewModel.getVideoStore(it) }
    }


    override fun initViewObservable() {
        super.initViewObservable()

        viewModel.videoStoreModel.observe(this) {
            Globals.log("xxxxxxx" + it.label.toString())
        }
    }


}