package com.xy.demo.ui.dialog

import android.app.Activity
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import com.xy.demo.R
import com.xy.demo.db.MyDataBase
import com.xy.demo.db.PdfFileModel
import com.xy.demo.network.Globals
import com.xy.demo.ui.EditListActivity
import com.xy.xframework.dialog.BaseDialog
import com.xy.xframework.utils.ToastUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class DialogManage {
	
	
	companion object {
		
		// 重命名
		fun showRenameDialog(activity: EditListActivity, fileModel: PdfFileModel) {
			val inflate = LayoutInflater.from(activity).inflate(R.layout.dialog_rename, null)
			BaseDialog.showBaseDialog(activity, inflate)
			val nameTV = inflate.findViewById<EditText>(R.id.nameTV)
			val positiveTV = inflate.findViewById<TextView>(R.id.positiveTV)
			positiveTV.setOnClickListener {
				if (!TextUtils.isEmpty(nameTV.text.toString() ) && nameTV.text.toString().contains(".pdf")) {
					upRoom(fileModel, nameTV.text.toString())
					activity.initData()
				}else{
					ToastUtils.showShort("请输入正确的名字包含文件格式")
				}
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
					
					if (renameTo){
						MyDataBase.instance.PdfFileDao().updateFile(newName, fileName,directory+"/"+newName)
						BaseDialog.dismissBaseDialog()
					}else{
						ToastUtils.showShort("操作失败")
					}
				} else {
					ToastUtils.showShort("操作失败")
				}
			}
		}
		
		
		
		
		// 压缩PDF
		fun showCompressDialog(activity: EditListActivity, fileModel: PdfFileModel) {
			val inflate = LayoutInflater.from(activity).inflate(R.layout.dialog_compress, null)
			BaseDialog.showBaseDialog(activity, inflate)
			val nameTV = inflate.findViewById<RadioGroup>(R.id.compressRG)
			val positiveTV = inflate.findViewById<TextView>(R.id.positiveTV)
			positiveTV.setOnClickListener {
			
			}
		}
		
		
		
		
		// 加密
		fun showEncryptDialog(activity: Activity, fileModel: PdfFileModel) {
			val inflate = LayoutInflater.from(activity).inflate(R.layout.dialog_encrypt, null)
			BaseDialog.showBaseDialog(activity, inflate)
			val nameTV = inflate.findViewById<EditText>(R.id.nameTV)
			val checkbox = inflate.findViewById<CheckBox>(R.id.checkbox)
			val positiveTV = inflate.findViewById<TextView>(R.id.positiveTV)
			positiveTV.setOnClickListener {
			
			}
		}
		
		
		
		
		
		// 解密
		fun showDecodeDialog(activity: Activity, fileModel: PdfFileModel) {
			val inflate = LayoutInflater.from(activity).inflate(R.layout.dialog_decode, null)
			BaseDialog.showBaseDialog(activity, inflate)
			val nameTV = inflate.findViewById<EditText>(R.id.nameTV)
			val checkbox = inflate.findViewById<CheckBox>(R.id.checkbox)
			val positiveTV = inflate.findViewById<TextView>(R.id.positiveTV)
			positiveTV.setOnClickListener {
			
			}
		}
		
		
		
		
	}
	
	
}