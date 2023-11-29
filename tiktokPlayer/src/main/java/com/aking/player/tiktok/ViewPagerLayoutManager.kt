package com.aking.player.tiktok

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Rick at 2023-11-22 14:41.
 * Description:
 */
open class ViewPagerLayoutManager(
    context: Context,
    orientation: Int = RecyclerView.VERTICAL,
    reverseLayout: Boolean = false,
    listener: OnViewPagerListener = DefaultPagerChangeListener()
) : LinearLayoutManager(context, orientation, reverseLayout) {

    private val mPagerSnapHelper: PagerSnapHelper = TiktokPagerSnapHelper(context)
    private var mOnViewPagerListener: OnViewPagerListener? = listener
    private var mDrift = 0
    private var mCurrent = 0
    private lateinit var mRecyclerView: RecyclerView

    private val isNext get() = mDrift >= 0

    private val mChildAttachStateChangeListener = object : RecyclerView.OnChildAttachStateChangeListener {
        override fun onChildViewAttachedToWindow(view: View) {
            //页面进入屏幕，1px就会触发
            mOnViewPagerListener?.onAttach(isNext, getPosition(view), view)

            if (childCount == 1) {
                val currentIndex = getPosition(view)
                mOnViewPagerListener?.onPageSelected(isNext, currentIndex, view)
            }
        }

        override fun onChildViewDetachedFromWindow(view: View) {
            //页面离开了屏幕
            mOnViewPagerListener?.onDetach(isNext, getPosition(view), view)
        }
    }

    override fun onAttachedToWindow(view: RecyclerView) {
        view.setItemViewCacheSize(1)    //调整可重用的缓存数量
        this.mRecyclerView = view
        this.mRecyclerView.addOnChildAttachStateChangeListener(mChildAttachStateChangeListener)
        super.onAttachedToWindow(view)

        this.mPagerSnapHelper.attachToRecyclerView(view)
        this.mOnViewPagerListener?.onInitComplete()
    }


    override fun onScrollStateChanged(state: Int) {
        when (state) {
            RecyclerView.SCROLL_STATE_IDLE -> { //（空闲）
                val viewIdle = mPagerSnapHelper.findSnapView(this) ?: return
                val currentIndex = getPosition(viewIdle)
                //页面被选中，完全进入
                if (mCurrent != currentIndex) {
                    mCurrent = currentIndex
                    mOnViewPagerListener?.onPageSelected(isNext, currentIndex, viewIdle)
                }
            }

            RecyclerView.SCROLL_STATE_DRAGGING -> { //（拖动）
            }

            RecyclerView.SCROLL_STATE_SETTLING -> { //（要移动到最后位置时）
            }
        }
    }

    override fun scrollVerticallyBy(
        dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?
    ): Int {
        this.mDrift = dy
        return super.scrollVerticallyBy(dy, recycler, state)
    }

    override fun scrollHorizontallyBy(
        dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?
    ): Int {
        this.mDrift = dx
        return super.scrollHorizontallyBy(dx, recycler, state)
    }

    fun setOnViewPagerListener(listener: OnViewPagerListener) {
        this.mOnViewPagerListener = listener
    }


    interface OnViewPagerListener {
        fun onInitComplete() {}
        fun onPageSelected(isNext: Boolean, position: Int, view: View) {}

        fun onAttach(isNext: Boolean, position: Int, view: View) {}
        fun onDetach(isNext: Boolean, position: Int, view: View) {}
    }
}