package com.aking.player.exo

import android.annotation.SuppressLint
import android.content.Context
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.aking.player.Config.MAX_BUFFER_MS
import com.aking.player.Config.MIN_BUFFER_MS
import com.aking.player.Config.PLAYBACK_BUFFER_MS
import com.aking.player.Config.RE_BUFFER_MS

/**
 * Created by Rick at 2023-11-22 0:46.
 * Description: 播放器
 */
@SuppressLint("UnsafeOptInUsageError")
class PlayerHolder(context: Context, cache: Cache) {
    val player: ExoPlayer

    init {
        // 构建一个默认的Http数据资源处理工厂
        val httpDataSourceFactory = DefaultHttpDataSource.Factory().setAllowCrossProtocolRedirects(true)

        // 这里的DefaultDataSource同时支持本地和HTTP请求的资源，自动实现检测
        val dataSourceFactory = DefaultDataSource.Factory(context, httpDataSourceFactory)

        //设置缓存
        val cacheDataSourceFactory = CacheDataSource.Factory()
            .setCache(cache)
            //设置上游数据源，缓存未命中时通过此获取数据
            .setUpstreamDataSourceFactory(dataSourceFactory)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR);


        //设置缓冲
        val loadControl = DefaultLoadControl.Builder()
            .setPrioritizeTimeOverSizeThresholds(true)//缓冲时时间优先级高于大小
            .setBufferDurationsMs(MIN_BUFFER_MS, MAX_BUFFER_MS, PLAYBACK_BUFFER_MS, RE_BUFFER_MS)
            .build()

        player = ExoPlayer.Builder(context)
            .setMediaSourceFactory(
                DefaultMediaSourceFactory(context)
                    .setDataSourceFactory(cacheDataSourceFactory)
            )
            .setLoadControl(loadControl)
            .build()
    }

    fun release() {
        player.release()
    }

}