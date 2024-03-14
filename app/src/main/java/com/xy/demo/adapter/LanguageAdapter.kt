package com.xy.demo.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xy.demo.R

import com.xy.demo.model.LanguageModel
import com.xy.demo.network.Globals

class LanguageAdapter : BaseQuickAdapter<LanguageModel, BaseViewHolder>(R.layout.item_language) {
 
	var initCheckIndex = -1
	
	
	public fun upCheck(index :Int){
		initCheckIndex = index
	}
	
 
	
	override fun convert(baseViewHolder: BaseViewHolder, bean: LanguageModel) {
		val checkTV = baseViewHolder.getView<ImageView>(R.id.checkTV)
		val flagIV = baseViewHolder.getView<ImageView>(R.id.flagIV)
		val titleTV = baseViewHolder.getView<TextView>(R.id.titleTV)
		
		Glide.with(flagIV).load(bean.flagImg).into(flagIV)
		titleTV.text = bean.languageName
		
		
//		if (initCheckIndex !=-1 && baseViewHolder.adapterPosition == initCheckIndex) {
//			checkTV.visibility = View.VISIBLE
//		} else {
//			checkTV.visibility = View.GONE
//		}
		
		if (bean.isCheck) {
			checkTV.setBackgroundResource(R.drawable.language_check)
			checkTV.visibility = View.VISIBLE
		}  else{
			checkTV.visibility = View.GONE
		}
 
	}
}