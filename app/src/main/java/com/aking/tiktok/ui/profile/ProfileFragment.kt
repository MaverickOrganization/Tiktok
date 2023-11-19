package com.aking.tiktok.ui.profile

import androidx.recyclerview.widget.RecyclerView
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.aking.base.base.BaseFragment
import com.aking.tiktok.R
import com.aking.tiktok.databinding.FragmentProfileBinding

/**
 * Fragment that demonstrates a responsive layout pattern where the format of the content
 * transforms depending on the size of the screen. Specifically this Fragment shows items in
 * the [RecyclerView] using LinearLayoutManager in a small screen
 * and shows items using GridLayoutManager in a large screen.
 */
class ProfileFragment : BaseFragment<FragmentProfileBinding>(), MavericksView {
    private val vm: ProfileViewModel by fragmentViewModel()
    override fun getLayoutId(): Int = R.layout.fragment_profile
    override fun invalidate() {

    }

}