package com.xy.demo.ui.vm

import android.app.Application
import com.xy.demo.R
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.model.ToolsModel
import com.xy.xframework.command.SingleLiveEvent

class MainViewModel(application: Application) : MBBaseViewModel(application){
	
	var transModels = SingleLiveEvent<ArrayList<ToolsModel>>()
	var transList = ArrayList<ToolsModel>()
	
	var editModels = SingleLiveEvent<ArrayList<ToolsModel>>()
	var editLists = ArrayList<ToolsModel>()
	
	var settingModels = SingleLiveEvent<ArrayList<ToolsModel>>()
	var settingLists = ArrayList<ToolsModel>()
	
	fun getTransList(){
		transList.add(ToolsModel(R.drawable.ic_home_top_j,"Image to PDF"))
		transList.add(ToolsModel(R.drawable.ic_home_top_e,"PDF to Image"))
		transModels.value= transList
	}
	
	fun getEditList(){
		editLists.add(ToolsModel(R.drawable.ic_home_top_k,"Compress"))
		editLists.add(ToolsModel(R.drawable.ic_home_top_l,"Extract text"))
		editLists.add(ToolsModel(R.drawable.ic_home_top_f,"Lock PDF"))
		editLists.add(ToolsModel(R.drawable.ic_home_top_g,"UnLock PDF"))
		editModels.value= editLists
	}
	
	
	
	fun getSettingList(){
		settingLists.add(ToolsModel(R.drawable.setting_share,"Share APP"))
		settingLists.add(ToolsModel(R.drawable.setting_languare,"Language"))
		settingLists.add(ToolsModel(R.drawable.setting_feed,"Feedback"))
		settingLists.add(ToolsModel(R.drawable.setting_privte,"Privacy Policy"))
		settingLists.add(ToolsModel(R.drawable.setting_rate,"Evaluate Us"))
		settingLists.add(ToolsModel(R.drawable.setting_version,"Version"))
		settingModels.value= settingLists
	}
	
}