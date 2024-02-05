package com.xy.demo.ui.infrared

import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivitySaveRemoteBinding
import com.xy.demo.db.MyDataBase
import com.xy.demo.db.RemoteModel
import com.xy.demo.ui.vm.MainViewModel
import com.xy.xframework.utils.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.format

//保存遥控器
class SaveRemoteActivity : MBBaseActivity<ActivitySaveRemoteBinding, MainViewModel>() {
	
	
	var remoteModel = RemoteModel()
	
	override fun showTitleBar(): Boolean {
		return false
	}
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	override fun getLayoutId(): Int {
		return R.layout.activity_save_remote
	}
	
	override fun initView() {
		super.initView()
		
		remoteModel = intent.getSerializableExtra(Constants.KEY_REMOTE) as RemoteModel
		
		binding.titleLay.titleTV.text = getString(R.string.save_the_remote)
		
		
		if (!TextUtils.isEmpty(remoteModel.brandName)) {
			
			binding.modelName.setText(String.format(resources.getString(R.string.s_tv_connected), remoteModel.brandName))
		 
			binding.hardwareET.setText(remoteModel.brandName)
		}
		
		
		remoteModel.name = binding.hardwareET.text.toString()
		remoteModel.location = binding.defaultRB.text.toString()
		
		binding.locationRG.setOnCheckedChangeListener { group, checkedId ->
			when (checkedId) {
				R.id.defaultRB -> {
					remoteModel.location = binding.defaultRB.text.toString()
				}
				
				R.id.bedRB -> {
					remoteModel.location = binding.bedRB.text.toString()
				}
				
				R.id.livingRB -> {
					remoteModel.location = binding.livingRB.text.toString()
				}
				
				R.id.dinningRB -> {
					remoteModel.location = binding.dinningRB.text.toString()
				}
				
				R.id.mediaRB -> {
					remoteModel.location = binding.mediaRB.text.toString()
				}
			}
			
		}
		
		
		
		binding.colorRG.setOnCheckedChangeListener { group, checkedId ->
			when (checkedId) {
				R.id.blueRB -> {
					binding.tvIV.setBackgroundResource(R.drawable.icon_save_tv_blue)
					remoteModel.color = 1
				}
				
				R.id.redRB -> {
					binding.tvIV.setBackgroundResource(R.drawable.icon_save_tv_red)
					remoteModel.color = 2
				}
				
				R.id.pinkRG -> {
					binding.tvIV.setBackgroundResource(R.drawable.icon_save_tv_pink)
					remoteModel.color = 3
				}
				
				R.id.yellowRB -> {
					binding.tvIV.setBackgroundResource(R.drawable.icon_save_tv_yellow)
					remoteModel.color = 4
				}
			}
		}
		
		
		showLoading()
		viewModel.getOrderListHttp(remoteModel.brandId, remoteModel.modelId)
		
	}
	
	
	override fun onClick(view: View) {
		when (view.id) {
			R.id.backIV -> finish()
			R.id.clearTV -> binding.hardwareET.setText("")
			R.id.saveTV -> {
				remoteModel.name = binding.hardwareET.text.toString()
				GlobalScope.launch(Dispatchers.IO) {
					if (MyDataBase.instance.RemoteDao().getByName(remoteModel.name) == null) {
						MyDataBase.instance.RemoteDao().insert(remoteModel)  // 插入数据
						
						withContext(Dispatchers.Main) {
							showLoading()
							delay(1000)
							val intent = Intent()
							intent.putExtra(Constants.KEY_REMOTE, remoteModel)
							intent.setClass(this@SaveRemoteActivity, TVConActivity::class.java)
							startActivity(intent)
							
						}
						finish()
					} else {
						withContext(Dispatchers.Main) {
							ToastUtils.showShort(getString(R.string.the_name_change_has_already_been_used))
						}
					}
				}
				
			}
		}
	}
}