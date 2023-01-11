package com.elvitalya.droiderhandbook.ui.core

import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentKey
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider

abstract class FragmentKey : DefaultFragmentKey(), DefaultServiceProvider.HasServices {
    override fun getFragmentTag(): String = toString()
    override fun getScopeTag(): String = fragmentTag
    override fun bindServices(serviceBinder: ServiceBinder) {}
}