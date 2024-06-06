package com.xy.demo.network;

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface Api {





    @GET("buy/store/count")
    suspend fun getStoreCount(): MBResponse<String>
    
    
    
    
    
    /**
     * // MultipartBody.Part 是包含文件名和RequestBody的包装器
     * // 这里我们使用文件的名字作为part名
     * MultipartBody.Part body = MultipartBody.Part.createFormData("file", documentFile.getName(), requestFile);
     * */
    @Multipart
    @POST("upload/file")
    suspend fun uploadFile(@Part file: Part): MBResponse<String>
    
    
 
}