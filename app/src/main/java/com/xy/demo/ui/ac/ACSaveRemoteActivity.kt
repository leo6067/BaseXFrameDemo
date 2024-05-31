package com.xy.demo.ui.ac

import android.content.Intent
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivitySaveAcremoteBinding
import com.xy.demo.db.MyDataBase
import com.xy.demo.db.RemoteModel
import com.xy.demo.network.Globals
import com.xy.demo.ui.common.BrandActivity
import com.xy.demo.ui.common.ReadyActivity
import com.xy.demo.ui.infrared.TVConActivity
import com.xy.demo.ui.infrared.TestOrderActivity
import com.xy.demo.ui.infrared.TvPowerOnActivity
import com.xy.demo.ui.vm.HttpViewModel
import com.xy.xframework.utils.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ACSaveRemoteActivity : MBBaseActivity<ActivitySaveAcremoteBinding,HttpViewModel>() {
	var remoteModel = RemoteModel()
	
	override fun showTitleBar(): Boolean {
		return false
	}
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	override fun getLayoutId(): Int {
		return R.layout.activity_save_acremote
	}
	
	
	override fun initView() {
		super.initView()
		remoteModel = intent.getSerializableExtra(Constants.KEY_REMOTE) as RemoteModel
		binding.titleLay.titleTV.text = getString(R.string.save_the_remote)
		binding.modelName.text = String.format(getString(R.string.s_ac_connected), remoteModel.brandName)
		
		if (TextUtils.isEmpty(remoteModel.name)) {
			binding.hardwareET.setText(remoteModel.brandName)
			remoteModel.name = binding.hardwareET.text.toString()
		} else {
			binding.hardwareET.setText(remoteModel.name)
		}
		
		when (remoteModel.location) {
			"1" -> binding.locationRG.check(R.id.livingRB)
			"2" -> binding.locationRG.check(R.id.bedRB)
			"3" -> binding.locationRG.check(R.id.dinningRB)
			"4" -> binding.locationRG.check(R.id.mediaRB)
		}
		
		
		
		
		binding.locationRG.setOnCheckedChangeListener { group, checkedId ->
			when (checkedId) {
				R.id.livingRB -> {
					remoteModel.location = "1"
				}
				
				R.id.bedRB -> {
					remoteModel.location = "2"
				}
				
				R.id.dinningRB -> {
					remoteModel.location = "3"
				}
				
				R.id.mediaRB -> {
					remoteModel.location = "4"
				}
			}
		}
		
 
//		viewModel.getOrderListHttp(remoteModel.brandId, remoteModel.modelId)
	}
	
	
	override fun onClick(view: View) {
		when (view.id) {
			R.id.backIV -> finish()
			R.id.clearTV -> binding.hardwareET.setText("")
			R.id.saveTV -> {
				remoteModel.name = binding.hardwareET.text.toString().trim()
				if (TextUtils.isEmpty(remoteModel.name)) {
					ToastUtils.showShort(getString(R.string.please_input_remote_name))
					return
				}
				saveUpSql()
			}
		}
	}
	
	
	fun saveUpSql() {
		if (Constants.isHomeToSave) {
			lifecycleScope.launch(Dispatchers.IO) {
				MyDataBase.instance.RemoteDao().update(remoteModel)
				delay(600)
				dismissLoading()
				finish()
			}
		} else {
			BrandActivity.activity?.finish()
			ReadyActivity.activity?.finish()
			ACTestActivity.activity?.finish()
			
			
			remoteModel.isOpen = 1
			remoteModel.swingInt = 0
			remoteModel.speedInt = 1
			remoteModel.modeInt = 1
			remoteModel.tcInt = 26
			remoteModel.isNewAc = 1
			
			lifecycleScope.launch(Dispatchers.IO) {
				if (MyDataBase.instance.RemoteDao().getByName(remoteModel.name) == null) {
					MyDataBase.instance.RemoteDao().insertOrReplace(remoteModel)  // 插入数据
					remoteModel = MyDataBase.instance.RemoteDao().getByName(remoteModel.name)!!
					withContext(Dispatchers.Main) {
						showLoading()
						delay(600)
						dismissLoading()
						val intent = Intent()
						intent.putExtra(Constants.KEY_REMOTE, remoteModel)
						intent.setClass(this@ACSaveRemoteActivity, ACConActivity::class.java)
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