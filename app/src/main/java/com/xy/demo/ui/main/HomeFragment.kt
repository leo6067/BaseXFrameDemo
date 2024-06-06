package com.xy.demo.ui.main

import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.LinearLayoutManager
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.xy.demo.R
import com.xy.demo.base.MBBaseFragment
import com.xy.demo.databinding.FragmentHomeBinding
import com.xy.demo.db.MyDataBase
import com.xy.demo.db.PdfFileModel
import com.xy.demo.network.Globals
import com.xy.demo.ui.EditListActivity
import com.xy.demo.ui.PDFActivity
import com.xy.demo.ui.PdfListActivity
import com.xy.demo.ui.adapter.PdfFileAdapter
import com.xy.demo.ui.image.DealImageActivity
import com.xy.demo.ui.vm.FileViewModel
import com.xy.xframework.imagePicker.WeChatPresenter
import com.ypx.imagepicker.ImagePicker
import com.ypx.imagepicker.bean.ImageItem
import com.ypx.imagepicker.bean.MimeType
import com.ypx.imagepicker.data.OnImagePickCompleteListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


open class HomeFragment : MBBaseFragment<FragmentHomeBinding, FileViewModel>() {
	
	private val filePaths = ArrayList<Uri>()
	
	
	val mAdapter = PdfFileAdapter()
	
	
	override fun getLayoutId(): Int {
		return R.layout.fragment_home
	}
	
	
	override fun initView() {
		binding.recyclerview.layoutManager = LinearLayoutManager(activity)
		binding.recyclerview.adapter = mAdapter
		mAdapter.setEmptyView(R.layout.include_home_empty)
		
		initListener()
	}
	
	override fun onResume() {
		super.onResume()
		
		GlobalScope.launch {
			val pdfList = MyDataBase.instance.PdfFileDao().getAllPdfList()
			withContext(Dispatchers.Main) {
				mAdapter.setNewInstance(pdfList)
			}
		}
	}
	
	fun initListener() {
		mAdapter.setOnItemClickListener { adapter, view, position ->
			val pdfFileModel = mAdapter.data[position] as PdfFileModel
			activity?.let { PDFActivity.setNewInstance(it, pdfFileModel.path, pdfFileModel.name, 2) }

//			val file = File(pdfFileModel.path)
//			val delete = file.delete()
		
		
		}
		
		binding.imageTV.setOnClickListener {
			imageToPdf()
		}
		
		binding.compressTV.setOnClickListener {
			val intent = Intent(activity, PdfListActivity::class.java)
			startActivity(intent)
		}
		
		binding.addTV.setOnClickListener {
			val intent = Intent(activity, PdfListActivity::class.java)
			startActivity(intent)
		}
		
		binding.lockTV.setOnClickListener {
			val intent = Intent(activity, PdfListActivity::class.java)
			startActivity(intent)
		}
		
		
		binding.editIV.setOnClickListener {
			activity?.let { it1 -> EditListActivity.setNewInstance(it1, 1) }
		}
		
	}
	
	
	fun imageToPdf() {
		
		
//		activity?.let {
//			CropImage.activity()
//				.setGuidelines(CropImageView.Guidelines.ON)
//				.start(it)
//		};
		
		
		
		
		val imgList = ArrayList<String>()
		
		
		XXPermissions.with(this)

			.permission(Permission.READ_MEDIA_IMAGES)
			.request { permissions, all ->
				ImagePicker.withMulti(WeChatPresenter()) //设置presenter
					.setMaxCount(50) //设置选择数量
					.setColumnCount(4) //设置列数
					.mimeTypes(MimeType.JPEG, MimeType.PNG, MimeType.WEBP)
					//设置需要过滤掉加载的文件类型
					.setPreview(true)
					.pick(activity, OnImagePickCompleteListener {
//						val imageItem = it[0] as ImageItem
						
						for (index in 0 until it.size){
							imgList.add(it[index].path)
						}
						
						
					 
						activity?.let { it1 -> DealImageActivity.newInstance(it1,imgList) }
					})
			}
	}


//
//	override fun initView() {
//
//		XXPermissions.with(this)
//			.permission(Permission.READ_MEDIA_AUDIO, Permission.READ_MEDIA_IMAGES, Permission.READ_MEDIA_VIDEO, Permission.MANAGE_EXTERNAL_STORAGE )
//			.request { permissions, all ->
//
//			}
////
//		var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//
//			ToastUtils.showShort("5313212312")
//		}
//
//	var resultLauncherB = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//
//			ToastUtils.showShort("5313212312")
//		}
//
////		var intent =   Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
////			intent.setData(Uri.parse("package:" + activity?.getPackageName()));
////			startActivity(intent );
////
//		documentData()
////
//		binding.nextTV.setOnClickListener {
////			"image/jpeg"  "video/mp4"
//
////			ImagePicker.withMulti(RedBookPresenter()) //设置presenter
////				.setMaxCount(1) //设置选择数量
////				.setColumnCount(4) //设置列数
////				.mimeTypes(MimeType.JPEG,MimeType.PNG,MimeType.WEBP)
////				//设置需要过滤掉加载的文件类型
////				.setPreview(true)
////				.setVideoSinglePick(true) //设置视频单选
////				.setMaxVideoDuration(200000000L) //设置可选区的最大视频时长
////				.pick(activity, OnImagePickCompleteListener {
////
////				})
//
//
//
//			var intent =   Intent(Intent.ACTION_GET_CONTENT);
//			intent.type = "application/pdf"
//
//				startActivity(intent);
//
//
//
//		}
//
//
//
//
//		binding.wayA.setOnClickListener {
//			val intent4 = Intent(activity, NormalFilePickActivity::class.java)
//			intent4.putExtra(Constant.MAX_NUMBER, 9)
//			intent4.putExtra(NormalFilePickActivity.SUFFIX, arrayOf("xlsx", "xls", "doc", "docx", "ppt", "pptx", "pdf"))
//			resultLauncherB.launch(intent4)
//		}
//
//
//
//		binding.wayB.setOnClickListener {
//			// 调用系统文件管理器
//
//			FilePickerBuilder.instance
//				.setMaxCount(5) //optional
//				.setSelectedFiles(filePaths) //optional
//				.setActivityTheme(R.style.LibAppTheme) //optional
//				.pickFile(this, 100);
//		}
//
//		binding.imageToPdf.setOnClickListener {
//			// 调用系统文件管理器
//			chooseImage()
//
//		}
//	}
//
//
//	fun chooseImage() {
//		Globals.log("xxxxxxxxx" )
//		XXPermissions.with(this)
//			.permission(Permission.READ_MEDIA_IMAGES)
//			.request { permissions, all ->
//				ImagePicker.withMulti(WeChatPresenter())
//					.mimeTypes(MimeType.ofImage())
//					.filterMimeTypes(MimeType.GIF) //剪裁完成的图片是否保存在DCIM目录下
//					//true：存储在DCIM下 false：存储在 data/包名/files/imagePicker/ 目录下
//					.cropSaveInDCIM(false) //设置剪裁比例
//					.setCropRatio(1, 1) //设置剪裁框间距，单位px
//					.cropRectMinMargin(50) //是否圆形剪裁，圆形剪裁时，setCropRatio无效
//					.cropAsCircle() //设置剪裁模式，留白或充满  CropConfig.STYLE_GAP 或 CropConfig.STYLE_FILL
//					.cropStyle(CropConfig.STYLE_FILL) //设置留白模式下生成的图片背景色，支持透明背景
//					.cropGapBackgroundColor(Color.TRANSPARENT)
//					.crop(activity, OnImagePickCompleteListener {
//						//图片剪裁回调，主线程
//					})
//
//			}
//
//	}
//
//
//
//
//	/**
//	 * 获取手机文档数据
//	 *
//	 * @param
//	 */
//	fun documentData(){
//			val columns = arrayOf(
//				MediaStore.Files.FileColumns._ID,
//				MediaStore.Files.FileColumns.MIME_TYPE,
//				MediaStore.Files.FileColumns.SIZE,
//				MediaStore.Files.FileColumns.DATE_MODIFIED,
//				MediaStore.Files.FileColumns.DATA
//			)
//			val select = "(_data LIKE '%.png')"
//			val contentResolver: ContentResolver? = activity?.getContentResolver()
//			val cursor = contentResolver?.query(MediaStore.Files.getContentUri("external"), columns, select, null, null)
//			var columnIndexOrThrow_DATA = 0
//			if (cursor != null) {
//				columnIndexOrThrow_DATA = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
//			}
//			if (cursor != null) {
//				while (cursor.moveToNext()) {
//					val path = cursor.getString(columnIndexOrThrow_DATA)
//					Globals.log("xxxxx"+" pdf $path")
//				}
//			}
//			cursor!!.close()
//		}
//
	

}