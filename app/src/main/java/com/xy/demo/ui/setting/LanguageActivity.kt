package com.xy.demo.ui.setting

import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityLanguageBinding
import com.xy.demo.logic.LanguageUtil
import com.xy.demo.model.LanguageModel
import com.xy.demo.ui.adapter.LanguageAdapter
import java.util.Locale

class LanguageActivity : MBBaseActivity<ActivityLanguageBinding,MBBaseViewModel>() {
 
	
	var mAdapter = LanguageAdapter()
 
 
	override fun showTitleBar(): Boolean {
		return false
	}
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	override fun getLayoutId(): Int {
		return R.layout.activity_language
	}
	
	override fun initView() {
		super.initView()
		binding.titleLay.titleTV.text= "Choose Language"
		this.mRecyclerView = binding.recyclerView
		initRecycler(1,1,0)
		mRecyclerView!!.adapter = mAdapter
 
		
		
		val optionBeenList: MutableList<LanguageModel> = java.util.ArrayList<LanguageModel>()
//		optionBeenList.add(LanguageModel("默认语言", "", R.drawable.mcountry,false))
		optionBeenList.add(LanguageModel("繁體中文", "tw", R.drawable.icon_zh,false))
		optionBeenList.add(LanguageModel("しろうと", "ja", R.drawable.icon_ja,false))
		optionBeenList.add(LanguageModel("한국어", "ko", R.drawable.icon_ko,false))
		optionBeenList.add(LanguageModel("English", "en", R.drawable.mcountry,false))
		
		
		mAdapter.setNewInstance(optionBeenList)
		
		LanguageUtil.getDefaultLocale(this)
		mAdapter.setOnItemClickListener { adapter, view, position ->
			
			val languageCode = mAdapter.data[position].languageCode
			mAdapter.data[position].isCheck = true
			
			var mlocale = Locale.getDefault()
			
			when (languageCode) {
				 ""  -> mlocale = LanguageUtil.getDefaultLocale(this)
				"en" -> mlocale = Locale.UK
				"tw" -> mlocale = Locale.TRADITIONAL_CHINESE
				"ja" -> mlocale = Locale.JAPAN
				"ko" -> mlocale = Locale.KOREA
			}
			LanguageUtil.reFreshLanguage(mlocale,this, LanguageActivity::class.java)
			
			
			mAdapter.checkPosition(position)
  
		}
	}
	
	
	override fun onClick(view: View){
		finish()
	}
	
	
	
}