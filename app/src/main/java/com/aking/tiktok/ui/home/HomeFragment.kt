package com.aking.tiktok.ui.home

import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.aking.base.base.BaseFragment
import com.aking.data.model.TestBean
import com.aking.tiktok.R
import com.aking.tiktok.databinding.FragmentHomeBinding
import com.aking.tiktok.widget.VideoPagerAdapter
import com.aking.tiktok.widget.VideoViewHolder

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun FragmentHomeBinding.initView() {
        val pagerAdapter = VideoPagerAdapter()
        pager.orientation = ViewPager2.ORIENTATION_VERTICAL
        pager.adapter = pagerAdapter
        pagerAdapter.submitList(
            listOf(
                TestBean(
                    1,
                    "https://media.w3.org/2010/05/sintel/trailer.mp4",
                    R.drawable.ic_avatar_default,
                    R.drawable.ic_avatar_default
                ),
                TestBean(
                    2,
                    "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4 ",
                    R.drawable.ic_avatar_default,
                    R.drawable.ic_avatar_default
                )
            )
        )
    }

    override fun FragmentHomeBinding.initObservable() {
        pager.post {
            var currentHolder = (pager[0] as RecyclerView).findViewHolderForAdapterPosition(0) as VideoViewHolder
            currentHolder.player.play()
            pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    currentHolder.player.pause()
                    currentHolder =
                        (pager[0] as RecyclerView).findViewHolderForAdapterPosition(position) as VideoViewHolder
                    currentHolder.player.play()
                }
            })
        }
    }
}