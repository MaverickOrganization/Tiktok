package com.aking.tiktok.widget

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutParams
import com.aking.data.model.TestBean
import com.aking.tiktok.ui.test.TiktokView

/**
 * Created by Rick at 2023-11-20 20:52.
 * Description: 视频刷适配器
 */
class TiktokVideoAdapter : ListAdapter<TestBean, TiktokViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TiktokViewHolder {
        return TiktokViewHolder(TiktokView(parent.context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        })
    }

    override fun onBindViewHolder(holder: TiktokViewHolder, position: Int) {
        holder.tiktokView.onBind(getItem(position), position)
    }

    override fun onViewRecycled(holder: TiktokViewHolder) {
        super.onViewRecycled(holder)
        holder.tiktokView.player = null
    }

}


class TiktokViewHolder(val tiktokView: TiktokView) : RecyclerView.ViewHolder(tiktokView)


val diffCallback = object : DiffUtil.ItemCallback<TestBean>() {
    override fun areItemsTheSame(oldItem: TestBean, newItem: TestBean) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: TestBean, newItem: TestBean) = oldItem == newItem
}
