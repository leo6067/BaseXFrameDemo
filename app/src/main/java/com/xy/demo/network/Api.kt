package com.xy.demo.network;

import com.alibaba.fastjson.JSONObject
import com.google.api.client.json.JsonString
import com.xy.demo.model.CheckSettingModel
import com.xy.demo.model.IpModel
import com.xy.demo.model.VideoStoreModel

import com.xy.demo.network.MBResponse
import retrofit2.http.*
import java.util.Objects

interface Api {




    @GET("https://ifconfig.co/json")
    suspend fun getOutIp(): MBResponse<IpModel>



    @POST("service/check-setting")
    suspend fun getCheckSetting(@Body jsonString: Map<String,String>): MBResponse<CheckSettingModel>


    @POST("video/store")
    suspend fun getVideoStore(@Body jsonString: Map<String,String>): MBResponse<VideoStoreModel>
}