package com.aking.player.features

import android.widget.MediaController
import java.net.URL

/**
 * Created by Rick on 2023-11-29  10:12.<p>
 *
 * Description:播放器接口（向上层屏蔽不同播放器实现的细节）
 */
interface IPlayer {
    /**
     * 资源地址
     */
    var url: URL?

    /**
     * 视频控制器，用于上层绘制进度条
     */
    var playControl: MediaController.MediaPlayerControl

    /**
     * 状态监听器
     */
    var listener: OnPlayerStateListener?

    /**
     * 开始播放
     */
    fun play()

    /**
     * 加载视频
     */
    fun prepare()

    /**
     * 暂停播放
     */
    fun pause()

    /**
     * 停止播放
     */
    fun stop()

    /**
     * 设置进度
     */
    fun seekTo(position: Long)

    /**
     * 销毁资源
     */
    fun release()

    /**
     * 将播放器和视图绑定
     */
    fun attachPlayerView(view: IPlayerView<*>)

    /**
     * 设置播放模式
     */
    fun setRepeatMode(repeatMode: Int)

}