package com.aking.base.base

import android.app.Application
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
        }
    }
}