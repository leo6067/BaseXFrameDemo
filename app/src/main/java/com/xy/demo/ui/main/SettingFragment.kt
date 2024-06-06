package com.xy.demo.ui.main

import android.content.ContentResolver
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.vincent.filepicker.Constant
import com.vincent.filepicker.activity.NormalFilePickActivity
import com.xy.demo.R
import com.xy.demo.base.MBBaseFragment
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.FragmentHomeBinding
import com.xy.demo.databinding.FragmentSettingBinding
import com.xy.demo.ui.adapter.SettingAdapter
import com.xy.demo.ui.dialog.RateDialog
import com.xy.demo.ui.mine.LanguageActivity
import com.xy.demo.ui.mine.PrivacyActivity
import com.xy.demo.ui.vm.MainViewModel
import com.xy.xframework.imagePicker.WeChatPresenter
import com.xy.xframework.utils.Globals
import com.xy.xframework.utils.ToastUtils
import com.ypx.imagepicker.ImagePicker
import com.ypx.imagepicker.bean.MimeType
import com.ypx.imagepicker.bean.selectconfig.CropConfig
import com.ypx.imagepicker.data.OnImagePickCompleteListener
import droidninja.filepicker.FilePickerBuilder


open class SettingFragment : MBBaseFragment<FragmentSettingBinding, MainViewModel>() {
	
	
	val settingAdapter = SettingAdapter()
	override fun getLayoutId(): Int {
		return R.layout.fragment_setting
	}
	
	override fun initView() {
		binding.recyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
		binding.recyclerview.adapter = settingAdapter
		viewModel.getSettingList()
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
				2 -> startActivity(Intent(requireActivity(), LanguageActivity::class.java))  //语言
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