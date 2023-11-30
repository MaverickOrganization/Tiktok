package com.aking.player.features

import android.view.View

/**
 * Created by Rick on 2023-11-29  22:05.<p>
 *
 * Description: 播放器视图接口（向上层屏蔽不同播放器视图实现的细节）
 */
interface IPlayerView<T> {
    /**
     * 用于绑定播放器，并准备播放器预加载解码视频内容，复用、解码、渲染。
     * 此时 onSurfaceCreated 尚未回调，画面未渲染至屏幕
     */
    fun onBind(item: T, position: Int)

    /**
     * 将播放视图和播放器解绑
     */
    fun onDetachPlayer(position: Int)

    /**
     * 返回当前播放器视图的实现，this
     */
    fun get(): View
}