package com.xy.network.watch

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiInfo
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer


class NetworkWatch(var context: Context) : ConnectivityManager.NetworkCallback() {

    init {
        val builder = NetworkRequest.Builder()
        val request = builder.build()
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
        if (connectivityManager is ConnectivityManager) {
            connectivityManager.registerNetworkCallback(request, this)
        }
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        post(NetworkType.CONNECT);
        Log.d("NetworkWatch", "onAvailable: 网络已连接");
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        post(NetworkType.NONE);
        Log.d("NetworkWatch", "onLost: 网络已断开");
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.d("NetworkWatch", "onCapabilitiesChanged: 网络类型为wifi");
                    post(NetworkType.WIFI)
                }
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.d("NetworkWatch", "onCapabilitiesChanged: 蜂窝网络");
                    post(NetworkType.CMWAP);
                }
                else -> {
                    Log.d("NetworkWatch", "onCapabilitiesChanged: 其他网络");
                    post(NetworkType.AUTO);
                }
            }
        }
    }

    private fun post(wifi: NetworkType) {
        NetworkStateLiveData.setNetState(wifi)
    }
}