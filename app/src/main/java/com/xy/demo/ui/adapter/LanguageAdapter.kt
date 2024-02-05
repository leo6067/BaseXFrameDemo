package com.xy.demo.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ruffian.library.widget.RImageView
import com.ruffian.library.widget.RTextView
import com.xy.demo.R
import com.xy.demo.model.LanguageModel
import com.xy.demo.network.Globals
import okhttp3.internal.notify
import okhttp3.internal.notifyAll

class LanguageAdapter : BaseQuickAdapter<LanguageModel, BaseViewHolder>(R.layout.item_language) {
	
	var checkIndex: Int = 0
	
	fun checkPosition(checkPosition: Int) {
		checkIndex = checkPosition
		notifyDataSetChanged()
	}
	
	override fun convert(baseViewHolder: BaseViewHolder, bean: LanguageModel) {
		val checkTV = baseViewHolder.getView<ImageView>(R.id.checkTV)
		val flagIV = baseViewHolder.getView<ImageView>(R.id.flagIV)
		val titleTV = baseViewHolder.getView<TextView>(R.id.titleTV)
		
		Glide.with(flagIV).load(bean.flagImg).into(flagIV)
		titleTV.text = bean.languageName
		
		
		if (bean.isCheck){
			checkTV.setBackgroundResource(R.drawable.language_check)
		}else{
			checkTV.setBackgroundResource(R.drawable.language_uncheck)
		}
		
//		if (baseViewHolder.adapterPosition == checkIndex) {
//			checkTV.setBackgroundResource(R.drawable.language_check)
//		} else {
//			checkTV.setBackgroundResource(R.drawable.language_uncheck)
//		}
		
	}
}