package com.elvitalya.droiderhandbook.ui.core

import android.content.Context
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.navigator.Navigator
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentKey
import com.zhuinden.simplestackextensions.servicesktx.lookup

/**
 * Created by uvays on 20.08.2021.
 */

fun Context.lookupBottomSheetBackstack(): Backstack {
    return Navigator.getBackstack(this).lookup(AppBottomSheetView.BACKSTACK_TAG)
}

fun Backstack.canNavigateBack(): Boolean {
    val keys = getHistory<DefaultFragmentKey>()

    // ignore PlaceholderKey if contained in history
    val size = if (keys.contains(PlaceholderKey())) keys.size - 1 else keys.size

    return size > 1
}