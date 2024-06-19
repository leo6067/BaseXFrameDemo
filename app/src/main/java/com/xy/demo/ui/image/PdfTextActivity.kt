package com.xy.demo.ui.image


import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityPdfTextBinding
import com.xy.demo.model.StringModel
import com.xy.demo.network.Globals
import com.xy.demo.ui.adapter.TextAdapter
import com.xy.demo.ui.view.CenterSnapHelper
import com.xy.demo.ui.view.CenteredLayoutManager
import com.xy.demo.ui.vm.MainViewModel
import com.xy.xframework.base.BaseSharePreference


// pdf 提取文字
class PdfTextActivity : MBBaseActivity<ActivityPdfTextBinding, MainViewModel>() {
	
	var mAdapter = TextAdapter()
	var StringModelList = arrayListOf<StringModel>()
	
	var currentPosition = 0
	
	
	companion object {
		//fromType  1： 最近文件 数据库中  2；公开可得路径
		fun setNewInstance(activity: Activity) {
			val intent = Intent(activity, PdfTextActivity::class.java)
			activity.startActivity(intent)
		}
	}
	
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	override fun getLayoutId(): Int {
		return R.layout.activity_pdf_text
	}
	
	
	override fun initView() {
		super.initView()
		
		titleBarView?.setTitle(getString(R.string.extract_text))
		val layoutManager = CenteredLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
		binding.recyclerview.layoutManager = layoutManager
		
		val snapHelper = CenterSnapHelper()
		snapHelper.attachToRecyclerView(binding.recyclerview)
		binding.recyclerview.adapter = mAdapter
		
		
		val pdfStr = BaseSharePreference.instance.getString(Constants.TEXT_LIST, "")
		val stringList = pdfStr?.split(Constants.PDF_TEXT_SPLIT)
		if (stringList != null) {
			for (index in 0 until stringList.size - 1) {
				StringModelList.add(StringModel(stringList[index]))
			}
		}
		mAdapter.setNewInstance(StringModelList)
		
		upNumberUi()
		
		binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
			override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {
				super.onScrolled(recyclerView, dx, dy)
//				val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
//				val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
//				currentPosition = layoutManager.findFirstVisibleItemPosition()
//				upNumberUi()
			}
		})
		
		
		binding.leftIV.setOnClickListener {
			if (currentPosition > 0) {
				currentPosition--
				binding.recyclerview.smoothScrollToPosition(currentPosition)
				upNumberUi()
			}
			
			
		}
		
		
		
		binding.rightIV.setOnClickListener {
			if (currentPosition < mAdapter.data.size - 1) {
				currentPosition++
				binding.recyclerview.smoothScrollToPosition(currentPosition)
				upNumberUi()
			}
			
		}
		
		
		//复制本页文字
		binding.pageTV.setOnClickListener {
			val textToCopy: String = mAdapter.data[currentPosition].editContent
			if (textToCopy.isNotEmpty()) {
				copyToClipboard(textToCopy)
				Toast.makeText(this@PdfTextActivity, getString(R.string.copied_to_clipboard), Toast.LENGTH_SHORT).show()
			} else {
				Toast.makeText(this@PdfTextActivity, getString(R.string.no_text_to_copy), Toast.LENGTH_SHORT).show()
			}
		}
		
		
		binding.allV.setOnClickListener {
			var pdfStr = BaseSharePreference.instance.getString(Constants.TEXT_LIST, "")
			pdfStr = pdfStr!!.replace(Constants.PDF_TEXT_SPLIT, "")
			if (pdfStr.isNotEmpty()) {
				copyToClipboard(pdfStr)
				Toast.makeText(this@PdfTextActivity, getString(R.string.copied_to_clipboard), Toast.LENGTH_SHORT).show()
			} else {
				Toast.makeText(this@PdfTextActivity, getString(R.string.no_text_to_copy), Toast.LENGTH_SHORT).show()
			}
		}
		
	}
	
	private fun copyToClipboard(text: String) {
		val clipboard: ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
		val clip = ClipData.newPlainText("label", text)
		clipboard.setPrimaryClip(clip)
	}
	
	fun upNumberUi() {
		val index = currentPosition + 1
		binding.numberTV.text = index.toString() + "/" + mAdapter.data.size
		binding.textEmsTV.text =
			getString(R.string.all) + " " + mAdapter.data[currentPosition].editContent.length + " " + getString(R.string.character)
		
		Globals.log("xxxxxcurrentPosition"+currentPosition+"     "+ mAdapter.data.size)
		
		if (index == 1) {
			binding.leftIV.setImageResource(R.drawable.icon_img_left_a)
		} else {
			binding.leftIV.setImageResource(R.drawable.icon_img_left_b)
		}
		if (index == mAdapter.data.size) {
			binding.rightIV.setImageResource(R.drawable.icon_img_right_a)
		} else {
			binding.rightIV.setImageResource(R.drawable.icon_img_right_b)
		}
	}
}
	
