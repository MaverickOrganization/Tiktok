package com.aking.base.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.databinding.ViewDataBinding
import com.aking.base.extended.contentView

/**
 * Created by Rick on 2023-01-30  19:24.
 * Description:
 */
abstract class BaseActivity<V : ViewDataBinding>(@LayoutRes layoutRes: Int) : BaseLifecycleActivity() {

    protected val binding: V by contentView(layoutRes)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.initView()
        binding.initObservable()
    }

    /**
     * 绑定布局变量
     */
    @MainThread
    fun bindVariables(vararg args: Pair<Int, Any>) {
        for (arg in args) {
            binding.setVariable(arg.first, arg.second)
        }
        binding.executePendingBindings()
    }

    protected open fun V.initView() {}
    protected open fun V.initObservable() {}

}
