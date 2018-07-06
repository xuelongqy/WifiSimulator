package com.knoyo.wifisimulator.bean

import android.graphics.drawable.Drawable

/**
 * @Title: AppInfo实体类
 * @Package: com.knoyo.wifisimulator.bean
 * @Description: App信息实体
 * @author XueLong xuelongqy@foxmail.com
 * @date 2018/7/6 11:32
 * @update_author
 * @update_time
 * @version V1.0
 * @exception
*/
data class AppInfo (
        // 图标
        val icon: Drawable,
        // 名字
        val name: String,
        // 是否模拟
        var isSimulation: Boolean = false
)