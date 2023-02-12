package com.elvitalya.droiderhandbook.presentation.core

import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.Bundleable
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.ScopedServices
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider
import com.zhuinden.statebundle.StateBundle

class FragmentStackHost(initialKey: Any) : Bundleable, ScopedServices.HandlesBack {

    var isActiveForBack: Boolean = false

    val backstack = Backstack()

    init {
        backstack.setScopedServices(DefaultServiceProvider())
        backstack.setup(History.of(initialKey))
    }

    override fun toBundle(): StateBundle = StateBundle().apply {
        putParcelable("BACKSTACK_STATE", backstack.toBundle())
    }

    override fun fromBundle(bundle: StateBundle?) {
        bundle?.run {
            backstack.fromBundle(getParcelable("BACKSTACK_STATE"))
        }
    }

    override fun onBackEvent(): Boolean {
        if (isActiveForBack) {
            return backstack.goBack()
        }

        return false
    }
}