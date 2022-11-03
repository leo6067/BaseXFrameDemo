package com.xy.network

class ApiException : Exception() {
    var code: Int = 0
    var msg: String = ""
    var throwable: Throwable? = null
}