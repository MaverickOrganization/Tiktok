package com.aking.base.base

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy
import com.aking.base.AppGlobe
import com.aking.base.BuildConfig
import timber.log.Timber


/**
 * Created by Rick at 2023-11-17 0:19.
 * Description:
 */
open class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppGlobe.init(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            turnOnStrictMode()
        }
    }

    /**
     * 开启严苛模式，当代码有违规操作时，可以通过Logcat或崩溃的方式提醒我们
     */
    private fun turnOnStrictMode() {
        //线程检测策略
        StrictMode.setThreadPolicy(
            ThreadPolicy.Builder().detectDiskReads() //检测主线程磁盘读取操作
                .detectDiskWrites() //检测主线程磁盘写入操作
                .detectNetwork() //检测主线程网络请求操作
                .detectCustomSlowCalls() //监测自定义运行缓慢函数
                .detectResourceMismatches() //检测发现资源不匹配
                .penaltyLog() //违规操作以log形式输出
                .penaltyDialog()//监测到上述状况时弹出对话框
                .build()
        )

        //虚拟机检测策略
        StrictMode.setVmPolicy(
            VmPolicy.Builder().detectLeakedSqlLiteObjects() //检测SqlLite泄漏
                .detectLeakedClosableObjects() //检测未关闭的closable对象泄漏
                .detectActivityLeaks()  //检测Activity 的内存泄露情况
                .detectLeakedRegistrationObjects()  //检测BroadcastReceiver、ServiceConnection是否被释放
                .detectFileUriExposure()    //检测file://或者是content:
                .penaltyLog()   //违规操作以log形式输出
//                .penaltyDeath() //发生违规操作时，直接崩溃
                .build()
        )
    }

    /**
     * 允许磁盘读取，且被StrictMode忽略
     */
    private fun permitDiskReads(func: () -> Any): Any {
        return if (BuildConfig.DEBUG) {
            val oldThreadPolicy = StrictMode.getThreadPolicy()
            StrictMode.setThreadPolicy(
                ThreadPolicy.Builder(oldThreadPolicy).permitDiskReads().build()
            )
            val anyValue = func()
            StrictMode.setThreadPolicy(oldThreadPolicy)

            anyValue
        } else {
            func()
        }
    }

}