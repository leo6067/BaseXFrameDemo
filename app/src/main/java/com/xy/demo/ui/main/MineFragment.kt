package com.xy.demo.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.xy.demo.R
import com.xy.demo.base.MBBaseFragment
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.FragmentMineBinding
import com.xy.demo.ui.main.viewModel.MineViewModel
import com.xy.xframework.base.XBaseFragment


class MineFragment : MBBaseFragment<FragmentMineBinding, MineViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initView() {


        binding.mineRecyclerView.layoutManager = LinearLayoutManager(activity)

    }

    override fun initArgument() {
        super.initArgument()


    }


}