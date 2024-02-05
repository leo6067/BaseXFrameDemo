package com.xy.demo.ui.infrared

import android.content.Intent
import android.graphics.Paint
import android.view.View
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityAddWayBinding
import com.xy.demo.db.RemoteModel
import com.xy.demo.ui.dialog.StartWayDialog


//添加 遥控电器  红外或者wifi
class AddWayActivity : MBBaseActivity<ActivityAddWayBinding, MBBaseViewModel>() {
	
	var remoteModel = RemoteModel()
	
	
	override fun showTitleBar(): Boolean {
		return false
	}
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	override fun getLayoutId(): Int {
		return R.layout.activity_add_way
	}
	
	override fun initView() {
		super.initView()
		binding.knowTV.paint.flags = Paint.UNDERLINE_TEXT_FLAG
	}
	
	
	override fun onClick(view: View) {
		when (view.id) {
			R.id.backIV -> finish()
			R.id.irIV -> StartWayDialog().show(supportFragmentManager, "1")
			R.id.smartIV -> StartWayDialog().show(supportFragmentManager, "2")
			R.id.irLin -> {
				
				remoteModel.type = 1
				val intent = Intent()
				intent.putExtra(Constants.KEY_REMOTE, remoteModel)
				intent.setClass(this@AddWayActivity, BrandActivity::class.java)
				startActivity(intent)
				finish()
				
			}
			
			R.id.smartLin -> {
				remoteModel.type = 2
				val intent = Intent()
				intent.putExtra(Constants.KEY_REMOTE, remoteModel)
				intent.setClass(this@AddWayActivity, BrandActivity::class.java)
				startActivity(intent)
				finish()
			}
			
			R.id.knowTV -> {
				remoteModel.type = 1
				val intent = Intent()
				intent.putExtra(Constants.KEY_REMOTE, remoteModel)
				intent.setClass(this@AddWayActivity, BrandActivity::class.java)
				startActivity(intent)
				finish()
			}
			
			
		}
		
	}
}