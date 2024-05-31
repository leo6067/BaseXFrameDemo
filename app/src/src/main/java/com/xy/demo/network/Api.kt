package com.xy.demo.network;

import com.alibaba.fastjson.JSONObject
import com.xy.demo.base.Constants

import com.xy.demo.network.MBResponse
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface Api {

 

    @GET("https://suggest.taobao.com/sug?code=utf-8&q=羽绒服")
    suspend fun getGood(): MBResponse<String>
    
    @GET("buy/store/count")
    suspend fun getStoreCount(): MBResponse<String>
    
    @GET("buy/store/count")
    suspend fun loginHttp(): MBResponse<String>
    
    
    @GET(Constants.releaseBaseUrl)
    suspend fun mainHttp(@QueryMap hashMap: HashMap<String,String>): MBResponse<String>
    
    @FormUrlEncoded
    @POST(Constants.releaseBaseUrl)
    suspend fun feedBackHttp(@FieldMap hashMap: HashMap<String,String>): MBResponse<String>
}