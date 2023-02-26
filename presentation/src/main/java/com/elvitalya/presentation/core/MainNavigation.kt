package com.elvitalya.presentation.core

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.elvitalya.presentation.R

enum class MainNavigation(@StringRes val titleRes: Int, val drawableRes: ImageVector) {
    SECTIONS(R.string.sections, Icons.Filled.List),
    SEARCH(R.string.search, Icons.Filled.Search),
    FAVORITE(R.string.favorite, Icons.Filled.Favorite),
    TEST(R.string.test, Icons.Filled.Book);

    companion object {
        fun fromValue(value: Int?) = when (value) {
            SECTIONS.ordinal -> SECTIONS
            SEARCH.ordinal -> SEARCH
            FAVORITE.ordinal -> FAVORITE
            else -> TEST
        }
    }
}