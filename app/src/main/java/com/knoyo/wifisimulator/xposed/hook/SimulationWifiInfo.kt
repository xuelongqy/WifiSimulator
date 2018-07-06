package com.knoyo.wifisimulator.xposed.hook

import android.content.Context
import android.net.wifi.WifiInfo
import com.knoyo.wifisimulator.preferences.WifiInfoPrefs
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers

object SimulationWifiInfo {
    // 获取WIFI名字方法
    const val GET_WIFI_NAME_METHOD = "getSSID"
    // 获取WIFI BSSID方法
    const val GET_WIFI_BSSID_METHOD = "getBSSID"
    // 获取WIFI IP方法
    const val GET_WIFI_IP_METHOD = "getIpAddress"

    /**
     * @Title: initSimulationWeWordWifi方法
     * @Class: SimulationWifiInfo
     * @Description: 模拟WIFI信息
     * @author XueLong xuelongqy@foxmail.com
     * @date 2018/7/5 12:59
     * @update_author
     * @update_time
     * @version V1.0
     * @param
     * @return
     * @throws
    */
    fun initSimulationWeWordWifi(mContext: Context) {
        // 获取WIFI信息配置
        val wifiInfoPrefs = WifiInfoPrefs(mContext)
        // 修改WIFI名字
        try {
            XposedHelpers.findAndHookMethod(WifiInfo::class.java, GET_WIFI_NAME_METHOD, object : XC_MethodHook(){
                @Throws(Throwable::class)
                override fun beforeHookedMethod(param: MethodHookParam) {
                    XposedBridge.log("${SimulationWifiInfo::class.java.name} -> hook $GET_WIFI_NAME_METHOD")
                    param.result = "\"${wifiInfoPrefs.wifiName}\""
                }
            })
        }catch (t: Throwable) {
            XposedBridge.log("${SimulationWifiInfo::class.java.name} -> ${t.localizedMessage}")
        }
        // 修改WIFI BSSID
        try {
            XposedHelpers.findAndHookMethod(WifiInfo::class.java, GET_WIFI_BSSID_METHOD, object : XC_MethodHook(){
                @Throws(Throwable::class)
                override fun beforeHookedMethod(param: MethodHookParam) {
                    XposedBridge.log("${SimulationWifiInfo::class.java.name} -> hook $GET_WIFI_BSSID_METHOD")
                    param.result = wifiInfoPrefs.wifiBssid
                }
            })
        }catch (t: Throwable) {
            XposedBridge.log("${SimulationWifiInfo::class.java.name} -> ${t.localizedMessage}")
        }
        // 修改WIFI IP
        try {
            XposedHelpers.findAndHookMethod(WifiInfo::class.java, GET_WIFI_IP_METHOD, object : XC_MethodHook(){
                @Throws(Throwable::class)
                override fun beforeHookedMethod(param: MethodHookParam) {
                    XposedBridge.log("${SimulationWifiInfo::class.java.name} -> hook $GET_WIFI_IP_METHOD")
                    param.result = wifiInfoPrefs.wifiIP
                }
            })
        }catch (t: Throwable) {
            XposedBridge.log("${SimulationWifiInfo::class.java.name} -> ${t.localizedMessage}")
        }
    }
}