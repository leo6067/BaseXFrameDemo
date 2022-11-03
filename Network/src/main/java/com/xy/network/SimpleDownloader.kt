package com.xy.network

import okhttp3.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object SimpleDownloader {

    fun download(url: String, savePaht: File?, fileName: String) {
        if (null == savePaht) return
        val httpClient = OkHttpClient.Builder().build()
        val request = Request.Builder()
            .url(url)
            .get()
            .build()
        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val inputStream = response.body?.byteStream()
                val fos = FileOutputStream(File(savePaht, fileName))
                try {
                    val buf = ByteArray(2048)
                    var len = 0
                    inputStream?.let { ism ->
                        while ((ism.read(buf).also { len = it }) != -1) {
                            fos.write(buf, 0, len)
                        }
                        fos.flush()
                    }
                } catch (e: Exception) {

                } finally {
                    try {
                        inputStream?.close()
                        fos.close()
                    } catch (e: IOException) {
//                        e.printStackTrace()
                    }
                }
            }
        })


    }
}