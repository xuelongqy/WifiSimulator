package com.knoyo.wifisimulator.provider

import com.crossbowffs.remotepreferences.RemotePreferenceProvider

/**
 * @Title: SharedPreferencesProvider类
 * @Package: com.knoyo.wifisimulator.provider
 * @Description: 配置文件提供器(统一默认获取配置和Xposed获取配置)
 * @author XueLong xuelongqy@foxmail.com
 * @date 2018/2/26 16:38
 * @update_author
 * @update_time
 * @version V1.0
 * @exception
*/
class SharedPreferencesProvider: RemotePreferenceProvider(
        PREFERENCE_PROVIDER_AUTHORITY, // 提供器标识
        arrayOf(PREFS_WIFI_INFO) // 需要共享的配置
) {

    // 伴生对象
    companion object {
        const val PREFERENCE_PROVIDER_AUTHORITY = "com.knoyo.wifisimulator.preferences"
        const val PREFS_WIFI_INFO = "wifi_info"
    }

    /**
     * @Title: checkAccess方法
     * @Class: SharedPreferencesProvider
     * @Description: 配置文件读写权限控制
     * @author XueLong xuelongqy@foxmail.com
     * @date 2018/2/26 16:41
     * @update_author
     * @update_time
     * @version V1.0
     * @param prefName[String] 权限文件
     * @param prefKey[String] 权限键
     * @param write[Boolean] 系统默认的写权限
     * @return
     * @throws
    */
    override fun checkAccess(prefName: String?, prefKey: String?, write: Boolean): Boolean {
        return true
    }
}