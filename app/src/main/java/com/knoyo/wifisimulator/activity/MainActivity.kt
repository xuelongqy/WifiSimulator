package com.knoyo.wifisimulator.activity

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.knoyo.wifisimulator.R
import com.knoyo.wifisimulator.receiver.WifiStateReceiver
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.knoyo.wifisimulator.preferences.WifiInfoPrefs
import com.knoyo.wifisimulator.xposed.util.XposedUtil
import kotlinx.android.synthetic.main.activity_main.*
import moe.feng.alipay.zerosdk.AlipayZeroSdk


/**
 * @Title: MainActivity类
 * @Package: com.knoyo.wifisimulator.activity
 * @Description: 主页面
 * @author XueLong xuelongqy@foxmail.com
 * @date 2018/7/5 10:47
 * @update_author
 * @update_time
 * @version V1.0
 * @exception
*/
class MainActivity : AppCompatActivity() {

    // WIFI信息配置文件
    private lateinit var wifiInfoPrefs: WifiInfoPrefs
    // WIFI广播接收器
    private lateinit var wifiStateReceiver: WifiStateReceiver
    // WIFI状态监听器
    private lateinit var wifiStateChangeListener: WifiStateReceiver.WifiStateChangeListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 初始化
        init()
        // 更新模拟信息
        updateSimulatorInfo()
        // 注册WIFI广播接收器
        registerWifiStateReceiver()
    }

    override fun onDestroy() {
        // 注销WIFI广播接收器
        unRegisterWifiStateReceiver()
        super.onDestroy()
    }

    /**
     * @Title: init方法
     * @Class: MainActivity
     * @Description: 初始化函数
     * @author XueLong xuelongqy@foxmail.com
     * @date 2018/7/5 10:48
     * @update_author
     * @update_time
     * @version V1.0
     * @param
     * @return
     * @throws
    */
    private fun init() {
        // 初始化WIFI信息配置
        wifiInfoPrefs =  WifiInfoPrefs(this)
        // 初始化WIFI状态监听器
        wifiStateChangeListener = object : WifiStateReceiver.WifiStateChangeListenerImpl() {
            // WIFI断开
            override fun onDisconnect() {
                // 清除WIFI信息
                main_now_wifi_connect_status.text = this@MainActivity.getString(R.string.no)
                main_now_wifi_name.text = ""
                main_now_wifi_bssid.text = ""
                main_now_wifi_ip.text = ""
            }
            // WIFI连接成功
            override fun onConnected() {
                val wifiInfo = (this@MainActivity.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager).connectionInfo
                // 设置WIFI信息
                main_now_wifi_connect_status.text = this@MainActivity.getString(R.string.yes)
                main_now_wifi_name.text = wifiInfo.ssid.replace("\"","")
                main_now_wifi_bssid.text = wifiInfo.bssid
                main_now_wifi_ip.text = wifiInfo.ipAddress.toString()
            }
        }
        // 初始化WIFI广播接收器
        wifiStateReceiver = WifiStateReceiver(wifiStateChangeListener)
        // 获取配置文件中的信息
        main_set_wifi_name.setText(wifiInfoPrefs.wifiName)
        main_set_wifi_bssid.setText(wifiInfoPrefs.wifiBssid)
        main_set_wifi_ip.setText(wifiInfoPrefs.wifiIP.toString())
        // 设置WIFI信息按钮
        main_set_wifi_info.setOnClickListener {
            // 判断是否获取WIFI信息
            if (getString(R.string.yes) == main_now_wifi_connect_status.text.toString()) {
                main_set_wifi_name.setText(main_now_wifi_name.text)
                main_set_wifi_bssid.setText(main_now_wifi_bssid.text)
                main_set_wifi_ip.setText(main_now_wifi_ip.text)
            }
        }
        // 使用WIFI信息按钮
        main_use_wifi_info.setOnClickListener {
            // 判断信息是否完整
            if (main_set_wifi_name.text.toString() != "" && main_set_wifi_bssid.text.toString() != "") {
                wifiInfoPrefs.wifiName = main_set_wifi_name.text.toString()
                wifiInfoPrefs.wifiBssid = main_set_wifi_bssid.text.toString()
                wifiInfoPrefs.wifiIP = main_set_wifi_ip.text.toString().toInt()
                wifiInfoPrefs.isSimulation = true
                // 更新模拟信息
                updateSimulatorInfo()
                // 检验Xposed是否激活
                if (XposedUtil.isXposedActive()) {
                    Toast.makeText(this@MainActivity, this@MainActivity.getString(R.string.successfulSimulation), Toast.LENGTH_LONG).show()
                }else {
                    Toast.makeText(this@MainActivity, this@MainActivity.getString(R.string.toActiveXposed), Toast.LENGTH_LONG).show()
                }
            }else {
                Toast.makeText(this@MainActivity, this@MainActivity.getString(R.string.failureSimulation), Toast.LENGTH_LONG).show()
            }
        }
        // 取消模拟
        main_cancel_simulation.setOnClickListener {
            wifiInfoPrefs.isSimulation = false
            // 更新模拟信息
            updateSimulatorInfo()
        }
        // 应用列表按钮
        main_app_list.setOnClickListener {
            startActivity(Intent(this, AppListActivity::class.java))
        }
        // 支持我按钮
        main_support_me.setOnClickListener {
            AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert)
                    .setTitle(R.string.supportMode)
                    .setItems(arrayOf(getString(R.string.githubStar),getString(R.string.aliPay)), { _, which ->
                        when (which) {
                            0 -> {
                                val uri = Uri.parse("https://github.com/xuelongqy/WifiSimulator")
                                val intent = Intent(Intent.ACTION_VIEW, uri)
                                startActivity(intent)
                            }
                            1 -> {
                                if (!AlipayZeroSdk.hasInstalledAlipayClient(this)) {
                                    Toast.makeText(this, R.string.alipayNotFound, Toast.LENGTH_SHORT).show()
                                    return@setItems
                                }
                                AlipayZeroSdk.startAlipayClient(this, "FKX03889Z997BS1BNALOC9")
                            }
                        }
                    }).show()
        }
    }



    /**
     * @Title: registerWifiStateReceiver
     * @Class: MainActivity
     * @Description: 注册WIFI广播接收器
     * @author XueLong xuelongqy@foxmail.com
     * @date 2018/7/5 10:58
     * @update_author
     * @update_time
     * @version V1.0
     * @param
     * @return
     * @throws
    */
    private fun registerWifiStateReceiver() {
        // 判断WIFI广播接收器是否注册过
        val mFilter = IntentFilter()
        mFilter.addAction(WifiManager.RSSI_CHANGED_ACTION) //信号强度变化
        mFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION) //网络状态变化
        mFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION) //wifi状态，是否连上，密码
        mFilter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION) //是不是正在获得IP地址
        mFilter.addAction(WifiManager.NETWORK_IDS_CHANGED_ACTION)
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        this.registerReceiver(wifiStateReceiver,mFilter)
    }

    /**
     * @Title: unRegisterWifiStateReceiver方法
     * @Class: MainActivity
     * @Description: 注销WIFI广播接收器
     * @author XueLong xuelongqy@foxmail.com
     * @date 2018/7/5 10:59
     * @update_author
     * @update_time
     * @version V1.0
     * @param
     * @return
     * @throws
    */
    private fun unRegisterWifiStateReceiver() {
        this.unregisterReceiver(wifiStateReceiver)
    }

    /**
     * @Title: updateSimulatorInfo方法
     * @Class: MainActivity
     * @Description: 更新模拟信息
     * @author XueLong xuelongqy@foxmail.com
     * @date 2018/7/6 10:23
     * @update_author
     * @update_time
     * @version V1.0
     * @param
     * @return
     * @throws
    */
    private fun updateSimulatorInfo() {
        // 获取Xposed激活状态
        if (XposedUtil.isXposedActive()) {
            main_simulator_active.text = getString(R.string.yes)
        }else {
            main_simulator_active.text = getString(R.string.no)
        }
        // 获取模拟状态
        if (wifiInfoPrefs.isSimulation) {
            main_simulator_status.text = getString(R.string.yes)
        }else {
            main_simulator_status.text = getString(R.string.no)
        }
        // 获取配置中信息
        main_simulator_wifi_name.text = wifiInfoPrefs.wifiName
        main_simulator_wifi_bssid.text = wifiInfoPrefs.wifiBssid
        main_simulator_wifi_ip.text = wifiInfoPrefs.wifiIP.toString()
    }
}
