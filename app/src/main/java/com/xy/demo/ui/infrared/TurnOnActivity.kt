package com.xy.demo.ui.infrared

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityTurnOnBinding


// 询问电视 是否开机
class TurnOnActivity : MBBaseActivity<ActivityTurnOnBinding, MBBaseViewModel>() {
	
	
	override fun showTitleBar(): Boolean {
		return false
	}
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	override fun getLayoutId(): Int {
		return R.layout.activity_turn_on
	}
	
	
	override fun onClick(view: View) {
		
		when (view.id) {
			R.id.backIV -> finish()
			R.id.closeTV -> finish()
			R.id.nextTV -> {
				val remoteModel =  intent.getSerializableExtra(Constants.KEY_REMOTE)
				val intent = Intent()
				intent.putExtra(Constants.KEY_REMOTE,remoteModel)
				intent.setClass(this@TurnOnActivity,TestRemoteActivity::class.java)
				startActivity(intent)
				finish()
			}
		}
		
	}
}