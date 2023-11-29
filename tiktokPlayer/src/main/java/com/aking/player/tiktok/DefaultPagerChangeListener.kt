package com.aking.player.tiktok

import android.view.View
import com.aking.player.VideoPlayerPool

/**
 * Created by Rick on 2023-11-29  22:29.<p>
 *
 * Description: 页面切换监听默认实现
 */
class DefaultPagerChangeListener : ViewPagerLayoutManager.OnViewPagerListener {
    override fun onAttach(isNext: Boolean, position: Int, view: View) {
        /* 注意：由于在 onBindViewHolder 期间已解码完成，这里只需要进入屏幕 1px，
          就会立即触发 Surface 的绘制（只会执行一次），即进入窗口的内容会显示视频的首帧画面 */
    }

    override fun onDetach(isNext: Boolean, position: Int, view: View) {
        //控制即将移除屏幕的播放器暂停，并 seekTo (0)，方便滑回屏幕时立即播放
        VideoPlayerPool.getVideoPlayer(position).run {
            pause()
            seekTo(0)
        }
    }

    /*控制当前屏幕的播放器开始播放。*/
    override fun onPageSelected(isNext: Boolean, position: Int, view: View) {
        VideoPlayerPool.getVideoPlayer(position).play()
    }
}