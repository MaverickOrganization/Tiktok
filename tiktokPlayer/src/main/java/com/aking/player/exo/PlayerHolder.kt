package com.aking.player.exo

import android.annotation.SuppressLint
import android.content.Context
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import com.aking.player.Config

/**
 * Created by Rick at 2023-11-22 0:46.
 * Description: 播放器
 */
@SuppressLint("UnsafeOptInUsageError")
class PlayerHolder(context: Context, mediaSourceFactory: MediaSource.Factory) {
    val player: ExoPlayer

    init {
        //设置缓冲
        val loadControl = DefaultLoadControl.Builder()
            .setPrioritizeTimeOverSizeThresholds(true)//缓冲时时间优先级高于大小
            .setBufferDurationsMs(
                Config.MIN_BUFFER_MS,
                Config.MAX_BUFFER_MS,
                Config.PLAYBACK_BUFFER_MS,
                Config.RE_BUFFER_MS
            ).build()

        player = ExoPlayer.Builder(context)
            .setMediaSourceFactory(mediaSourceFactory)
            .setLoadControl(loadControl)
            .build()
    }

    fun release() {
        player.release()
    }

}