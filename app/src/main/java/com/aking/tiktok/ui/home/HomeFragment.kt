package com.aking.tiktok.ui.home

import android.util.Log
import android.view.View
import com.aking.base.base.BaseFragment
import com.aking.data.model.TestBean
import com.aking.tiktok.R
import com.aking.tiktok.databinding.FragmentHomeBinding
import com.aking.tiktok.ui.test.TiktokView
import com.aking.tiktok.ui.test.VideoPlayerPool
import com.aking.tiktok.ui.test.ViewPagerLayoutManager
import com.aking.tiktok.widget.TiktokVideoAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val tiktokAdapter = TiktokVideoAdapter()
    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun FragmentHomeBinding.initView() {
        val pagerLayoutManager = ViewPagerLayoutManager(requireContext())
        rcVideos.run {
            setItemViewCacheSize(1)
            layoutManager = pagerLayoutManager
            adapter = tiktokAdapter
        }

        pagerLayoutManager.setOnViewPagerListener(object : ViewPagerLayoutManager.OnViewPagerListener {
            override fun onAttach(isNext: Boolean, position: Int, view: View) {
                /* 注意：由于在 onBindViewHolder 期间已解码完成，这里只需要进入屏幕 1px，
                  就会立即触发 Surface 的绘制（只会执行一次），即进入窗口的内容会显示视频的首帧画面 */
            }

            override fun onDetach(isNext: Boolean, position: Int, view: View) {
                //控制即将移除屏幕的播放器暂停，并 seekTo (0)，方便滑回屏幕时立即播放
                Log.e(TAG, "onDetach: $position")
                if (view is TiktokView) {
                    VideoPlayerPool.getVideoPlayer(position).run {
                        pause()
                        seekTo(0)
                    }
                }
            }

            /*控制当前屏幕的播放器开始播放。*/
            override fun onPageSelected(isNext: Boolean, position: Int, view: View) {
                Log.e(TAG, "onPageSelected: $position")
                VideoPlayerPool.getVideoPlayer(position).play()
            }
        })

        val list = listOf(
            TestBean(
                1,
                "https://tiktop-2023-1320797275.cos.ap-guangzhou.myqcloud.com/storage/emulated/0/Android/data/com.aking.tiktok/cache/1061G_20231101121240_054_0010.mp4",
                R.drawable.ic_avatar_default,
                R.drawable.ic_avatar_default
            ),
            TestBean(
                2,
                "https://tiktop-2023-1320797275.cos.ap-guangzhou.myqcloud.com/storage/emulated/0/Android/data/com.aking.tiktok/cache/1061G_20231101121240_054_0010.mp4",
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
                "https://tiktop-2023-1320797275.cos.ap-guangzhou.myqcloud.com/storage/emulated/0/Android/data/com.aking.tiktok/cache/1061G_20231101121240_054_0010.mp4 ",
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