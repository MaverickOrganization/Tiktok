package com.aking.player

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import com.aking.player.exo.ExoMediaPlayer
import com.aking.player.features.IPlayer
import java.io.File

/**
 * Created by Rick at 2023-11-22 11:56.
 * Description: 播放器池 todo:当前依赖[ExoMediaPlayer]实现，后期解耦播放器池，配置可选播放器
 */
@SuppressLint("UnsafeOptInUsageError")
object VideoPlayerPool {
    // 池大小
    private var POOL_SIZE: Int = Config.LOOP_PLAYER_SIZE
    private val pool: Array<IPlayer?> = arrayOfNulls(POOL_SIZE)
    private lateinit var application: Application

    // 设置缓存目录和缓存机制
    private lateinit var cache: Cache

    fun init(
        application: Application,
        cacheDir: File = File(application.cacheDir, Config.PLAYER_CACHE_DIR),
        poolSize: Int = Config.LOOP_PLAYER_SIZE
    ) {
        this.application = application
        POOL_SIZE = poolSize
        cache = SimpleCache(
            cacheDir,
            LeastRecentlyUsedCacheEvictor(100 * 1024 * 1024),   /*缓存最大值为100M*/
            StandaloneDatabaseProvider(application)
        )
    }

    // 构建新的播放器实例
    private fun createVideoPlayer(context: Context): IPlayer {
        return ExoMediaPlayer(context, cache)
    }

    // 从池中获取播放器实例
    fun getVideoPlayer(index: Int): IPlayer {
        val realIndex = index.mod(POOL_SIZE)
        return pool[realIndex] ?: createVideoPlayer(application).also {
            pool[realIndex] = it
        }
    }


    // 释放池中所有播放器资源
    fun release() {
        (pool.indices).forEach { index ->
            pool[index]?.release()
            pool[index] = null
        }
    }

}