package com.xy.demo.ui.setting

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.result.contract.ActivityResultContract
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityFeedBackBinding
import com.xy.demo.ui.infrared.BrandActivity
import com.xy.demo.ui.vm.HttpViewModel
import com.xy.xframework.utils.ToastUtils


//反馈
class FeedBackActivity : MBBaseActivity<ActivityFeedBackBinding, HttpViewModel>() {
	
	
	var brandIdStr = ""
	var feedType = 1
	
	
	override fun showTitleBar(): Boolean {
		return false
	}
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	override fun getLayoutId(): Int {
		return R.layout.activity_feed_back
	}
	
	override fun initView() {
		super.initView()
	 
		
		//常规带回调启动Activity
		val requestDataLauncher = registerForActivityResult(object : ActivityResultContract<String, String>() {
			override fun createIntent(context: Context, input: String?): Intent {
				//创建启动页面所需的Intent对象，传入需要传递的参数
				return Intent(this@FeedBackActivity, BrandActivity::class.java).apply {
					putExtra(Constants.KEY_FEEDBACK, input)
				}
			}
			
			override fun parseResult(resultCode: Int, intent: Intent?): String {
				//页面回传的数据解析，相当于原onActivityResult方法
				val data = intent?.getStringExtra(Constants.KEY_TV_BRAND) ?: ""
				brandIdStr = intent?.getStringExtra(Constants.KEY_TV_BRAND_ID) ?: ""
				return if (resultCode == RESULT_OK) data else ""
			}
		}) {
			binding.brandTV.text = it
			binding.noBrandTV.visibility = View.GONE
		}
		
		
		binding.brandTV.setOnClickListener {
			requestDataLauncher.launch("FeedBackActivity")
		}
		
		
	}
	
	override fun initViewObservable() {
		super.initViewObservable()
		
		viewModel.resultStr.observe(this){
			dismissLoading()
			finish()
		}
	}
	
	
	override fun onClick(view: View) {
		when (view.id) {
			R.id.backIV -> finish()
			R.id.problemTV -> {
				popupWindow(this@FeedBackActivity, view)
			}
			
			R.id.submitTV -> {
				submit()
			}
		}
	}
	
	
	fun submit() {
		
		if (binding.brandTV.text.isEmpty()) {
			binding.noBrandTV.visibility = View.VISIBLE
			return
		}
		
		if (binding.problemTV.text.isEmpty()) {
			binding.noProblemTV.visibility = View.VISIBLE
			return
		}
		if (binding.detailET.text.toString().isEmpty()) {
			ToastUtils.showLong(getString(R.string.please_enter_feedback))
			return
		}
		
		
		//接口
		
		showLoading()
		viewModel.postFeedBack(brandIdStr,feedType,binding.detailET.text.toString())
		
		
	}
	
	
	fun popupWindow(context: Context, view: View?) {
		// 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
		val contentView = LayoutInflater.from(context).inflate(R.layout.popup_window_problem, null)
		val popupWindow = PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)
		
		// 设置弹出窗口的背景色
		popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		// 设置弹出窗口的位置
		popupWindow.showAsDropDown(view, 40, 0)
		
		//设置可以获取焦点
		popupWindow.isFocusable = true
		//设置可以触摸弹出框以外的区域
		popupWindow.isOutsideTouchable = true
		
		
		val radioGroup = contentView.findViewById<RadioGroup>(R.id.radioGroup)
		val nwRB = contentView.findViewById<RadioButton>(R.id.nwRB)
		val sbRB = contentView.findViewById<RadioButton>(R.id.sbRB)
		val ccRB = contentView.findViewById<RadioButton>(R.id.ccRB)
		val otherRB = contentView.findViewById<RadioButton>(R.id.otherRB)
		
		
		radioGroup.setOnCheckedChangeListener { group, checkedId ->
			when (checkedId) {
				R.id.nwRB -> {
					binding.problemTV.text = nwRB.text
					feedType = 1
				}
				
				R.id.sbRB -> {
					binding.problemTV.text = sbRB.text
					feedType = 2
				}
				
				R.id.ccRB -> {
					binding.problemTV.text = ccRB.text
					feedType = 3
				}
				
				R.id.otherRB -> {
					binding.problemTV.text = otherRB.text
					feedType = 4
				}
			}
			binding.noProblemTV.visibility = View.GONE
			popupWindow.dismiss()
		}
		
		
	}
}