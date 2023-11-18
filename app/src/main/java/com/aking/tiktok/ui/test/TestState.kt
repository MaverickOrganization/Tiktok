package com.aking.tiktok.ui.test

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized
import com.aking.data.model.Friend
import com.aking.data.model.User

/**
 * Created by Rick at 2023-11-18 20:28.
 * Description:
 */
data class TestState(
    val count: Int = 0,
    val friend: Friend = Friend(1, 1),
    val data: Async<User> = Uninitialized,
) : MavericksState