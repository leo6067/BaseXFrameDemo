package com.xy.demo.ui.image

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.os.Environment
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityDealImageBinding
import com.xy.demo.model.ImgModel
import com.xy.demo.network.Globals
import com.xy.demo.ui.adapter.ImageAdapter
import com.xy.demo.ui.vm.MainViewModel
import java.io.File
import java.io.FileOutputStream


class DealImageActivity : MBBaseActivity<ActivityDealImageBinding, MainViewModel>() {
	
	val mAdapter = ImageAdapter()
	val imageList = arrayListOf<ImgModel>()
	
	
	var doIndex =  0   //操作图片角标
	
	var rotate: Float = 0f   //旋转角度
	
	var doAction = 0  //旋转  剪裁 删除
	
	
	lateinit var originalBitmap: Bitmap  // 当前预览的bitmap
	
	
	
 
	companion object {
		fun newInstance(activity: Activity, imgList: ArrayList<String>) {
			val intent = Intent()
			intent.putStringArrayListExtra("imgPathList", imgList)
			intent.setClass(activity, DealImageActivity::class.java)
			activity.startActivity(intent)
		}
	}
	
	
	override fun showTitleBar(): Boolean {
		return super.showTitleBar()
	}
	
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	override fun getLayoutId(): Int {
		return R.layout.activity_deal_image
	}
	
	
	override fun initView() {
		super.initView()
		titleBarView?.setTitle("编辑图片")
		titleBarView?.setRightText("取消")
		
		mRecyclerView = binding.recyclerview
		initRecycler(1, 2, 0)
		binding.recyclerview.adapter = mAdapter
		
		val imgPathList = intent.getStringArrayListExtra("imgPathList")
		for (index in 0 until imgPathList?.size!!) {
			val imgModel = ImgModel()
			imgModel.imgPath = imgPathList[index]
			imageList.add(imgModel)
		}
		
		mAdapter.setNewInstance(imageList)
		
		upCenterImg()
		
		initListener()
	
		
	}
	
	
	
	fun initListener(){
		
		mAdapter.setOnItemClickListener { adapter, view, position ->
			rotate = 0f
			doIndex = position
			val imgModel = mAdapter.data[position]
			binding.cropImageView.setImageBitmap(imgModel.bitmapImg)
			Glide.with(this).load(imgModel.bitmapImg).into(binding.imageView)
			originalBitmap = imgModel.bitmapImg
			
		}
		
		binding.rotateTV.setOnClickListener {    // 旋转
//			binding.cropImageView.rotateImage(90)
			doAction = 1
			rotate += 90
			binding.imageView.rotation = rotate
			val downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path
			val timeMillis = System.currentTimeMillis()
			var fileName = downloadDir + "/xxy" + "/" + timeMillis + ".png"
			val rotatedBitmap = rotateBitmap(originalBitmap, rotate)
			mAdapter.data[doIndex].setBitmapImg(rotatedBitmap)
			mAdapter.data[doIndex].setImgPath(fileName)
			saveBitmapToDownloadDir(rotatedBitmap, "$timeMillis.png")
			mAdapter.notifyItemChanged(doIndex)
		}
		
		
		binding.cropTV.setOnCheckedChangeListener { compoundButton, b ->
			if (b) {
				doAction = 2
				binding.cropImageView.visibility = View.VISIBLE
				binding.imageView.visibility = View.GONE
				binding.finishTV.visibility = View.GONE
				binding.positiveTV.visibility = View.VISIBLE
				
				binding.rotateTV.isEnabled = false
				binding.deleteTV.isEnabled = false
				
				titleBarView?.getRightTextView()?.visibility = View.VISIBLE
			} else {
				doAction = 0
				binding.cropImageView.visibility = View.GONE
				binding.imageView.visibility = View.VISIBLE
				binding.finishTV.visibility = View.VISIBLE
				binding.positiveTV.visibility = View.GONE
				
				binding.rotateTV.isEnabled = true
				binding.deleteTV.isEnabled = true
				
				titleBarView?.getRightTextView()?.visibility = View.GONE
			}
			
		}
		
		
		
		binding.deleteTV.setOnClickListener {
			doAction = 3
			mAdapter.data.removeAt(doIndex)
			mAdapter.notifyDataSetChanged()
			upCenterImg()
		}
		
		
		binding.positiveTV.setOnClickListener {
			
			val downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path
			val timeMillis = System.currentTimeMillis()
			
			var fileName = downloadDir+"/xxy"+"/"+timeMillis+".png"
			
			Globals.log("XXXXXXXXdownloadDir"+downloadDir)
			Globals.log("XXXXXXXX timeMillis"+timeMillis)
			Globals.log("XXXXXXXX fileName"+fileName)
			
			if (doAction == 1) {    //保存旋转过的照片
				val rotatedBitmap = rotateBitmap(originalBitmap, rotate)
				mAdapter.data[doIndex].setBitmapImg(rotatedBitmap)
				mAdapter.data[doIndex].setImgPath(fileName)
				
				saveBitmapToDownloadDir( rotatedBitmap, "$timeMillis.png")
			} else if (doAction == 2) {  //裁剪
				val cropBitmap = binding.cropImageView.croppedImage
				binding.imageView.setImageBitmap(cropBitmap)
				mAdapter.data[doIndex].setBitmapImg(cropBitmap)
				mAdapter.data[doIndex].setImgPath(fileName)
				saveBitmapToDownloadDir(cropBitmap,"$timeMillis.png")
			}
			
			mAdapter.notifyItemChanged(doIndex)
			
			binding.cropTV.isChecked = false
			binding.finishTV.visibility = View.VISIBLE
			binding.positiveTV.visibility = View.GONE
		}
		
		
		binding.finishTV.setOnClickListener {
			val imageList = arrayListOf<String>()
			for (index in 0 until mAdapter.data.size) {
				imageList.add(mAdapter.data[index].imgPath)
			}
			DragSortActivity.newInstance(this@DealImageActivity,imageList)
		}
	}
	
	
	fun upCenterImg(){
		Glide.with(binding.imageView)
			.asBitmap()
			.load(mAdapter.data[doIndex].imgPath)
			.into(object : CustomTarget<Bitmap>() {
				override fun onResourceReady(resource: Bitmap, transition: com.bumptech.glide.request.transition.Transition<in Bitmap?>?) {
					binding.imageView.setImageBitmap(resource)
					binding.cropImageView.setImageBitmap(resource)
					originalBitmap = resource
				}
				override fun onLoadCleared(placeholder: Drawable?) {
				}
			})
	}
	
	
	// 旋转Bitmap的方法
	fun rotateBitmap(source: Bitmap, angle: Float): Bitmap {
		val matrix = Matrix()
		matrix.postRotate(angle)
		return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
	}
	
	// 保存Bitmap到文件的方法
 
	fun saveBitmapToDownloadDir( bitmap: Bitmap,fileName:String) {
		// 获取Download目录的路径
		val downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
		
		// 创建子目录xxy
		val xxyDir = File(downloadDir, "xxy")
		if (!xxyDir.exists()) {
			if (!xxyDir.mkdirs()) {
				// 处理目录创建失败的情况
				return
			}
		}
		
		// 构建文件路径和名称
//		val fileName = "20240605.jpg" // 假设你想保存为JPEG格式
		val file = File(xxyDir, fileName)
		
		// 将Bitmap保存到文件
		try {
			FileOutputStream(file).use { fos ->
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos) // 第二个参数是质量，范围从0（最差质量/最小文件大小）到100（最佳质量/最大文件大小）
			}
		} catch (e: java.lang.Exception) {
			// 处理异常，例如文件写入失败
			e.printStackTrace()
		}
	}
	
	
	
	
}