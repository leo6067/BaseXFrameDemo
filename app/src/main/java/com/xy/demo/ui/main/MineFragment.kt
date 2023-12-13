package com.xy.demo.ui.main

import androidx.recyclerview.widget.LinearLayoutManager
import com.xy.demo.R
import com.xy.demo.base.MBBaseFragment
import com.xy.demo.databinding.FragmentMineBinding
import com.xy.demo.model.MineModel
import com.xy.demo.network.Globals
import com.xy.demo.ui.main.adapter.MineAdapter
import com.xy.demo.ui.main.viewModel.MineViewModel


class MineFragment : MBBaseFragment<FragmentMineBinding, MineViewModel>() {


    var mAdapter = MineAdapter()

    var listData = arrayListOf(MineModel.PanelList())

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initView() {
        initRecyclerView(binding.mineRecyclerView, LinearLayoutManager.VERTICAL, 0)

        val topLayout = layoutInflater.inflate(R.layout.include_mine_top, null)
        binding.mineRecyclerView.adapter = mAdapter
        mAdapter.removeAllHeaderView()
        mAdapter.addHeaderView(topLayout)


        mAdapter.setOnItemClickListener{adapter,view,position ->


        }



    }

    override fun initArgument() {
        super.initArgument()
        readerParams?.let { viewModel.getUserInfo(it) }
    }


    override fun initViewObservable() {
        super.initViewObservable()
        listData.reverse()

        viewModel.mineModel.observe(this) {


            for (modelList in it.panel_list) {
                for (model in modelList) {
                    listData.add(model)
                }
            }
            Globals.log("xxxxxxxxxmineModel"+listData.size)
            mAdapter.setNewInstance(listData)
        }
    }


}