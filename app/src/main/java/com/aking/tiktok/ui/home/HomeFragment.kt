package com.aking.tiktok.ui.home

import com.aking.base.base.BaseFragment
import com.aking.data.model.TestBean
import com.aking.player.tiktok.ViewPagerLayoutManager
import com.aking.tiktok.R
import com.aking.tiktok.databinding.FragmentHomeBinding
import com.aking.tiktok.widget.TiktokVideoAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val tiktokAdapter = TiktokVideoAdapter()
    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun FragmentHomeBinding.initView() {
        val pagerLayoutManager = ViewPagerLayoutManager(requireContext())
        rcVideos.run {
            layoutManager = pagerLayoutManager
            adapter = tiktokAdapter
        }

        val list = listOf(
            TestBean(
                1,
                "http://81.71.83.180/video/%E4%B8%8B%E8%BD%BD2.mp4",
                R.drawable.ic_avatar_default,
                R.drawable.ic_avatar_default
            ),
            TestBean(
                2,
                "http://81.71.83.180/video/%E5%B8%85%E7%8C%AB.mp4",
                R.drawable.ic_avatar_default,
                R.drawable.ic_avatar_default
            ), TestBean(
                3,
                "http://81.71.83.180/video/%E4%B8%8B%E8%BD%BD1.mp4",
                R.drawable.ic_avatar_default,
                R.drawable.ic_avatar_default
            ),
            TestBean(
                4,
                "http://81.71.83.180/video/%E4%B8%8B%E8%BD%BD.mp4 ",
                R.drawable.ic_avatar_default,
                R.drawable.ic_avatar_default
            ), TestBean(
                5,
                "https://www.w3school.com.cn/example/html5/mov_bbb.mp4",
                R.drawable.ic_avatar_default,
                R.drawable.ic_avatar_default
            ),
            TestBean(
                6,
                "https://www.w3schools.com/html/movie.mp4 ",
                R.drawable.ic_avatar_default,
                R.drawable.ic_avatar_default
            ), TestBean(
                7,
                "https://tiktop-2023-1320797275.cos.ap-guangzhou.myqcloud.com/storage/emulated/0/Android/data/com.aking.tiktok/cache/1061G_20231101121240_054_0010.mp4",
                R.drawable.ic_avatar_default,
                R.drawable.ic_avatar_default
            ),
            TestBean(
                8,
                "https://tiktop-2023-1320797275.cos.ap-guangzhou.myqcloud.com/storage/emulated/0/Android/data/com.aking.tiktok/cache/1061G_20231101121240_054_0010.mp4 ",
                R.drawable.ic_avatar_default,
                R.drawable.ic_avatar_default
            ),
            TestBean(
                7,
                "https://tiktop-2023-1320797275.cos.ap-guangzhou.myqcloud.com/storage/emulated/0/Android/data/com.aking.tiktok/cache/1061G_20231101121240_054_0010.mp4",
                R.drawable.ic_avatar_default,
                R.drawable.ic_avatar_default
            ),
            TestBean(
                8,
                "https://tiktop-2023-1320797275.cos.ap-guangzhou.myqcloud.com/storage/emulated/0/Android/data/com.aking.tiktok/cache/1061G_20231101121240_054_0010.mp4 ",
                R.drawable.ic_avatar_default,
                R.drawable.ic_avatar_default
            )
        )

        tiktokAdapter.submitList(list)
    }
}