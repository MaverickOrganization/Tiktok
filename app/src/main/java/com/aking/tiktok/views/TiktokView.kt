package com.aking.tiktok.views

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import androidx.core.net.toUri
import androidx.media3.common.Player
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.aking.base.extended.TAG_C
import com.aking.base.widget.inflater
import com.aking.data.model.TestBean
import com.aking.player.VideoPlayerPool
import com.aking.player.features.IPlayerView
import com.aking.player.features.OnPlayerStateListener
import com.aking.player.features.State
import com.aking.tiktok.databinding.LayoutVideoContentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by Rick at 2023-11-22 2:45.
 * Description:
 */
@SuppressLint("UnsafeOptInUsageError")
class TiktokView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : PlayerView(context, attrs), IPlayerView<TestBean>, CoroutineScope by MainScope() {

    private val binding = LayoutVideoContentBinding.inflate(inflater(), this, true)
    private lateinit var uri: Uri

    init {
        controllerAutoShow = false
        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH
    }

    /* 用于绑定播放器，并预加载解码视频内容，复用、解码、渲染。
       此时 onSurfaceCreated 尚未回调，画面未渲染至屏幕 */
    override fun onBind(item: TestBean, position: Int) {
        Timber.tag(TAG_C).e("onBind: $position -- $item")
        uri = item.url.toUri()
        launch(Dispatchers.IO) {//预缓存
            VideoPlayerPool.preCacheVideo(uri, 5_000)
        }
        VideoPlayerPool.getVideoPlayer(position).let {
            val player = VideoPlayerPool.getVideoPlayer(position)
            player.attachPlayerView(this)   //播放器附加到视图
            player.setRepeatMode(Player.REPEAT_MODE_ALL)   //循环播放
            player.listener = playerStateListener
            player.url = uri      //设置媒体项
            player.prepare()
        }

        binding.item = item
    }


    private val playerStateListener = object : OnPlayerStateListener {
        override fun onStateChange(state: State) {
            if (state == State.Playing) VideoPlayerPool.cancelCacheTask(uri)    //停止预缓存
        }
    }

    override fun onDetachPlayer(position: Int) {
        VideoPlayerPool.getVideoPlayer(position).listener = null
        player = null
    }

    override fun get(): View = this
}