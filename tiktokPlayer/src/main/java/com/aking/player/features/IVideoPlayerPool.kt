package com.aking.player.features

/**
 * Created by Rick on 2023-11-29  10:34.<p>
 *
 * Description:播放器池接口
 */
interface IVideoPlayerPool {
    /**
     *  获取播放器实例
     */
    fun getVideoPlayer(index: Int): IPlayer

    /**
     * 清空池
     */
    fun clear()
}