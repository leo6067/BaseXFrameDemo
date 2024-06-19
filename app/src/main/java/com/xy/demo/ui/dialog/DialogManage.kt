package com.xy.demo.ui.dialog

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.text.InputType
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Base64
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import com.jeremyliao.liveeventbus.LiveEventBus

import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.encryption.AccessPermission
import com.tom_roush.pdfbox.pdmodel.encryption.StandardProtectionPolicy
import com.tom_roush.pdfbox.pdmodel.graphics.image.PDImageXObject
import com.tom_roush.pdfbox.text.PDFTextStripper
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MyApplication
import com.xy.demo.db.MyDataBase
import com.xy.demo.db.PdfFileModel
import com.xy.demo.ui.common.EditListActivity
import com.xy.demo.ui.common.PDFActivity
import com.xy.demo.ui.image.BitMapListActivity
import com.xy.demo.ui.image.PdfTextActivity
import com.xy.demo.ui.success.LockActivity
import com.xy.demo.ui.success.UnlockActivity
import com.xy.demo.utils.CompressUtil
import com.xy.xframework.base.BaseSharePreference
import com.xy.xframework.dialog.BaseDialog
import com.xy.xframework.utils.FileUtils
import com.xy.xframework.utils.Globals
import com.xy.xframework.utils.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException


class DialogManage {
	
	
	companion object {
		
		
		fun showLoginDialog(activity: Activity) {
			val inflate = LayoutInflater.from(activity).inflate(R.layout.dialog_login, null)
			BaseDialog.setSeat(Gravity.CENTER)
			BaseDialog.showBaseDialog(activity, inflate)
			val closeIV = inflate.findViewById<ImageView>(R.id.closeIV)
			
			
			closeIV.setOnClickListener {
				BaseDialog.dismissBaseDialog()
			}
		}
		
		
		// 图片保存成功，跳转相册
		fun showBitmapDialog(activity: Activity) {
			val inflate = LayoutInflater.from(activity).inflate(R.layout.dialog_bitmap, null)
			BaseDialog.setSeat(Gravity.BOTTOM)
			BaseDialog.setMargins(0)
			BaseDialog.showBaseDialog(activity, inflate)
			val closeIV = inflate.findViewById<ImageView>(R.id.closeIV)
			val okTV = inflate.findViewById<TextView>(R.id.okTV)
			val openTV = inflate.findViewById<TextView>(R.id.openTV)
			okTV.setOnClickListener {
				BaseDialog.dismissBaseDialog()
				activity.finish()
			}
			
			
			openTV.setOnClickListener {
				BaseDialog.dismissBaseDialog()
				val intent = Intent(Intent.ACTION_VIEW)
				intent.type = "image/*"
				activity.startActivity(intent)
				activity.finish()
			}
			
			closeIV.setOnClickListener {
				BaseDialog.dismissBaseDialog()
				activity.finish()
			}
		}
		
		
		// 重命名 窗口
		fun showRenameDialog(activity: EditListActivity, fileModel: PdfFileModel) {
			val inflate = LayoutInflater.from(activity).inflate(R.layout.dialog_rename, null)
			BaseDialog.setSeat(Gravity.CENTER)
			BaseDialog.showBaseDialog(activity, inflate)
			val closeIV = inflate.findViewById<ImageView>(R.id.closeIV)
			val nameTV = inflate.findViewById<EditText>(R.id.nameTV)
			val positiveTV = inflate.findViewById<TextView>(R.id.positiveTV)
			positiveTV.setOnClickListener {
				if (!TextUtils.isEmpty(nameTV.text.toString())) {
					upRoom(fileModel, nameTV.text.toString() + ".pdf")
					activity.initData()
				} else {
					ToastUtils.showShort(activity.getString(R.string.please_input_file_name))
				}
			}
			
			closeIV.setOnClickListener {
				BaseDialog.dismissBaseDialog()
			}
		}
		
		
		fun upRoom(fileModel: PdfFileModel, newName: String) {
			GlobalScope.launch {
				val lastIndex = fileModel.path.lastIndexOf('/')
				if (lastIndex != -1) {
					val directory = fileModel.path.substring(0, lastIndex)
					val fileName = fileModel.path.substring(lastIndex + 1)
					val oldFile = File(directory, fileName)
					val newFile = File(directory, newName)
					// 使用renameTo()方法重命名文件
					val renameTo = oldFile.renameTo(newFile)
					
					if (renameTo) {
						MyDataBase.instance.PdfFileDao().updateFile(newName, fileName, directory + "/" + newName)
						BaseDialog.dismissBaseDialog()
						delay(2000)
						Globals.log("xxxxxxxxxxxxEVENT_REFRESH_FILE_EDIT0066666660")
						LiveEventBus.get<String>(Constants.EVENT_REFRESH_FILE_EDIT).post(Constants.EVENT_REFRESH_FILE_EDIT)
					} else {
						withContext(Dispatchers.Main) {
							ToastUtils.showShort("fail")
						}
					}
				} else {
					withContext(Dispatchers.Main) {
						ToastUtils.showShort("fail")
					}
					
				}
				
				
			}
		}
		
		
		// 压缩PDF 窗口
		fun showCompressDialog(activity: Activity, filePath: String) {
			var compressLevel: Int = 40
			val inflate = LayoutInflater.from(activity).inflate(R.layout.dialog_compress, null)
			BaseDialog.setSeat(Gravity.BOTTOM)
			BaseDialog.setMargins(0)
			BaseDialog.showBaseDialog(activity, inflate)
			val compressRG = inflate.findViewById<RadioGroup>(R.id.compressRG)
			val positiveTV = inflate.findViewById<TextView>(R.id.positiveTV)
			val closeIV = inflate.findViewById<ImageView>(R.id.closeIV)
			
			closeIV.setOnClickListener {
				BaseDialog.dismissBaseDialog()
			}
			
			compressRG.setOnCheckedChangeListener { radioGroup, i ->
				when (i) {
					R.id.compressA -> compressLevel = 40
					R.id.compressB -> compressLevel = 60
					R.id.compressC -> compressLevel = 80
				}
			}
			
			positiveTV.setOnClickListener {
				Globals.log("xxxxxxx fileModel.path---" + FileUtils.getFileOrFilesSize(filePath, 3))
				GlobalScope.launch(Dispatchers.IO) {
					CompressUtil.compress(activity, filePath, compressLevel)
				}
//				compressPDF(fileModel.path, fileModel.path, compressLevel)
				BaseDialog.dismissBaseDialog()
			}
		}
		
		
		// 加密 窗口
		fun showEncryptDialog(activity: Activity, fileModel: PdfFileModel) {
			val inflate = LayoutInflater.from(activity).inflate(R.layout.dialog_encrypt, null)
			BaseDialog.setMargins(150)
			BaseDialog.setSeat(Gravity.CENTER)
			BaseDialog.showBaseDialog(activity, inflate)
			val passwordEditText = inflate.findViewById<EditText>(R.id.pwdET)
			val checkbox = inflate.findViewById<CheckBox>(R.id.checkbox)
			val positiveTV = inflate.findViewById<TextView>(R.id.positiveTV)
			val closeIV = inflate.findViewById<ImageView>(R.id.closeIV)
			
			checkbox.setOnCheckedChangeListener { compoundButton, isPasswordVisible ->
				if (isPasswordVisible) {
					// 切换到密码模式
					passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
					passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance())
				} else {
					// 切换到明文模式
					passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
					passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
				}
			}
			
			positiveTV.setOnClickListener {
				if (passwordEditText.text.toString().isNotEmpty()) {
					encryptPDF(activity, fileModel.path, passwordEditText.text.toString(), false)
					BaseDialog.dismissBaseDialog()
				}
			}
			
			closeIV.setOnClickListener {
				BaseDialog.dismissBaseDialog()
			}
			
		}
		
		
		//对文件 加密 加锁
		fun encryptPDF(activity: Activity, filePath: String, password: String, needCover: Boolean) {
			GlobalScope.launch {
				val inputFile = File(filePath)
				// 指定PDF文件的路径
				// 加载现有的PDF文档
				val document = PDDocument.load(inputFile)
				// 创建访问权限对象（默认允许所有权限）
				val accessPermission = AccessPermission()
				// 设置保护策略，仅设置用户密码
				val protectionPolicy = StandardProtectionPolicy(null, password, accessPermission)
				// 设置加密强度
				protectionPolicy.encryptionKeyLength = 128
				// 应用保护策略到文档
				document.protect(protectionPolicy)
				// 保存加密后的PDF文档
				if (needCover) {
					document.save(filePath)
					document.close()
					println("PDF文件已加密并保存到：$filePath")
				} else {
					val fileName = inputFile.name
					val split = fileName.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
					val outFileName = Constants.publicXXYDir + split[0] + Constants.ENCRYPT_FORMAT
					document.save(outFileName)
					document.close()
					println("PDF文件已加密并保存到：$outFileName")
					LockActivity.newInstance(activity, outFileName)
				}
			}
		}
		
		
		// 解密 窗口
		fun showDecodeDialog(activity: Activity, path: String, name: String, doType: Int) {   // doType : 1 解密  2 解锁
			val inflate = LayoutInflater.from(activity).inflate(R.layout.dialog_decode, null)
			BaseDialog.setSeat(Gravity.CENTER)
			BaseDialog.setMargins(150)
			BaseDialog.showBaseDialog(activity, inflate)
			val passwordEditText = inflate.findViewById<EditText>(R.id.passwordEditText)
			val checkbox = inflate.findViewById<CheckBox>(R.id.checkbox)
			val positiveTV = inflate.findViewById<TextView>(R.id.positiveTV)
			val closeIV = inflate.findViewById<ImageView>(R.id.closeIV)
			
			checkbox.setOnCheckedChangeListener { compoundButton, isPasswordVisible ->
				if (isPasswordVisible) {
					// 切换到密码模式
					passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
					passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance())
				} else {
					// 切换到明文模式
					passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
					passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
				}
			}
			
			
			positiveTV.setOnClickListener {
				if (passwordEditText.text.toString().isNotEmpty()) {
					if (doType == 1) {
						decodePDF(activity, path, name, passwordEditText.text.toString())
					} else {
						unlockPDF(activity, path, passwordEditText.text.toString())
					}
					BaseDialog.dismissBaseDialog()
				}
			}
			
			closeIV.setOnClickListener {
				BaseDialog.dismissBaseDialog()
			}
			
		}
		
		
		//对文件 解密
		fun decodePDF(activity: Activity, path: String, name: String, password: String) {
			GlobalScope.launch(Dispatchers.IO) {
				try {
					// 加载加密的PDF文档
					val document = PDDocument.load(File(path), password)
					// 检查文档是否已加密
					if (document.isEncrypted) {
						println("PDF文件已成功解密")
						PDFActivity.setNewInstance(activity, path, name, 2, password)
					}
				} catch (e: Exception) {
					withContext(Dispatchers.Main) {
						ToastUtils.showShort(activity.getString(R.string.wrong_password))
					}
				}
			}
			
		}
		
		
		// 对文件 去锁
		fun unlockPDF(activity: Activity, path: String, password: String) {
			GlobalScope.launch {
				try {
					// 加载加密的PDF文档
					val document = PDDocument.load(File(path), password)
					document.setAllSecurityToBeRemoved(true)
					// 保存解密后的PDF文件----同路径 解密文档覆盖
					document.save(File(path))
					document.close()
					UnlockActivity.newInstance(activity, path)
				} catch (e: Exception) {
					withContext(Dispatchers.Main) {
						ToastUtils.showShort(activity.getString(R.string.wrong_password))
					}
				}
			}
		}
		
		
		//pdf 提取窗口
		fun showExtractDialog(activity: Activity, path: String, doType: Int) {   // doType : 1 图片提取  2 文字提取
			val inflate = LayoutInflater.from(activity).inflate(R.layout.dialog_extract, null)
			val textView = inflate.findViewById<TextView>(R.id.rateTV)
			BaseDialog.setSeat(Gravity.CENTER)
			BaseDialog.showBaseDialog(activity, inflate)
			GlobalScope.launch(Dispatchers.IO) {
				delay(600)
				if (doType == 1) {
					extractImagesFromPdf(activity, path, textView)
				} else {
					extractTextFromPDF(activity, path, textView)
				}
				BaseDialog.dismissBaseDialog()
			}
		}
		
		
		// 提取图片
		fun extractImagesFromPdf(activity: Activity, pdfPath: String, textView: TextView) {
			var bitmapList = ArrayList<Bitmap>()
			var document: PDDocument? = null
			try {
				val file = File(pdfPath)
				document = PDDocument.load(file)
				val pages = document.pages
				var index = 0
				for (page in pages) {
					index++
					GlobalScope.launch(Dispatchers.Main) {
						delay(200)
						textView.text = activity.getString(R.string.processing) + index + "/" + pages.count
					}
					
					val xObjectNames = page.resources.xObjectNames
					if (xObjectNames != null) {
						for (xObjectName in xObjectNames) {
							val xObject = page.resources.getXObject(xObjectName)
							if (xObject is PDImageXObject) {
								val image = xObject
								bitmapList.add(image.image)
								// 在这里处理 bitmap，例如保存到文件或显示在 UI 上
							}
						}
					}
				}
				
				val jsonArray = JSONArray()
				for (bitmap in bitmapList) {
					val byteArrayOutputStream = ByteArrayOutputStream()
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
					val byteArray = byteArrayOutputStream.toByteArray()
					val encodedBitmap: String = Base64.encodeToString(byteArray, Base64.DEFAULT)
					jsonArray.put(encodedBitmap)
				}
				BaseSharePreference.instance.putString(Constants.BITMAP_LIST, jsonArray.toString())
				BitMapListActivity.newInstance(activity)
			} catch (e: IOException) {
				BaseDialog.dismissBaseDialog()
				Globals.log("XXXXXXXTAG", "Error loading PDF document: ${e.message}")
			} finally {
				try {
					document?.close()
				} catch (e: IOException) {
					Globals.log("XXXXXXXTAG", "Error closing PDF document: ${e.message}")
				}
			}
		}
		
		
		// 提取文字
		private fun extractTextFromPDF(activity: Activity, pdfPath: String, textView: TextView) {
			val pdfFile = File(pdfPath)
			var pdfStr: String = ""
			var PDF_SPLIT: String = ""
			if (pdfFile.exists()) {
				try {
					val document = PDDocument.load(FileInputStream(pdfFile))
					val pdfStripper = PDFTextStripper()
//					val text = pdfStripper.getText(document)
					
					// 获取总页数
					val totalPages = document.numberOfPages
					//sort设置为true 则按照行进行读取，默认是false
					pdfStripper.setSortByPosition(true);
					
					// 逐页提取文本
					for (page in 1..totalPages) {
						pdfStripper.startPage = page
						pdfStripper.endPage = page
						val pageText = pdfStripper.getText(document)
						// 在这里处理每页提取的文本，例如打印或保存到列表
						println("Page $page: $pageText")
						
						if (page > 1) {
							PDF_SPLIT = Constants.PDF_TEXT_SPLIT
						}
						
						if (TextUtils.isEmpty(pageText)) {
							pdfStr = pdfStr + MyApplication.instance.getString(R.string.no_text_recognized_on_this_page) + PDF_SPLIT
						} else {
							pdfStr = pdfStr + pageText + PDF_SPLIT
						}
						
						
						GlobalScope.launch(Dispatchers.Main) {
							delay(200)
							textView.text = activity.getString(R.string.processing) + "  " + page + "/" + totalPages
						}
					}
					
					document.close()
					BaseSharePreference.instance.putString(Constants.TEXT_LIST, pdfStr)
					PdfTextActivity.setNewInstance(activity)
					// 在这里处理提取的文本，例如显示在 TextView 中
				} catch (e: IOException) {
					e.printStackTrace()
				}
			}
		}
		
		
	}
	
	
}