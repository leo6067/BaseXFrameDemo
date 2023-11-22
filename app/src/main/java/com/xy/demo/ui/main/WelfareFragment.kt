package com.xy.demo.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xy.demo.R
import com.xy.demo.base.MBBaseFragment
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.FragmentWelfareBinding


class WelfareFragment : MBBaseFragment<FragmentWelfareBinding, MBBaseViewModel>() {


    override fun getLayoutId(): Int {
        return R.layout.fragment_welfare;
    }

    override fun initView() {

    }


}