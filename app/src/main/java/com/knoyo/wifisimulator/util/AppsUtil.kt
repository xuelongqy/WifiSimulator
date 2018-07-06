package com.knoyo.wifisimulator.util

import android.content.Context
import com.knoyo.wifisimulator.bean.AppInfo
import android.content.pm.PackageInfo



/**
 * @Title: AppsUtil类
 * @Package: com.knoyo.wifisimulator.util
 * @Description: App工具类,用于获取设备中所有的应用信息
 * @author XueLong xuelongqy@foxmail.com
 * @date 2018/7/6 11:36
 * @update_author
 * @update_time
 * @version V1.0
 * @exception
*/
class AppsUtil(val context: Context) {
    /**
     * @Title: getAllAppsInfo方法
     * @Class: AppsUtil
     * @Description: 获取所有应用信息
     * @author XueLong xuelongqy@foxmail.com
     * @date 2018/7/6 11:37
     * @update_author
     * @update_time
     * @version V1.0
     * @param
     * @return
     * @throws
    */
    fun getAllAppsInfo(): HashMap<String, AppInfo> {
        // 缓存AppMap对象
        val appsMap = hashMapOf<String, AppInfo>()
        // 获取App列表
        val packages = context.packageManager.getInstalledPackages(0)
        packages.forEach {
            val appInfo = AppInfo(
                    it.applicationInfo.loadIcon(context.packageManager),
                    it.applicationInfo.loadLabel(context.packageManager).toString()
            )
            appsMap[it.packageName] = appInfo
        }
        return appsMap
    }
}