package com.aking.base.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.aking.base.extended.TAG_C

/**
 * Created by Rick on 2023-07-08  23:10
 * Description:
 */
abstract class BaseLifecycleActivity : AppCompatActivity() {

    protected val TAG = TAG_C
    private var lifecycleLog = false

    /**
     * 生命周期日志开关
     */
    protected fun lifecycleLogEnable(enable: Boolean) {
        lifecycleLog = enable
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (lifecycleLog) Log.v(TAG, "onCreate")
    }

    override fun onStart() {
        super.onStart()
        if (lifecycleLog) Log.v(TAG, "onStart")
    }

    override fun onRestart() {
        super.onRestart()
        if (lifecycleLog) Log.v(TAG, "onRestart")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (lifecycleLog) Log.v(TAG, "onSaveInstanceState")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (lifecycleLog) Log.v(TAG, "onNewIntent")
    }

    override fun onResume() {
        super.onResume()
        if (lifecycleLog) Log.v(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        if (lifecycleLog) Log.v(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        if (lifecycleLog) Log.v(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (lifecycleLog) Log.v(TAG, "onDestroy")
    }


}