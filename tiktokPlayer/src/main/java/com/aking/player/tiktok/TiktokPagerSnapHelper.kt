package com.aking.player.tiktok

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

/**
 * Created by Rick at 2023-11-22 14:17.
 * Description: 松手之后，会有一个加速滑动，到终点之前又逐渐减速的过程
 */
open class TiktokPagerSnapHelper(private val context: Context) : PagerSnapHelper() {
    companion object {
        private const val MILLISECONDS_PER_INCH = 100f
        private const val MAX_SCROLL_ON_FLING_DURATION = 120
    }

    // 减速插值器
    private val interpolator = DecelerateInterpolator(2.1f)

    override fun createScroller(layoutManager: RecyclerView.LayoutManager): RecyclerView.SmoothScroller? {
        if (layoutManager !is RecyclerView.SmoothScroller.ScrollVectorProvider) {
            return null
        }
        return object : LinearSmoothScroller(context) {
            override fun onTargetFound(targetView: View, state: RecyclerView.State, action: Action) {
                val snapDistances = calculateDistanceToFinalSnap(layoutManager, targetView) ?: intArrayOf()
                val dx = snapDistances[0]
                val dy = snapDistances[1]
                val time = calculateTimeForDeceleration(abs(dx).coerceAtLeast(abs(dy)));
                if (time > 0) {
                    // 使用自定义插值器
                    action.update(dx, dy, time, interpolator)
                }
            }

            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return MILLISECONDS_PER_INCH / displayMetrics.densityDpi
            }

            override fun calculateTimeForScrolling(dx: Int): Int {
                return MAX_SCROLL_ON_FLING_DURATION.coerceAtMost(super.calculateTimeForScrolling(dx))
            }
        }
    }

}