package com.aking.tiktok.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.media3.common.Player
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.aking.base.extended.TAG_C
import com.aking.base.widget.inflater
import com.aking.data.model.TestBean
import com.aking.player.VideoPlayerPool
import com.aking.player.features.IPlayerView
import com.aking.tiktok.databinding.LayoutVideoContentBinding
import java.net.URL

/**
 * Created by Rick at 2023-11-22 2:45.
 * Description:
 */
@SuppressLint("UnsafeOptInUsageError")
class TiktokView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : PlayerView(context, attrs), IPlayerView<TestBean> {

    private val binding = LayoutVideoContentBinding.inflate(inflater(), this, true)

    init {
        controllerAutoShow = false
        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH
    }

    /* 用于绑定播放器，并预加载解码视频内容，复用、解码、渲染。
       此时 onSurfaceCreated 尚未回调，画面未渲染至屏幕 */
    override fun onBind(item: TestBean, position: Int) {
        Log.e(TAG_C, "onBind:${this.hashCode()} $position -- $item")
        val player = VideoPlayerPool.getVideoPlayer(position)
        player.attachPlayerView(this)   //播放器附加到视图
        player.setRepeatMode(Player.REPEAT_MODE_ALL)   //循环播放
        player.url = URL(item.url)      //设置媒体项
        player.prepare()

        binding.item = item
    }

    override fun onDetachPlayer() {
        player = null
    }

    override fun get(): View = this
}