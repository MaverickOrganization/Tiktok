package com.aking.player.features

/**
 * Created by Rick on 2023-11-29  10:23.<p>
 *
 * Description: 播放状态
 */
sealed interface State {
    /**
     * 第一帧被渲染
     */
    object FirstFrameRendered : State

    /**
     * 缓冲结束，随时可播放。
     */
    object Ready : State

    /**
     * 播放出错
     */
    class Error(val exception: Exception) : State

    /**
     * 播放中
     */
    object Playing : State

    /**
     * 播放手动停止
     */
    object Stop : State

    /**
     * 播放结束
     */
    object End : State

    /**
     * 缓冲中
     */
    object Buffering : State
}