package com.aking.tiktok.widget

import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.aking.base.extended.TAG_C
import com.aking.base.widget.CommonViewHolder
import com.aking.base.widget.inflater
import com.aking.data.model.TestBean
import com.aking.tiktok.databinding.ItemPageVideoBinding
import timber.log.Timber

/**
 * Created by Rick at 2023-11-20 20:52.
 * Description:
 */
class VideoPagerAdapter : ListAdapter<TestBean, VideoViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(ItemPageVideoBinding.inflate(parent.inflater(), parent, false))
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(getItem(position))
        val player = holder.player
        Timber.tag(TAG_C).e("onBindViewHolder: $position")
        holder.getBind<ItemPageVideoBinding>().run {
            //test
            val mediaItem = MediaItem.fromUri(getItem(position).url)
            // Set the media item to be played.
            player.setMediaItem(mediaItem)
            // Prepare the player.
            player.prepare()
        }
    }
}


class VideoViewHolder(binding: ItemPageVideoBinding) : CommonViewHolder(binding) {
    val player = ExoPlayer.Builder(binding.root.context).build()

    init {
        // Attach player to the view.
        binding.playerView.player = player
        //循环播放
        player.repeatMode = Player.REPEAT_MODE_ALL
    }
}

val diffCallback = object : DiffUtil.ItemCallback<TestBean>() {
    override fun areItemsTheSame(oldItem: TestBean, newItem: TestBean) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: TestBean, newItem: TestBean) = oldItem == newItem
}