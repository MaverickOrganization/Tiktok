package com.aking.tiktok.widget

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import com.aking.data.model.TestBean
import com.aking.player.features.IPlayerView
import com.aking.player.tiktok.BaseTiktokVideoAdapter
import com.aking.tiktok.views.TiktokView

/**
 * Created by Rick at 2023-11-20 20:52.
 * Description: 视频刷适配器
 */
class TiktokVideoAdapter : BaseTiktokVideoAdapter<TestBean>(diffCallback) {
    override fun createPlayerView(context: Context): IPlayerView<TestBean> {
        return TiktokView(context)
    }
}


val diffCallback = object : DiffUtil.ItemCallback<TestBean>() {
    override fun areItemsTheSame(oldItem: TestBean, newItem: TestBean) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: TestBean, newItem: TestBean) = oldItem == newItem
}
