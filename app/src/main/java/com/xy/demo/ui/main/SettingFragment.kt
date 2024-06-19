package com.xy.demo.ui.main

import android.content.ContentResolver
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.xy.demo.R
import com.xy.demo.base.MBBaseFragment
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.FragmentHomeBinding
import com.xy.demo.databinding.FragmentSettingBinding
import com.xy.demo.ui.adapter.SettingAdapter
import com.xy.demo.ui.dialog.RateDialog
import com.xy.demo.ui.mine.FeedBackActivity
import com.xy.demo.ui.mine.LanguageActivity
import com.xy.demo.ui.mine.PrivacyActivity
import com.xy.demo.ui.vm.MainViewModel


open class SettingFragment : MBBaseFragment<FragmentSettingBinding, MainViewModel>() {
	
	val settingAdapter = SettingAdapter()
	override fun getLayoutId(): Int {
		return R.layout.fragment_setting
	}
	
	override fun initView() {
		binding.recyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
		binding.recyclerview.adapter = settingAdapter
		activity?.let { viewModel.getSettingList(it) }
		initObserve()
	}
	
	fun initObserve() {
		viewModel.settingModels.observe(this) {
			settingAdapter.setNewInstance(it)
		}
		
		settingAdapter.setOnItemClickListener { adapter, view, position ->
			when (position) {
				0 -> shareApp()
				1 -> startActivity(Intent(requireActivity(), LanguageActivity::class.java))  //语言
				2 -> startActivity(Intent(requireActivity(), FeedBackActivity::class.java))
				3 -> startActivity(Intent(requireActivity(), PrivacyActivity::class.java))
				4 -> RateDialog().show(requireActivity().supportFragmentManager, "1")
			}
		}
		
	}
	
	
	fun shareApp() {
		val textIntent = Intent(Intent.ACTION_SEND)
		textIntent.type = "text/plain"
		textIntent.putExtra(Intent.EXTRA_TEXT, "share app")
		startActivity(Intent.createChooser(textIntent, "share"))
	}
	
}