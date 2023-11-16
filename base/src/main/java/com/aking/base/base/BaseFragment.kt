package com.aking.base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.databinding.ViewDataBinding
import com.aking.base.extended.binding

/**
 * Created by Rick on 2023-01-30  20:22.
 * Description:
 */
abstract class BaseFragment<V : ViewDataBinding> : BaseLifecycleFragment() {

    protected val binding: V by binding()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    @LayoutRes
    protected abstract fun getLayoutId(): Int
    protected open fun V.initView() {}
    protected open fun V.initObservable() {}

}