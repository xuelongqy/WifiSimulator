package com.knoyo.wifisimulator.preferences

import android.content.Context
import android.content.SharedPreferences
import com.crossbowffs.remotepreferences.RemotePreferences
import com.knoyo.wifisimulator.provider.SharedPreferencesProvider.Companion.PREFERENCE_PROVIDER_AUTHORITY
import com.knoyo.wifisimulator.provider.SharedPreferencesProvider.Companion.PREFS_WIFI_INFO

/**
 * @Title: WifiInfoPrefs类
 * @Package: com.knoyo.wifisimulator.preferences
 * @Description: WIFI信息配置
 * @author XueLong xuelongqy@foxmail.com
 * @date 2018/7/5 12:20
 * @update_author
 * @update_time
 * @version V1.0
 * @exception
*/
class WifiInfoPrefs(val context: Context) {
    companion object {
        const val IS_SIMULATION = "is_simulation"
        const val WIFI_NAME = "wifi_name"
        const val WIFI_BSSID = "wifi_bssid"
        const val WIFI_IP = "wifi_ip"
        const val APPS = "apps"
    }

    // 获取WIFI信息配置
    private val prefs: SharedPreferences = RemotePreferences(context,
            PREFERENCE_PROVIDER_AUTHORITY,
            PREFS_WIFI_INFO, true)

    // 是否模拟
    var isSimulation: Boolean
        get() {
            return prefs.getBoolean(IS_SIMULATION, false)
        }
        set(value) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putBoolean(IS_SIMULATION, value)
            editor.apply()
        }

    // WIFI名字
    var wifiName: String
        get() {
            return prefs.getString(WIFI_NAME, "")
        }
        set(value) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putString(WIFI_NAME, value)
            editor.apply()
        }

    // WIFI BSSID
    var wifiBssid: String
        get() {
            return prefs.getString(WIFI_BSSID, "")
        }
        set(value) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putString(WIFI_BSSID, value)
            editor.apply()
        }

    // WIFI IP
    var wifiIP: Int
        get() {
            return prefs.getInt(WIFI_IP, 0)
        }
        set(value) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(WIFI_IP, value)
            editor.apply()
        }

    // 需要模拟的App
    var apps: String
        get() {
            return prefs.getString(APPS, "")
        }
        set(value) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putString(APPS, value)
            editor.apply()
        }
}