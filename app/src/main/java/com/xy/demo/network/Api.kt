package com.xy.demo.network;

import com.alibaba.fastjson.JSONObject

import com.xy.demo.network.MBResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {


    /**
     * 上报设备信息
     */
    @POST("ad/a")
    suspend fun startA(@Body map: HashMap<String, String?>): MBResponse<String>



    //订单-购买商品（一连抽或五连抽）
    @POST("buy/draw/buy")
    suspend fun purchaseGoods(@Body jsonBody: JSONObject): MBResponse<String>

    //订单详情页面提交订单
    @POST("buy/order/submit")
    suspend fun submitOrderList(@Body jsonBody: JSONObject): MBResponse<Int>

    //取消订单
    @POST("buy/order/cancel")
    suspend fun cancelPay(@Body jsonBody: JSONObject): MBResponse<Any>



    @GET("buy/store/count")
    suspend fun getStoreCount(): MBResponse<Int>
}