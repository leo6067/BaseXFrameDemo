package com.xy.demo.ui.mine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityLanguageBinding
import com.xy.demo.model.LanguageModel
import com.xy.demo.ui.adapter.LanguageAdapter
import com.xy.demo.ui.vm.MainViewModel
import com.xy.demo.utils.LanguageUtil
import java.util.Locale

class LanguageActivity : MBBaseActivity<ActivityLanguageBinding,MainViewModel>() {
	
	
	var mAdapter = LanguageAdapter()
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	
	override fun getLayoutId(): Int {
		return R.layout.activity_language
	}
	
	
	
	override fun initView() {
		super.initView()
		
		titleBarView?.setTitle("语言设置")
	 
		this.mRecyclerView = binding.recyclerView
		initRecycler(1, 1, 0)
		mRecyclerView!!.adapter = mAdapter
		
		val optionBeenList: MutableList<LanguageModel> = java.util.ArrayList<LanguageModel>()
//		optionBeenList.add(LanguageModel("默认语言", "", R.drawable.mcountry,false))
		optionBeenList.add(LanguageModel("English", "en", R.drawable.icon_q_mcountry, TextUtils.equals("en", LanguageUtil.getLanguage())))
		optionBeenList.add(LanguageModel("繁體中文", "tw", R.drawable.icon_q_zh, TextUtils.equals("tw", LanguageUtil.getLanguage())))
		optionBeenList.add(LanguageModel("しろうと", "ja", R.drawable.icon_q_ja, TextUtils.equals("ja", LanguageUtil.getLanguage())))
		optionBeenList.add(LanguageModel("한국어", "ko", R.drawable.icon_q_ko, TextUtils.equals("ko", LanguageUtil.getLanguage())))
		optionBeenList.add(LanguageModel("العربية", "ar", R.drawable. icon_q_ar, TextUtils.equals("ar", LanguageUtil.getLanguage())))
		mAdapter.setNewInstance(optionBeenList)
		
		LanguageUtil.getDefaultLocale(this)
		mAdapter.setOnItemClickListener { adapter, view, position ->
			val languageCode = mAdapter.data[position].languageCode
			mAdapter.data[position].isCheck = true
			
			var mlocale = Locale.getDefault()
			when (languageCode) {
				"" -> mlocale = LanguageUtil.getDefaultLocale(this)
				"en" -> mlocale = Locale.UK
				"tw" -> mlocale = Locale.TRADITIONAL_CHINESE
				"ja" -> mlocale = Locale.JAPAN
				"ko" -> mlocale = Locale.KOREA
				"ar" -> mlocale = Locale("ar", "Arabic")
			}
			LanguageUtil.reFreshLanguage(mlocale, this, LanguageActivity::class.java)
			mAdapter.notifyDataSetChanged()
		}
	}
	
	
	override fun onClick(view: View) {
		finish()
	}
}