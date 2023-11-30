package com.aking.player.tiktok

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutParams
import com.aking.player.features.IPlayerView

/**
 * Created by Rick at 2023-11-20 20:52.
 * Description: 视频刷适配器
 */
abstract class BaseTiktokVideoAdapter<T>(diffCallback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, BaseTiktokVideoAdapter<T>.DefaultTiktokViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultTiktokViewHolder {
        return DefaultTiktokViewHolder(createPlayerView(parent.context)).apply {
            playerView.get().layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }
    }

    override fun onBindViewHolder(holder: DefaultTiktokViewHolder, position: Int) {
        holder.onBind(getItem(position), position)
    }

    override fun onViewRecycled(holder: DefaultTiktokViewHolder) {
        super.onViewRecycled(holder)
        holder.onDetachPlayer(holder.absoluteAdapterPosition)
    }

    /**
     * 创建播放器视图，需实现[IPlayerView]接口
     */
    protected abstract fun createPlayerView(context: Context): IPlayerView<T>

    /**
     * 播放器视图持有者
     */
    inner class DefaultTiktokViewHolder(val playerView: IPlayerView<T>) :
        RecyclerView.ViewHolder(playerView.get()), IPlayerView<T> by playerView
}


