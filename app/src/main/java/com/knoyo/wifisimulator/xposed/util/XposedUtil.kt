package com.knoyo.wifisimulator.xposed.util

/**
 * @Title: XposedUtil类
 * @Package: com.knoyo.wifisimulator.xposed.util
 * @Description: Sposed工具类
 * @author XueLong xuelongqy@foxmail.com
 * @date 2018/5/4 13:34
 * @update_author
 * @update_time
 * @version V1.0
 * @exception
*/
object XposedUtil {
    /**
     * @Title: isXposedInstalled方法
     * @Class: XposedUtil
     * @Description: 检验Xpose是否安装
     * @author XueLong xuelongqy@foxmail.com
     * @date 2018/5/4 13:11
     * @update_author
     * @update_time
     * @version V1.0
     * @param Xposed是否安装
     * @return
     * @throws
    */
    fun isXposedInstalled(): Boolean {
        val stack = Thread.currentThread().stackTrace
        for (i in stack.size - 3 until stack.size) {
            if (stack[i].toString().contains("de.robv.android.xposed.XposedBridge"))
                return true
        }
        return false
    }

    /**
     * @Title: isXposedActive方法
     * @Class: XposedUtil
     * @Description: 判断xposed是否激活(注: 激活后会使用hook返回true)
     * @author XueLong xuelongqy@foxmail.com
     * @date 2018/5/4 13:10
     * @update_author
     * @update_time
     * @version V1.0
     * @param Xposed是否激活
     * @return
     * @throws
    */
    fun isXposedActive(): Boolean {
        return false
    }
}