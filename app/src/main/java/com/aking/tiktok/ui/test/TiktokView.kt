package com.aking.tiktok.ui.test

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.aking.base.extended.TAG_C
import com.aking.base.widget.inflater
import com.aking.data.model.TestBean
import com.aking.tiktok.databinding.LayoutVideoContentBinding

/**
 * Created by Rick at 2023-11-22 2:45.
 * Description:
 */
@SuppressLint("UnsafeOptInUsageError")
class TiktokView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : PlayerView(context, attrs) {

    private val binding: LayoutVideoContentBinding = LayoutVideoContentBinding.inflate(inflater(), this, true)

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH
        setShowBuffering(SHOW_BUFFERING_WHEN_PLAYING)   //缓冲条
    }

    // 创建播放器，并预加载解码视频内容，播放器控制解析到首帧时暂停
    fun onBind(item: TestBean, position: Int) {
        Log.e(TAG_C, "onBind:${this.hashCode()} $position -- $item")
        val player = VideoPlayerPool.getVideoPlayer(position)
        setPlayer(player)   //播放器附加到视图
        player.repeatMode = Player.REPEAT_MODE_ALL  //循环播放
        val mediaItem = MediaItem.fromUri(item.url)
        player.setMediaItem(mediaItem)      //设置媒体项
        player.prepare()

        binding.item = item
    }

}