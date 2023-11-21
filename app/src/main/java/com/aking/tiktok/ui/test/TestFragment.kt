package com.aking.tiktok.ui.test

import android.os.Bundle
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.args
import com.airbnb.mvrx.fragmentViewModel
import com.aking.base.app
import com.aking.base.base.BaseFragment
import com.aking.data.model.TestBean
import com.aking.tiktok.BR
import com.aking.tiktok.R
import com.aking.tiktok.databinding.FragmentTestBinding
import timber.log.Timber

/**
 * Created by Rick at 2023-11-18 20:27.
 * Description:
 */
class TestFragment : BaseFragment<FragmentTestBinding>(), MavericksView {
    private val vm: TestViewModel by fragmentViewModel()
    private val arg: TestBean by args()
    private val player = ExoPlayer.Builder(app).build()

    init {
        lifecycleLogEnable(true)
    }

    override fun getLayoutId(): Int = R.layout.fragment_test

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //循环播放
        player.repeatMode = Player.REPEAT_MODE_ALL
        // Set the media item to be played.
        val mediaItem = MediaItem.fromUri(arg.url)
        player.setMediaItem(mediaItem)
        // Prepare the player.
        player.prepare()
    }

    override fun FragmentTestBinding.initView() {
        Timber.e("initView: $arg")
        // Attach player to the view.
        playerView.player = player
        setVariable(BR.item, arg)
    }

    override fun onResume() {
        super.onResume()
        player.play()
    }

    override fun onPause() {
        super.onPause()
        player.pause()
    }

    override fun invalidate() {

    }

}