package com.elvitalya.droiderhandbook.navigation

import androidx.annotation.StringRes
import com.elvitalya.droiderhandbook.R


sealed class BottomNavigationScreen(val route: String, @StringRes val resourceId: Int) {
    object Sections : BottomNavigationScreen(NavConstants.SECTIONS, R.string.sections)
    object Favorite : BottomNavigationScreen(NavConstants.FAVORITE, R.string.favorite)
    object Search : BottomNavigationScreen(NavConstants.SEARCH, R.string.search)
    object Test : BottomNavigationScreen(NavConstants.TEST, R.string.test)
}