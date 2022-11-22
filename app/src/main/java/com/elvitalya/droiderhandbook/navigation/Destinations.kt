package com.elvitalya.droiderhandbook.navigation

import androidx.annotation.StringRes
import com.elvitalya.droiderhandbook.R

sealed class Destinations(val route: String, @StringRes val resourceId: Int) {
    object QuestionDetail : Destinations(
        route = "${NavConstants.QUESTION}/{${NavConstants.QUESTION_ID}}",
        resourceId = R.string.details
    ) {
        fun createRoute(id: Int) = "${NavConstants.QUESTION}/$id"
    }

    object ColorsScreen : Destinations(
        route = NavConstants.COLORS,
        resourceId = R.string.colors
    )
}