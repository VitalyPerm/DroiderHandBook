package com.elvitalya.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.elvitalya.presentation.core.FragmentKey
import com.elvitalya.presentation.core.createComposeView
import com.elvitalya.presentation.core.lookupBottomSheetBackstack
import com.elvitalya.presentation.screens.SelectAuthMethodScreen
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectAuthMethodKey(val placeholder: Int = 0) : FragmentKey() {
    override fun instantiateFragment(): Fragment = SelectAuthMethodFragment()
}

class SelectAuthMethodFragment : KeyedFragment() {

    private val contentView by lazy { requireContext().lookupBottomSheetBackstack() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = createComposeView(requireContext()).apply {
        setContent {
            SelectAuthMethodScreen { authMethod ->
                contentView.goTo(AuthKey(authMethod))
            }
        }
    }
}