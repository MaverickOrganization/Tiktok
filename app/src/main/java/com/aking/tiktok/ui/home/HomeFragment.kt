package com.aking.tiktok.ui.home

import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.aking.base.base.BaseFragment
import com.aking.data.model.TestBean
import com.aking.tiktok.R
import com.aking.tiktok.databinding.FragmentHomeBinding
import com.aking.tiktok.widget.VideoPagerAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun FragmentHomeBinding.initView() {
        val pagerAdapter = VideoPagerAdapter(this@HomeFragment)
        (pager[0] as RecyclerView).setItemViewCacheSize(1)
        pager.orientation = ViewPager2.ORIENTATION_VERTICAL
        pager.adapter = pagerAdapter
        val list = listOf(
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
            ), TestBean(
                3,
                "https://media.w3.org/2010/05/sintel/trailer.mp4",
                R.drawable.ic_avatar_default,
                R.drawable.ic_avatar_default
            ),
            TestBean(
                4,
                "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4 ",
                R.drawable.ic_avatar_default,
                R.drawable.ic_avatar_default
            ), TestBean(
                5,
                "https://media.w3.org/2010/05/sintel/trailer.mp4",
                R.drawable.ic_avatar_default,
                R.drawable.ic_avatar_default
            ),
            TestBean(
                6,
                "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4 ",
                R.drawable.ic_avatar_default,
                R.drawable.ic_avatar_default
            ), TestBean(
                7,
                "https://media.w3.org/2010/05/sintel/trailer.mp4",
                R.drawable.ic_avatar_default,
                R.drawable.ic_avatar_default
            ),
            TestBean(
                8,
                "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4 ",
                R.drawable.ic_avatar_default,
                R.drawable.ic_avatar_default
            ),
            TestBean(
                7,
                "https://media.w3.org/2010/05/sintel/trailer.mp4",
                R.drawable.ic_avatar_default,
                R.drawable.ic_avatar_default
            ),
            TestBean(
                8,
                "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4 ",
                R.drawable.ic_avatar_default,
                R.drawable.ic_avatar_default
            )
        )

        pagerAdapter.submitList(list)
    }
}