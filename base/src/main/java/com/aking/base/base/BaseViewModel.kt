package com.aking.base.base

import com.airbnb.mvrx.BuildConfig
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelConfigFactory
import com.aking.base.extended.TAG_C

/**
 * Created by Rick at 2023/7/9 15:11.
 * Description: ViewModel层基类
 */
abstract class BaseViewModel<S : MavericksState>(
    initialState: S,
    configFactory: MavericksViewModelConfigFactory = MavericksViewModelConfigFactory(BuildConfig.DEBUG),
) : MavericksViewModel<S>(initialState, configFactory) {
    protected val TAG = TAG_C
}