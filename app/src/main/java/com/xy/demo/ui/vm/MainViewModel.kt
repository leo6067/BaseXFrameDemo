package com.xy.demo.ui.vm

import android.app.Activity
import android.app.Application
import com.xy.demo.MainActivity
import com.xy.demo.R
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.base.MyApplication
import com.xy.demo.model.ToolsModel
import com.xy.xframework.command.SingleLiveEvent

class MainViewModel(application: Application) : MBBaseViewModel(application){
	
	var transModels = SingleLiveEvent<ArrayList<ToolsModel>>()
	var transList = ArrayList<ToolsModel>()
	
	var editModels = SingleLiveEvent<ArrayList<ToolsModel>>()
	var editLists = ArrayList<ToolsModel>()
	
	var settingModels = SingleLiveEvent<ArrayList<ToolsModel>>()
	var settingLists = ArrayList<ToolsModel>()
	
	fun getTransList(activity: Activity){
		transList.add(ToolsModel(R.drawable.ic_home_top_j,activity.getString(R.string.image_to_pdf)))
		transList.add(ToolsModel(R.drawable.ic_home_top_e, activity.getString(R.string.pdf_to_image)))
		transModels.value= transList
	}
	
	fun getEditList(activity: Activity){
		editLists.add(ToolsModel(R.drawable.ic_home_top_k,activity.getString(R.string.compress)))
		editLists.add(ToolsModel(R.drawable.ic_home_top_l,activity.getString(R.string.extract_text)))
		editLists.add(ToolsModel(R.drawable.ic_home_top_f,activity.getString(R.string.lock_pdf)))
		editLists.add(ToolsModel(R.drawable.ic_home_top_g, activity.getString(R.string.unlock_pdf)))
		editModels.value= editLists
	}
	
	
	
	fun getSettingList(activity: Activity){
		settingLists.add(ToolsModel(R.drawable.setting_share, activity.getString(R.string.share_app)))
		settingLists.add(ToolsModel(R.drawable.setting_languare, activity.getString(R.string.language)))
		settingLists.add(ToolsModel(R.drawable.setting_feed,activity.getString(R.string.feedback)))
		settingLists.add(ToolsModel(R.drawable.setting_privte,activity.getString(R.string.privacy_policy)))
		settingLists.add(ToolsModel(R.drawable.setting_rate, activity.getString(R.string.evaluate_us)))
		settingLists.add(ToolsModel(R.drawable.setting_version, activity.getString(R.string.version)))
		settingModels.value= settingLists
	}
	
}