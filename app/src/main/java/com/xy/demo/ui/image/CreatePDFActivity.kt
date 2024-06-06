package com.xy.demo.ui.image

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityCreatePdfBinding
import com.xy.demo.ui.vm.MainViewModel


//生成PDF  参数设置
class CreatePDFActivity : MBBaseActivity<ActivityCreatePdfBinding,MainViewModel>() {
	
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	override fun getLayoutId(): Int {
		return R.layout.activity_create_pdf
	}
}