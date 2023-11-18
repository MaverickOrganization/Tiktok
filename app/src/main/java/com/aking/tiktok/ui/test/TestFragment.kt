package com.aking.tiktok.ui.test

import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.aking.base.base.BaseFragment
import com.aking.tiktok.R
import com.aking.tiktok.databinding.FragmentTestBinding

/**
 * Created by Rick at 2023-11-18 20:27.
 * Description:
 */
class TestFragment : BaseFragment<FragmentTestBinding>(), MavericksView {
    private val vm: TestViewModel by fragmentViewModel()

    override fun getLayoutId(): Int = R.layout.fragment_test

    override fun FragmentTestBinding.initView() {
        button.setOnClickListener {
            vm.incrementCount()
        }

        vm.onEach(TestState::count) {
            binding.textView2.text = "${it}"
        }
    }

    override fun invalidate() {
        withState(vm) {
            when (it.data) {
                is Loading -> {}
                is Success -> {}
                is Fail -> {}
                else -> {}
            }
        }
    }
}