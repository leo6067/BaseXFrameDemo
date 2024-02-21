package com.xy.demo.ui.infrared

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivitySaveRemoteBinding
import com.xy.demo.db.MyDataBase
import com.xy.demo.db.RemoteModel
import com.xy.demo.ui.vm.HttpViewModel
import com.xy.xframework.utils.DigitKeyboardUtils
import com.xy.xframework.utils.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


//保存遥控器
class SaveRemoteActivity : MBBaseActivity<ActivitySaveRemoteBinding, HttpViewModel>() {
	
	
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
		binding.modelName.text = String.format(resources.getString(R.string.s_tv_connected), remoteModel.brandName)
		
		
		if (TextUtils.isEmpty(remoteModel.name)) {
			binding.hardwareET.setText(remoteModel.brandName)
			remoteModel.name = binding.hardwareET.text.toString()
		} else {
			binding.hardwareET.setText(remoteModel.name)
		}
		
		
		
		
		when (remoteModel.location) {
			"1" -> binding.locationRG.check(R.id.defaultRB)
			"2" -> binding.locationRG.check(R.id.livingRB)
			"3" -> binding.locationRG.check(R.id.bedRB)
			"4" -> binding.locationRG.check(R.id.dinningRB)
			"5" -> binding.locationRG.check(R.id.mediaRB)
		}
		
		when (remoteModel.color) {
			1 -> {
				binding.colorRG.check(R.id.blueRB)
				binding.tvIV.setBackgroundResource(R.drawable.icon_save_tv_blue)
			}
			
			2 -> {
			    binding.colorRG.check(R.id.redRB)
				binding.tvIV.setBackgroundResource(R.drawable.icon_save_tv_red)
			}
			3 -> {
				binding.colorRG.check(R.id.pinkRG)
				binding.tvIV.setBackgroundResource(R.drawable.icon_save_tv_pink)
			}
		
			4 -> {
				binding.colorRG.check(R.id.yellowRB)
				binding.tvIV.setBackgroundResource(R.drawable.icon_save_tv_yellow)
			}
		
		}
		
		
		
		
		binding.locationRG.setOnCheckedChangeListener { group, checkedId ->
			when (checkedId) {
				R.id.defaultRB -> {
					remoteModel.location = "1"
				}
				
				R.id.livingRB -> {
					remoteModel.location = "2"
				}
				
				R.id.bedRB -> {
					remoteModel.location = "3"
				}
				
				R.id.dinningRB -> {
					remoteModel.location = "4"
				}
				
				R.id.mediaRB -> {
					remoteModel.location = "5"
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
			AddWayActivity.activity?.finish()
			BrandActivity.activity?.finish()
			TurnOnActivity.activity?.finish()
			ReadyActivity.activity?.finish()
			TestRemoteActivity.activity?.finish()
		 
			lifecycleScope.launch(Dispatchers.IO) {
				if (MyDataBase.instance.RemoteDao().getByName(remoteModel.name) == null) {
					MyDataBase.instance.RemoteDao().insert(remoteModel)  // 插入数据
					withContext(Dispatchers.Main) {
						showLoading()
						delay(600)
						dismissLoading()
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
	
	
	override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
		if (ev.action == MotionEvent.ACTION_DOWN) {
			val v = currentFocus
			if (isShouldHideInput(v, ev)) {
				val imm =  getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
				imm?.hideSoftInputFromWindow(v!!.windowToken, 0)
			}
			return super.dispatchTouchEvent(ev)
		}
		// 必不可少，否则所有的组件都不会有TouchEvent了
		return if (window.superDispatchTouchEvent(ev)) {
			true
		} else onTouchEvent(ev)
	}
	
	
	fun isShouldHideInput(v: View?, event: MotionEvent): Boolean {
		if (v != null && v is EditText) {
			val leftTop = intArrayOf(0, 0)
			//获取输入框当前的location位置
			v.getLocationInWindow(leftTop)
			val left = leftTop[0]
			val top = leftTop[1]
			val bottom = top + v.getHeight()
			val right = left + v.getWidth()
			return !(event.x > left && event.x < right && event.y > top && event.y < bottom)
		}
		return false
	}
}