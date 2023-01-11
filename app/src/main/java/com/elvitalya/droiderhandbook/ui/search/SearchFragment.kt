package com.elvitalya.droiderhandbook.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.elvitalya.droiderhandbook.ui.core.FragmentKey
import com.elvitalya.droiderhandbook.ui.core.createComposeView
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchKey(val placeholder: Int = 0) : FragmentKey() {
    override fun instantiateFragment(): Fragment = SearchFragment()
}

class SearchFragment : KeyedFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = createComposeView(requireContext()).apply {
        setContent {
            SearchScreen()
        }
    }
}