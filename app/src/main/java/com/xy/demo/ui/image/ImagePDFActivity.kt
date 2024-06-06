package com.xy.demo.ui.image

import android.media.ImageReader
import com.alibaba.android.arouter.launcher.ARouter.logger
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.PDPage
import com.tom_roush.pdfbox.pdmodel.PDPageContentStream
import com.tom_roush.pdfbox.pdmodel.PDPageContentStream.AppendMode
import com.tom_roush.pdfbox.pdmodel.common.PDRectangle
import com.tom_roush.pdfbox.pdmodel.graphics.image.JPEGFactory
import com.tom_roush.pdfbox.pdmodel.graphics.image.LosslessFactory
import com.tom_roush.pdfbox.pdmodel.graphics.image.PDImageXObject
import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityImagePdfBinding
import com.xy.demo.ui.vm.MainViewModel
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.Locale



// 图片转 PDF 参数设置
class ImagePDFActivity : MBBaseActivity<ActivityImagePdfBinding,MainViewModel>() {
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	override fun getLayoutId(): Int {
		return R.layout.activity_image_pdf
	}
	
	
	
	
	
 
	

}