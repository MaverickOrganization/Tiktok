package com.aking.base

import android.app.Application

/**
 * Created by Rick on 2023-01-30  21:12.
 * Description: 全局ApplicationContext
 */

/**
 * 获取Application
 */
val app get() :Application = AppGlobe.get()

object AppGlobe {
    private const val TAG = "AppGlobal"
    private lateinit var app: Application

    internal fun init(application: Application) {
        app = application
    }

    /**
     * 获取Application
     */
    fun get(): Application {
        if (!this::app.isInitialized) {
            error("$TAG AppGlobal context is not initialize.")
        }
        return app
    }

}