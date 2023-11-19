package com.aking.tiktok.ui.profile

import com.airbnb.mvrx.MavericksState
import com.aking.base.base.BaseViewModel

data class ProfileState(val name: String = "") : MavericksState

class ProfileViewModel(state: ProfileState) : BaseViewModel<ProfileState>(state) {

}