package com.elvitalya.droiderhandbook.ui.core

import androidx.fragment.app.Fragment
import com.elvitalya.droiderhandbook.R
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import kotlinx.parcelize.Parcelize

/**
 * Created by uvays on 19.11.2021.
 */

@Parcelize
data class PlaceholderKey(val placeholder: Int = 0) : FragmentKey() {
    override fun instantiateFragment(): Fragment = PlaceholderFragment()
}

class PlaceholderFragment : KeyedFragment(R.layout.fragment_placeholder)