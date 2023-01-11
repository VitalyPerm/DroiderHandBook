package com.elvitalya.droiderhandbook.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.elvitalya.droiderhandbook.ui.auth.SelectAuthMethodKey
import com.elvitalya.droiderhandbook.ui.core.FragmentKey
import com.elvitalya.droiderhandbook.ui.core.createComposeView
import com.elvitalya.droiderhandbook.ui.main.MainFlowKey
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.StateChange
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import com.zhuinden.simplestackextensions.fragmentsktx.backstack
import kotlinx.parcelize.Parcelize

@Parcelize
data class SplashKey(val placeholder: Int = 0) : FragmentKey() {
    override fun instantiateFragment(): Fragment = SplashFragment()
}

class SplashFragment : KeyedFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = createComposeView(requireContext()).apply {
        setContent {
            SplashScreen()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val history = if (Firebase.auth.currentUser == null) History.of(SelectAuthMethodKey())
        else History.of(MainFlowKey())

        backstack.setHistory(history, StateChange.REPLACE)
    }
}