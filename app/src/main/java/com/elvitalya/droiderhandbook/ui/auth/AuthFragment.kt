package com.elvitalya.droiderhandbook.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.elvitalya.droiderhandbook.ui.core.FragmentKey
import com.elvitalya.droiderhandbook.ui.core.createComposeView
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthKey(val placeholder: Int = 0) : FragmentKey() {
    override fun instantiateFragment(): Fragment = AuthFragment()
}

@AndroidEntryPoint
class AuthFragment : KeyedFragment() {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = createComposeView(requireContext()).apply {
        setContent {

            val screenState by viewModel.screenState.collectAsState()

            AuthScreen(
                onSuccess = { },
                screenState = screenState,
                onAuthMethodSelected = viewModel::onAuthMethodSelected,
                onEmailInputChanged = viewModel::onEmailInputChanged,
                onPassInputChanged = viewModel::onPassInputChanged,
                onClickLogin = viewModel::onClickLogin
            )
        }
    }

}