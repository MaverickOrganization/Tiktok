package com.aking.tiktok.ui.test

import android.annotation.SuppressLint
import android.content.Context
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.ExoPlayer
import com.aking.base.app
import com.aking.tiktok.widget.Config
import java.io.File

/**
 * Created by Rick at 2023-11-22 11:56.
 * Description: 播放器池
 */
@SuppressLint("UnsafeOptInUsageError")
object VideoPlayerPool {
    // 池大小
    private const val POOL_SIZE = Config.LOOP_PLAYER_SIZE
    private val pool: Array<PlayerHolder?> = arrayOfNulls(POOL_SIZE)

    // 设置缓存目录和缓存机制
    private lateinit var cache: Cache

    fun init(cacheDir: File) {
        cache = SimpleCache(
            cacheDir,
            LeastRecentlyUsedCacheEvictor(100 * 1024 * 1024),   /*缓存最大值为100M*/
            StandaloneDatabaseProvider(app)
        )
    }

    // 构建新的播放器实例
    private fun createVideoPlayer(context: Context): PlayerHolder {
        return PlayerHolder(context, cache)
    }

    // 从池中获取播放器实例
    fun getVideoPlayer(index: Int): ExoPlayer {
        val realIndex = index.mod(POOL_SIZE)
        return (pool[realIndex] ?: createVideoPlayer(app).also {
            pool[realIndex] = it
        }).player
    }


    // 释放池中所有播放器资源
    fun release() {
        (pool.indices).forEach { index ->
            pool[index]?.release()
            pool[index] = null
        }
    }

}