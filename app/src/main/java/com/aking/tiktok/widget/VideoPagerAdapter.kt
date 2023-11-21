package com.aking.tiktok.widget

import android.util.Log
import androidx.collection.LongSparseArray
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.airbnb.mvrx.asMavericksArgs
import com.aking.base.extended.TAG_C
import com.aking.data.model.TestBean
import com.aking.tiktok.ui.test.TestFragment

/**
 * Created by Rick at 2023-11-20 20:52.
 * Description:
 */
class VideoPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private var mCurrentList: List<TestBean> = mutableListOf()
    private var mFragments = LongSparseArray<TestFragment>()
    override fun getItemCount(): Int = mCurrentList.size

    override fun onBindViewHolder(holder: FragmentViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        Log.e("TestFragment", "onBindViewHolder: ${holder.hashCode()} --- $holder")
    }

    override fun createFragment(position: Int): Fragment {
        if (mFragments.size() == Config.LOOP_FRAGMENT_SIZE) {
            val fragment = mFragments[position % Config.LOOP_FRAGMENT_SIZE.toLong()] as Fragment
            fragment.arguments = mCurrentList[position].asMavericksArgs()
            return fragment
        }
        return TestFragment().apply {
            Log.e("TestFragment", "createFragment: ${this.TAG_C}")
            arguments = mCurrentList[position].asMavericksArgs()
            mFragments.put(getItemId(position), this)
        }
    }

    fun submitList(videoList: List<TestBean>?) {
        mCurrentList = videoList ?: mutableListOf()
    }
}
