package com.xy.demo.network

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.text.format.Formatter
import android.util.Log
import com.xy.demo.network.IpAddress
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.*
import java.util.*

/**
 * author: Leo
 * createDate: 2023/11/20 15:55
 */
object IpAddress {
    /**
     * 获取用户IP地址<br></br>
     * 注意：需要在androidManifest.xml中声明下面三个权限才能正常使用该方法，否则会空指针异常
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
     * <uses-permission android:name="android.permission.INTERNET"></uses-permission>
     */
    @SuppressLint("MissingPermission")
    fun getIpAddress(context: Context?): String {
        if (context == null) {
            return ""
        }
        val conManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        try {
            val info = conManager.activeNetworkInfo
            if (info != null && info.isConnected) {
                // 3/4g网络
                if (info.type == ConnectivityManager.TYPE_MOBILE) {
                    return hostIp
                } else if (info.type == ConnectivityManager.TYPE_WIFI) {
//                    return getLocalIPAddress(context); // 局域网地址
                    return outNetIP // 外网地址
                } else if (info.type == ConnectivityManager.TYPE_ETHERNET) {
                    // 以太网有限网络
                    return hostIp
                }
            }
        } catch (e: Exception) {
            return ""
        }
        return ""
    }
    /**
     * 获取外网ip地址（非本地局域网地址）的方法
     */// 将流转化为字符串//json格式信息的API，使用这个自己搞代码
    //            String address = "http://pv.sohu.com/cityjson?ie=utf-8"; //json格式信息的API，下面有一个使用案例。
    //设置浏览器ua 保证不出现503
//            String address = "http://www.3322.org/dyndns/getip"; //单独只有IP外网地址的API
    /**
     * 获取外网ip地址的方法1--网页信息格式
     * @return
     */
    val outNetIP: String
        get() {
            var ipAddress = ""
            val address = "https://ifconfig.co/json" //json格式信息的API，使用这个自己搞代码

            NetLaunchManager.launchRequest({ NetManager.getOutIp() }, {
                Globals.log("oooooo" + it?.ip)
            }, {})

            return ipAddress
        }

    // wifi下获取本地网络IP地址（局域网地址）
    //    public static String getLocalIPAddress(Context context) {
    //        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    //        if (wifiManager != null) {
    //            @SuppressLint("MissingPermission") WifiInfo wifiInfo = wifiManager.getConnectionInfo();
    //            String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());
    //            return ipAddress;
    //        }
    //        return "";
    //    }
    // 获取有限网IP
    val hostIp: String
        get() {
            try {
                val en = NetworkInterface
                    .getNetworkInterfaces()
                while (en.hasMoreElements()) {
                    val intf = en.nextElement()
                    val enumIpAddr = intf
                        .inetAddresses
                    while (enumIpAddr.hasMoreElements()) {
                        val inetAddress = enumIpAddr.nextElement()
                        if (!inetAddress.isLoopbackAddress
                            && inetAddress is Inet4Address
                        ) {
                            return inetAddress.getHostAddress()
                        }
                    }
                }
            } catch (ex: Exception) {
            }
            return "0.0.0.0"
        }

    fun getAllNetInterface(): Array<String?>? {
        val availableInterface = ArrayList<String>()
        var interfaces: Array<String?>? = null
        try {
            val nis: Enumeration<*> = NetworkInterface.getNetworkInterfaces()
            var ia: InetAddress? = null
            while (nis.hasMoreElements()) {
                val ni = nis.nextElement() as NetworkInterface
                val ias = ni.inetAddresses
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement()
                    if (ia is Inet6Address) {
                        continue  // skip ipv6
                    }
                    val ip = ia.hostAddress
                    Log.d(
                        "xxxxxxxx",
                        "getAllNetInterface,available interface:" + ni.name + ",address:" + ip
                    )
                    // 过滤掉127段的ip地址
                    if ("127.0.0.1" != ip) {
                        availableInterface.add(ni.name)
                    }
                }
            }
        } catch (e: SocketException) {
            e.printStackTrace()
        }
        Log.d("xxxxxxxxxx", "all interface:$availableInterface")
        val size = availableInterface.size
        if (size > 0) {
            interfaces = arrayOfNulls(size)
            for (i in 0 until size) {
                interfaces[i] = availableInterface[i]
            }
        }
        return interfaces
    }

    /**
     * Get Ip address 自动获取IP地址
     *
     * @throws SocketException
     */
    fun getIpAddress(netInterface: String): String? {
        var hostIp: String? = null
        try {
            val nis: Enumeration<*> = NetworkInterface.getNetworkInterfaces()
            var ia: InetAddress? = null
            while (nis.hasMoreElements()) {
                val ni = nis.nextElement() as NetworkInterface
                //Log.d(TAG,"getIpAddress,interface:"+ni.getName());
                if (ni.name == netInterface) {
                    val ias = ni.inetAddresses
                    while (ias.hasMoreElements()) {
                        ia = ias.nextElement()
                        if (ia is Inet6Address) {
                            continue  // skip ipv6
                        }
                        val ip = ia.hostAddress
                        // 过滤掉127段的ip地址
                        if ("127.0.0.1" != ip) {
                            hostIp = ia.hostAddress
                            break
                        }
                    }
                }
            }
        } catch (e: SocketException) {
            e.printStackTrace()
        }
        Log.d("xxxxxxxxx", "getIpAddress,interface:$netInterface,ip:$hostIp")
        return hostIp
    }

    fun ppppp(context: Context) {
// 获取系统服务中的ConnectivityManager实例
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // 获取当前活动的网络信息
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        // 获取本机IP地址
        var ipAddress = ""
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            if (activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI) {
                // 如果是Wi-Fi网络，使用WifiManager获取IP地址
                val wifiManager =
                    context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
                ipAddress = Formatter.formatIpAddress(wifiManager.connectionInfo.ipAddress)
            } else if (activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                // 如果是移动网络，使用NetworkInterface获取IP地址
                try {
                    val networkInterfaces = NetworkInterface.getNetworkInterfaces()
                    while (networkInterfaces.hasMoreElements()) {
                        val networkInterface = networkInterfaces.nextElement()
                        val inetAddresses = networkInterface.inetAddresses
                        while (inetAddresses.hasMoreElements()) {
                            val inetAddress = inetAddresses.nextElement()
                            if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                                ipAddress = inetAddress.getHostAddress()
                                Globals.log("XXXXXXipss ipAddress$ipAddress")
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}