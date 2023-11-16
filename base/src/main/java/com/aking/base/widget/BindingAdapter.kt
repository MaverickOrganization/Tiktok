package com.aking.base.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.aking.base.extended.dp
import com.aking.base.utils.OnDebouncingClickListener
import com.aking.base.utils.applyDebouncing
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

/**
 * DataBinding适配器
 */

@BindingAdapter(value = ["adapter"], requireAll = false)
fun RecyclerView.bindAdapter(adapter: RecyclerView.Adapter<*>) {
    this.adapter = adapter
}

@BindingAdapter(value = ["submitList"], requireAll = false)
fun RecyclerView.submitList(list: List<Any>?) {
    adapter ?: return
    (adapter as? ListAdapter<Any, *>)?.submitList(list)
}

@BindingAdapter(value = ["selected"], requireAll = false)
fun View.bindSelect(select: Boolean) {
    this.isSelected = select
}

@BindingAdapter(value = ["textScroll"], requireAll = false)
fun TextView.bindTextScroll(isScroll: Boolean) {
    if (isScroll) {
        this.movementMethod = ScrollingMovementMethod.getInstance()
    }
}

@BindingAdapter(value = ["adjustWidth"])
fun View.bindAdjustWidth(adjustWidth: Int) {
    val params = this.layoutParams
    params.width = adjustWidth
    this.layoutParams = params
}

@BindingAdapter(value = ["adjustHeight"])
fun View.bindAdjustHeight(adjustHeight: Int) {
    val params = this.layoutParams
    params.height = adjustHeight
    this.layoutParams = params
}

@BindingAdapter(value = ["onTouchListener"])
fun View.bindOnTouchListener(onTouchListener: View.OnTouchListener) {
    this.setOnTouchListener(onTouchListener)
}

@BindingAdapter(value = ["bottomDecoration"], requireAll = false)
fun RecyclerView.bindBottomDecoration(dp: Int) {
    this.addItemDecoration(object : ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State,
        ) {
            outRect.bottom = dp.dp.toInt()
        }
    })
}

@BindingAdapter(value = ["itemDecoration"], requireAll = false)
fun RecyclerView.bindItemDecoration(dp: Int) {
    this.addItemDecoration(object : ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State,
        ) {
            val size = dp.dp.toInt()
            outRect.bottom = size
            outRect.top = size
            outRect.right = size
            outRect.left = size
        }
    })
}

/**
 * 是否显示
 */
@BindingAdapter("showIf")
fun View.bindShowIf(show: Boolean) {
    visibility = if (show) {
        VISIBLE
    } else {
        GONE
    }
}

/**
 * 点击防抖
 */
@BindingAdapter(
    "debouncingClick",
    "debouncingDuration",
    "debouncingWithEnable",
    "debouncingCountDown",
    requireAll = false
)
fun View.bindClickDebouncing(
    clickListener: View.OnClickListener,
    duration: Long,
    withEnable: Boolean,
    countdownCallback: OnDebouncingClickListener.OnCountdownCallback? = null,
) {
    this.applyDebouncing(
        clickListener,
        if (duration == 0L) OnDebouncingClickListener.DEBOUNCING_DEFAULT_VALUE else duration,
        withEnable,
        countdownCallback
    )
}

//淡入效果
val fadeOptions = DrawableTransitionOptions().crossFade()

@BindingAdapter(
    "glideUrl",
    "glidePlaceholder",
    "glideError",
    "glideCenterCrop",
    "glideCircularCrop",
    "glideBlock",
    requireAll = false
)
fun ImageView.bindGlideUrl(
    url: String,
    placeholder: Int? = null,
    error: Int? = null,
    centerCrop: Boolean = false,
    circularCrop: Boolean = false,
    block: ((RequestBuilder<Drawable>) -> Unit)? = null,
) {
    /* Glide加载 */
    val glideRequest = createGlideRequest(
        context,
        url,
        placeholder,
        error,
        centerCrop,
        circularCrop
    )
    block?.invoke(glideRequest)
    glideRequest.into(this)
}

@SuppressLint("CheckResult")
fun createGlideRequest(
    context: Context,
    url: String,
    placeholder: Int? = null,
    error: Int? = null,
    centerCrop: Boolean,
    circularCrop: Boolean,
): RequestBuilder<Drawable> {
    val req = Glide.with(context).load(url).transition(fadeOptions)
    placeholder?.let { req.placeholder(it) }
    error?.let { req.error(it) }
    if (centerCrop) req.centerCrop()
    if (circularCrop) req.circleCrop()
    return req
}


@BindingAdapter("onTouch")
fun setOnTouchListener(view: View, listener: View.OnTouchListener) {
    view.setOnTouchListener(listener)
}


@BindingAdapter(
    value = ["topSystemWindowInsets", "bottomSystemWindowInsets", "isInsetsMargin", "insetsValue", "windowInsetClear"],
    requireAll = false
)
fun View.bindSystemWindowInsets(
    topInsets: Boolean,
    bottomInsets: Boolean,
    isInsetsMargin: Boolean = false,
    insetsValue: Int = 0,
    windowInsetClear: Boolean,
) {
    setOnApplyWindowInsetsListener { v, insets ->
        WindowInsetsCompat.toWindowInsetsCompat(insets, v).also {
            if (windowInsetClear) return@setOnApplyWindowInsetsListener insets
            if (isInsetsMargin) it.insetsMargin(v, topInsets, bottomInsets, insetsValue.dp.toInt())
            else it.insetsPadding(v, topInsets, bottomInsets, insetsValue.dp.toInt())
        }
        insets
    }
}

private fun WindowInsetsCompat.insetsPadding(
    v: View,
    topInsets: Boolean,
    bottomInsets: Boolean,
    insetsValue: Int,
) {
    val top =
        if (topInsets) getInsets(WindowInsetsCompat.Type.statusBars()).top + insetsValue else v.paddingTop
    val bottom =
        if (bottomInsets) getInsets(WindowInsetsCompat.Type.navigationBars()).bottom + insetsValue else v.paddingBottom
    if (topInsets || bottomInsets) v.updatePadding(top = top, bottom = bottom)
}

private fun WindowInsetsCompat.insetsMargin(
    v: View,
    topInsets: Boolean,
    bottomInsets: Boolean,
    insetsValue: Int,
) {
    val top =
        if (topInsets) getInsets(WindowInsetsCompat.Type.statusBars()).top + insetsValue else v.marginTop
    val bottom =
        if (bottomInsets) getInsets(WindowInsetsCompat.Type.navigationBars()).bottom + insetsValue else v.marginBottom
    if (topInsets || bottomInsets) v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
        topMargin = top
        bottomMargin = bottom
    }
}