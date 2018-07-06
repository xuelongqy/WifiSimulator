package com.knoyo.wifisimulator.xposed.hook

import android.app.Application
import android.content.Context
import com.knoyo.wifisimulator.preferences.WifiInfoPrefs
import de.robv.android.xposed.*
import de.robv.android.xposed.callbacks.XC_LoadPackage

/**
 * @Title: Main类
 * @Package: com.knoyo.wifisimulator.xposed.hook
 * @Description: Xposed hook主类
 * @author XueLong xuelongqy@foxmail.com
 * @date 2018/7/6 9:20
 * @update_author
 * @update_time
 * @version V1.0
 * @exception
*/
class Main: IXposedHookZygoteInit, IXposedHookLoadPackage {

    // hook模拟WIFI对象
    private var fakeWifiConnection = FakeWifiConnection()

    // Xposed模块初始化完成
    @Throws(Throwable::class)
    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam?) {
        XposedBridge.log("com.knoyo.wifisimulator -> Xposed loaded!")
    }

    /**
     * @Title: 重写handleLoadPackage方法
     * @Class: Main
     * @Description: 监听方法并hook
     * @author XueLong xuelongqy@foxmail.com
     * @date 2018/7/5 12:42
     * @update_author
     * @update_time
     * @version V1.0
     * @param lpparam[XC_LoadPackage.LoadPackageParam] hook所需参数
     * @return
     * @throws
    */
    @Throws(Throwable::class)
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        // 注册Hook
        XposedHelpers.findAndHookMethod(Application::class.java, "attach", Context::class.java, object : XC_MethodHook() {
            // 方法执行前hook
            @Throws(Throwable::class)
            override fun afterHookedMethod(param: MethodHookParam) {
            }

            // 方法执行后hook
            @Throws(Throwable::class)
            override fun beforeHookedMethod(param: MethodHookParam) {
                // 获取上下文
                val mContext = param.args[0] as Context
                // 获取配置
                val wifiInfoPrefs = WifiInfoPrefs(mContext)
                // 开始hook
                when (lpparam.packageName) {
                    // 此应用(WIFI模拟器)
                    WifiSimulator.WIFI_SIMULATOR_PKG_NAME -> {
                        WifiSimulator.initXposedActive(lpparam)
                    }
                }
                // 判断是否开启模拟
                if (!wifiInfoPrefs.isSimulation) return
                // 判断模拟WIFI应用列表是否包含此应用
                if (wifiInfoPrefs.apps.contains(lpparam.packageName)) {
                    fakeWifiConnection.initFakeWifiConnection(lpparam)
                    SimulationWifiInfo.initSimulationWeWordWifi(mContext)
                }
            }
        })
    }
}