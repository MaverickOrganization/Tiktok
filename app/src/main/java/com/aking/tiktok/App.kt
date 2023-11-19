package com.aking.tiktok

import com.airbnb.mvrx.Mavericks
import com.aking.base.base.BaseApplication
import com.aking.tiktop.httpclient.upload.UploadUtil

/**
 * Created by Rick at 2023-11-18 20:52.
 * Description:
 */
class App : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        Mavericks.initialize(this)
        UploadUtil.init(this)
    }
}