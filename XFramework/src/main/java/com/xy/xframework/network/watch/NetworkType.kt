package com.xy.network.watch

enum class NetworkType(desc: String) {
    AUTO("其他AUTO"),//任意网络
    WIFI("wifi"),//WIFI
    CMNET("手机上网CMNET"), //手机上网
    CMWAP("手机上网CMWAP"), //手机上网
    NONE("无网络NONE")  //无网络
}