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

interface Api {





    @GET("buy/store/count")
    suspend fun getStoreCount(): MBResponse<String>
    
    
    
    @FormUrlEncoded
    @POST(Constants.releaseBaseUrl)
    suspend fun feedBackHttp(@FieldMap hashMap: HashMap<String,String>): MBResponse<String>
}