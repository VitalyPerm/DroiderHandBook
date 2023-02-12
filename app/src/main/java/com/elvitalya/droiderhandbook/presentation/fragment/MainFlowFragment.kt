package com.elvitalya.droiderhandbook.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.elvitalya.droiderhandbook.R
import com.elvitalya.droiderhandbook.presentation.core.FragmentKey
import com.elvitalya.droiderhandbook.presentation.core.FragmentStackHost
import com.elvitalya.droiderhandbook.presentation.core.FragmentStackHostFragment
import com.elvitalya.droiderhandbook.presentation.core.MainNavigation
import com.elvitalya.droiderhandbook.presentation.viewmodel.MainFlowViewModel
import com.elvitalya.droiderhandbook.ui.main.MainNavigationTabs
import com.google.accompanist.insets.ProvideWindowInsets
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import com.zhuinden.simplestackextensions.servicesktx.add
import kotlinx.parcelize.Parcelize
import org.koin.androidx.viewmodel.ext.android.viewModel

@Parcelize
data class MainFlowKey(val placeholder: Int = 0) : FragmentKey() {
    override fun instantiateFragment(): Fragment = MainFlowFragment()
    override fun bindServices(serviceBinder: ServiceBinder) {
        serviceBinder.run {
            add(FragmentStackHost(SectionsKey()), MainNavigation.SECTIONS.name)
            add(FragmentStackHost(SearchKey()), MainNavigation.SEARCH.name)
            add(FragmentStackHost(FavoriteKey()), MainNavigation.FAVORITE.name)
            add(FragmentStackHost(TestKey()), MainNavigation.TEST.name)
        }
    }
}

class MainFlowFragment : KeyedFragment(R.layout.fragment_main_flow) {

    private val viewModel by viewModel<MainFlowViewModel>()

    private lateinit var sectionsFragment: FragmentStackHostFragment
    private lateinit var searchFragment: FragmentStackHostFragment
    private lateinit var favoriteFragment: FragmentStackHostFragment
    private lateinit var testFragment: FragmentStackHostFragment

    private lateinit var composeViewWrapper: View
    private lateinit var composeView: ComposeView

    private val fragments
        get() = arrayOf(
            sectionsFragment,
            searchFragment,
            favoriteFragment,
            testFragment
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sectionsFragment = FragmentStackHostFragment.newInstance(MainNavigation.SECTIONS.name)
        searchFragment = FragmentStackHostFragment.newInstance(MainNavigation.SEARCH.name)
        favoriteFragment = FragmentStackHostFragment.newInstance(MainNavigation.FAVORITE.name)
        testFragment = FragmentStackHostFragment.newInstance(MainNavigation.TEST.name)

        childFragmentManager
            .beginTransaction()
            .add(R.id.viewContainer, sectionsFragment, MainNavigation.SECTIONS.name)
            .add(R.id.viewContainer, searchFragment, MainNavigation.SEARCH.name)
            .add(R.id.viewContainer, favoriteFragment, MainNavigation.FAVORITE.name)
            .add(R.id.viewContainer, testFragment, MainNavigation.TEST.name)
            .selectFragment(viewModel.selectedPosition.value.ordinal)
            .commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navTabs = MainNavigation.values()

        composeViewWrapper = view.findViewById(R.id.composeViewWrapper)
        composeViewWrapper.clipToOutline = true

        composeView = view.findViewById(R.id.composeView)
        composeView.setContent {
            val selectedPosition by viewModel.selectedPosition.collectAsState()
            ProvideWindowInsets {
                MainNavigationTabs(
                    array = navTabs,
                    selectedPosition = selectedPosition.ordinal,
                    onClick = ::selectTab
                )
            }
        }
    }

    private fun selectTab(index: Int) {

        if (index > MainNavigation.values().lastIndex) return

        viewModel.selectedPosition.value = MainNavigation.fromValue(index)

        childFragmentManager
            .beginTransaction()
            .selectFragment(viewModel.selectedPosition.value.ordinal)
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