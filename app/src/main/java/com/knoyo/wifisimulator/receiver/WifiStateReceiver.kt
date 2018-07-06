package com.knoyo.wifisimulator.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.net.NetworkInfo

/**
 * @Title: WifiStateReceiver类
 * @Package: com.knoyo.wifisimulator.receiver
 * @Description: 监听WIFI状态
 * @author XueLong xuelongqy@foxmail.com
 * @date 2017/12/4 16:03
 * @update_author
 * @update_time
 * @version V1.0
*/
class WifiStateReceiver(wifiStateChangeListener: WifiStateChangeListener): BroadcastReceiver() {
    // WIFI管理器
    private lateinit var wifiManager:WifiManager
    // WIFI状态改变监听器
    private var wifiStateChangeListener: WifiStateChangeListener = wifiStateChangeListener
    fun setWifiStateChangeListener(wifiStateChangeListener: WifiStateChangeListener) {
        this.wifiStateChangeListener = wifiStateChangeListener
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        // 获取WIFI管理器
        wifiManager = context!!.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        // 获取响应事件的action
        val action = intent!!.action
        when (action) {
            WifiManager.RSSI_CHANGED_ACTION ->
                //信号强度变化
                wifiStateChangeListener.onSignalStrengthChanged(getStrength())
            WifiManager.NETWORK_STATE_CHANGED_ACTION -> {
                val info = intent.getParcelableExtra<NetworkInfo>(WifiManager.EXTRA_NETWORK_INFO)
                when (info.detailedState) {
                    NetworkInfo.DetailedState.DISCONNECTED ->
                        //wifi已断开
                        wifiStateChangeListener.onDisconnect()
                    NetworkInfo.DetailedState.CONNECTING ->
                        //正在连接...
                        wifiStateChangeListener.onConnecting()
                    NetworkInfo.DetailedState.CONNECTED ->
                        //连接到网络
                        wifiStateChangeListener.onConnected()
                    NetworkInfo.DetailedState.OBTAINING_IPADDR ->
                        //正在获取IP地址
                        wifiStateChangeListener.onGettingIP()
                    NetworkInfo.DetailedState.FAILED ->
                        //连接失败
                        wifiStateChangeListener.onFailed()
                    else -> {}
                }
            }
            WifiManager.WIFI_STATE_CHANGED_ACTION -> {
                val wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0)
                when (wifiState) {
                    WifiManager.WIFI_STATE_ENABLING ->
                        //wifi正在启用
                        wifiStateChangeListener.onEnabling()
                    WifiManager.WIFI_STATE_ENABLED ->
                        //Wifi已启用
                        wifiStateChangeListener.onEnable()
                    WifiManager.WIFI_STATE_DISABLING ->
                        //Wifi正在关闭
                        wifiStateChangeListener.onDisable()
                    WifiManager.WIFI_STATE_DISABLED ->
                        //Wifi已关闭
                        wifiStateChangeListener.onDisable()
                }
            }
            WifiManager.SUPPLICANT_STATE_CHANGED_ACTION -> {
                val error = intent.getIntExtra(WifiManager.EXTRA_SUPPLICANT_ERROR, -100)
                if (error == WifiManager.ERROR_AUTHENTICATING) {
                    //wifi密码认证错误！
                    wifiStateChangeListener.onPasswordError()
                }
            }
            WifiManager.NETWORK_IDS_CHANGED_ACTION ->
                //已经配置的网络的ID可能发生变化时
                wifiStateChangeListener.onIDChange()
            ConnectivityManager.CONNECTIVITY_ACTION ->{
                //连接状态发生变化，暂时没用到
                //val type = intent.getIntExtra(ConnectivityManager.EXTRA_NETWORK_TYPE, 0)
            }
            else -> {
                return
            }
        }
    }

    /**
     * @Title: getStrength方法
     * @Class: WifiStateReceiver
     * @Description: 获取WIFI强度
     * @author XueLong xuelongqy@foxmail.com
     * @date 2017/12/4 16:48
     * @update_author
     * @update_time
     * @version V1.0
    */
    private fun getStrength(): Int {
        val info = wifiManager.connectionInfo
        return if (info.bssid != null) {
            WifiManager.calculateSignalLevel(info.rssi, 5)
        } else 0
    }

    /**
     * @Title: WifiStateChangeListener接口
     * @Class: WifiStateChangeListener
     * @Description: 当WIFI状态改变时响应事件的接口(监听器)
     * @author XueLong xuelongqy@foxmail.com
     * @date 2017/12/4 16:07
     * @update_author
     * @update_time
     * @version V1.0
    */
    interface WifiStateChangeListener {
        // WIFI正在连接
        fun onConnecting()
        // WIFI断开
        fun onDisconnect()
        // WIFI连接到网络
        fun onConnected()
        // WIFI获取IP地址
        fun onGettingIP()
        // WIFI连接失败
        fun onFailed()
        // WIFI正在启用
        fun onEnabling()
        // WIFI启用成功
        fun onEnable()
        // WIFI正在关闭
        fun onDisabling()
        // WIFI已经关闭
        fun onDisable()
        // WIFI密码错误
        fun onPasswordError()
        // 已经配置的网络的ID可能发生变化时
        fun onIDChange()
        // 信号发生变化时
        fun onSignalStrengthChanged(strength: Int)
    }

    /**
     * @Title: WifiStateChangeListenerImpl类
     * @Package: cn.cas.snoy.cameraremote.receiver
     * @Description: 空的相机事件监听器
     * @author XueLong xuelongqy@foxmail.com
     * @date 2017/12/27 10:57
     * @update_author
     * @update_time
     * @version V1.0
     * @exception
    */
    open class WifiStateChangeListenerImpl: WifiStateChangeListener {
        override fun onConnecting() {
        }

        override fun onDisconnect() {
        }

        override fun onConnected() {
        }

        override fun onGettingIP() {
        }

        override fun onFailed() {
        }

        override fun onEnabling() {
        }

        override fun onEnable() {
        }

        override fun onDisabling() {
        }

        override fun onDisable() {
        }

        override fun onPasswordError() {
        }

        override fun onIDChange() {
        }

        override fun onSignalStrengthChanged(strength: Int) {
        }

    }
}