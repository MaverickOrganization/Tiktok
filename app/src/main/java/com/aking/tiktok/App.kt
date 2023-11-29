package com.aking.tiktok

import com.airbnb.mvrx.Mavericks
import com.aking.base.base.BaseApplication
import com.aking.player.VideoPlayerPool
import com.aking.tiktop.httpclient.upload.UploadUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.File

/**
 * Created by Rick at 2023-11-18 20:52.
 * Description:
 */
class App : BaseApplication(), CoroutineScope by MainScope() {

    override fun onCreate() {
        super.onCreate()
        launch(Dispatchers.IO) { initVideoPlayerPool() }
        Mavericks.initialize(this)
        UploadUtil.init(this)
    }

    private fun initVideoPlayerPool() {
        com.aking.player.VideoPlayerPool.init(File(cacheDir, Config.PLAYER_CACHE_DIR))
    }
}