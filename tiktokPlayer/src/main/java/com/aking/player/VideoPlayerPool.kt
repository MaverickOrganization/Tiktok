package com.aking.player

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.Uri
import android.os.Looper
import android.util.Log
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DataSpec
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.CacheWriter
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.MediaSource
import com.aking.player.exo.ExoMediaPlayer
import com.aking.player.features.IPlayer
import java.io.File
import java.io.InterruptedIOException
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by Rick at 2023-11-22 11:56.
 * Description: 播放器池 todo:当前依赖[ExoMediaPlayer]实现，后期解耦播放器池，配置可选播放器
 */
@SuppressLint("UnsafeOptInUsageError")
object VideoPlayerPool {
    private const val TAG = "VideoPlayerPool"

    // 池大小
    private var POOL_SIZE: Int = Config.LOOP_PLAYER_SIZE
    private val pool: Array<IPlayer?> = arrayOfNulls(POOL_SIZE)
    private lateinit var application: Application

    /** 设置缓存目录和缓存机制 */
    private lateinit var cache: Cache

    /** mediaSource工厂 */
    private lateinit var mediaSourceFactory: MediaSource.Factory

    /** 缓存数据源工厂 */
    private lateinit var cacheDataSourceFactory: CacheDataSource.Factory

    /** 预缓存 */
    private val cacheTask = ConcurrentHashMap<Uri, CacheWriter>()

    fun init(
        application: Application,
        cacheDir: File = File(application.cacheDir, Config.PLAYER_CACHE_DIR),
        poolSize: Int = Config.LOOP_PLAYER_SIZE,
    ) {
        this.application = application
        POOL_SIZE = poolSize
        cache = SimpleCache(
            cacheDir,
            LeastRecentlyUsedCacheEvictor(100 * 1024 * 1024),   /*缓存最大值为100M*/
            StandaloneDatabaseProvider(application)
        )

        // 构建一个默认的Http数据资源处理工厂
        val httpDataSourceFactory = DefaultHttpDataSource.Factory().setAllowCrossProtocolRedirects(true)

        // 这里的DefaultDataSource同时支持本地和HTTP请求的资源，自动实现检测
        val dataSourceFactory = DefaultDataSource.Factory(application, httpDataSourceFactory)

        //设置缓存
        cacheDataSourceFactory = CacheDataSource.Factory()
            .setCache(cache)
            //设置上游数据源，缓存未命中时通过此获取数据
            .setUpstreamDataSourceFactory(dataSourceFactory)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)

        mediaSourceFactory =
            DefaultMediaSourceFactory(application).setDataSourceFactory(cacheDataSourceFactory)
    }

    // 构建新的播放器实例
    private fun createVideoPlayer(context: Context): IPlayer {
        return ExoMediaPlayer(context, mediaSourceFactory)
    }

    /**
     * 从池中获取播放器实例
     */
    fun getVideoPlayer(index: Int): IPlayer {
        val realIndex = index.mod(POOL_SIZE)
        return pool[realIndex] ?: createVideoPlayer(application).also {
            pool[realIndex] = it
        }
    }


    /**
     * 释放池中所有播放器资源
     */
    fun release() = (pool.indices).forEach { index ->
        pool[index]?.release()
        pool[index] = null
    }

    fun getCache() = cache

    /**
     * 预缓存
     */
    suspend fun preCacheVideo(uri: Uri, length: Long) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw IllegalThreadStateException("called on UI Thread error.")
        }

        if (cacheTask.containsKey(uri)) return

        // 如果缓存已经足够多，则不执行任何操作
        if (cache.isCached(uri.toString(), 0, length)) {
            Log.d(TAG, "video has been cached, return")
            return
        }

        cancelCacheTask(uri)
        val dataSpec = DataSpec(uri, 0, length)
        val cacheWriter = CacheWriter(cacheDataSourceFactory.createDataSource(), dataSpec, null)
        { _, bytesCached, _ ->
            Log.w(TAG, "preCacheVideo: $bytesCached")
            if (bytesCached >= length) {
                cacheTask.remove(uri)
            }
        }
        cacheTask[uri] = cacheWriter
        runCatching { cacheWriter.cache() }.onFailure {
            if (it is InterruptedIOException) return@onFailure
            it.printStackTrace()
        }
    }

    /**
     * 取消缓存任务
     */
    fun cancelCacheTask(uri: Uri) = cacheTask[uri]?.let {
        cacheTask.remove(uri)
        Log.e(TAG, "cancelCacheTask: ")
        it.cancel()
    }

}