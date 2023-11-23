package com.aking.tiktok.ui.test

import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.args
import com.airbnb.mvrx.fragmentViewModel
import com.aking.base.base.BaseFragment
import com.aking.data.model.TestBean
import com.aking.tiktok.R
import com.aking.tiktok.databinding.LayoutVideoContentBinding

/**
 * Created by Rick at 2023-11-18 20:27.
 * Description:
 */
class TestFragment : BaseFragment<LayoutVideoContentBinding>(), MavericksView {

    private val vm: TestViewModel by fragmentViewModel()
    private val arg: TestBean by args()

    init {
        lifecycleLogEnable(true)
    }

    override fun getLayoutId(): Int = R.layout.exo_player_view


    override fun invalidate() {

    }

}