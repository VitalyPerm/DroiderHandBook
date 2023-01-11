package com.elvitalya.droiderhandbook.ui.main

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.elvitalya.droiderhandbook.R
import com.elvitalya.droiderhandbook.ui.core.FragmentKey
import com.elvitalya.droiderhandbook.ui.core.FragmentStackHost
import com.elvitalya.droiderhandbook.ui.core.FragmentStackHostFragment
import com.elvitalya.droiderhandbook.ui.favorite.FavoriteKey
import com.elvitalya.droiderhandbook.ui.search.SearchKey
import com.elvitalya.droiderhandbook.ui.sections.SectionsKey
import com.google.accompanist.insets.ProvideWindowInsets
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import com.zhuinden.simplestackextensions.servicesktx.add
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainFlowKey(val placeholder: Int = 0) : FragmentKey() {
    override fun instantiateFragment(): Fragment = MainFlowFragment()
    override fun bindServices(serviceBinder: ServiceBinder) {
        serviceBinder.run {
            add(FragmentStackHost(SectionsKey()), MainNavigation.SECTIONS.name)
            add(FragmentStackHost(SearchKey()), MainNavigation.SEARCH.name)
            add(FragmentStackHost(FavoriteKey()), MainNavigation.FAVORITE.name)
            add(FragmentStackHost(SectionsKey()), MainNavigation.TEST.name)
        }
    }
}

@AndroidEntryPoint
class MainFlowFragment : KeyedFragment() {

    companion object {
        private const val STATE_SELECTED_INDEX = "selectedIndex"
    }

    val viewModel: MainFlowViewModel by viewModels()

    private var selectedIndex = 0

    private lateinit var fragment1: FragmentStackHostFragment
    private lateinit var fragment2: FragmentStackHostFragment
    private lateinit var fragment3: FragmentStackHostFragment
    private lateinit var fragment4: FragmentStackHostFragment

    private lateinit var composeViewWrapper: View
    private lateinit var composeView: ComposeView

    private val fragments
        get() = arrayOf(
            fragment1,
            fragment2,
            fragment3,
            fragment4
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            fragment1 = FragmentStackHostFragment.newInstance(MainNavigation.SECTIONS.name)
            fragment2 = FragmentStackHostFragment.newInstance(MainNavigation.SEARCH.name)
            fragment3 = FragmentStackHostFragment.newInstance(MainNavigation.FAVORITE.name)
            fragment4 = FragmentStackHostFragment.newInstance(MainNavigation.TEST.name)

            childFragmentManager
                .beginTransaction()
                .add(R.id.viewContainer, fragment1, MainNavigation.SECTIONS.name)
                .add(R.id.viewContainer, fragment2, MainNavigation.SEARCH.name)
                .add(R.id.viewContainer, fragment3, MainNavigation.FAVORITE.name)
                .add(R.id.viewContainer, fragment3, MainNavigation.TEST.name)
                .selectFragment(selectedIndex)
                .commit()
        } else {
            selectedIndex = savedInstanceState.getInt(STATE_SELECTED_INDEX, 0)

            fragment1 = childFragmentManager.findFragmentByTag(MainNavigation.SECTIONS.name)
                    as FragmentStackHostFragment

            fragment2 = childFragmentManager.findFragmentByTag(MainNavigation.SEARCH.name)
                    as FragmentStackHostFragment

            fragment3 = childFragmentManager.findFragmentByTag(MainNavigation.FAVORITE.name)
                    as FragmentStackHostFragment

            fragment4 = childFragmentManager.findFragmentByTag(MainNavigation.TEST.name)
                    as FragmentStackHostFragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val array = MainNavigation.values()

        composeViewWrapper = view.findViewById(R.id.composeViewWrapper)
        composeViewWrapper.clipToOutline = true

        composeView = view.findViewById(R.id.composeView)
        composeView.setContent {
            val selectedPosition by viewModel.selectedPosition.collectAsState()
            ProvideWindowInsets {
                MainNavigationTabs(
                    array = array,
                    selectedPosition = selectedPosition.ordinal,
                    onClick = { index ->
                        selectTab(index)
                    }
                )
            }
        }
    }

    private fun selectTab(index: Int) {

        if (index > MainNavigation.values().lastIndex) return

        selectedIndex = index
        viewModel.selectedPosition.value = MainNavigation.fromValue(index)

        childFragmentManager
            .beginTransaction()
            .selectFragment(selectedIndex)
            .commit()
    }


    private fun FragmentTransaction.selectFragment(selectedIndex: Int): FragmentTransaction {
        fragments.forEachIndexed { index, fragment ->
            if (index == selectedIndex) {
                show(fragment)
            } else {
                hide(fragment)
            }

            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        }

        return this
    }

}