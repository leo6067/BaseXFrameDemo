package com.xy.demo.logic

import android.app.Activity
import com.airbnb.lottie.animation.content.Content
import com.xy.xframework.utils.Globals
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException

class JsonUtil {
	
	companion object {
		fun paramJson(mContent: Activity, fileName: String): String {
			val i = 0
			var resultString = ""
			val isr: InputStreamReader // InputStreamReader-->转换流
			try {
				isr = InputStreamReader(mContent.getAssets().open(fileName), "UTF-8") //读取文件中的字节并将其转换为字符
				val br = BufferedReader(isr) // 将传入的isr一行一行解析读取出来出来
				var line: String?
				val stringBuilder = StringBuilder() // StringBuilder:一个可变的字符序列。用在字符串缓冲区被单个线程使用的时候
				while (br.readLine().also { line = it } != null) {
					stringBuilder.append(line)
				}
				isr.close()
				br.close()
				resultString = stringBuilder.toString()
				Globals.log("解析结果："+resultString)
			} catch (e: UnsupportedEncodingException) {
				e.printStackTrace()
			} catch (e: IOException) {
				e.printStackTrace()
			}
			
			return resultString;
		}
		
	}
}