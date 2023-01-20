package com.elvitalya.droiderhandbook.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.elvitalya.droiderhandbook.ui.core.FragmentKey
import com.elvitalya.droiderhandbook.ui.core.createComposeView
import com.elvitalya.droiderhandbook.ui.core.lookupBottomSheetBackstack
import com.elvitalya.droiderhandbook.ui.main.MainFlowKey
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.StateChange
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import com.zhuinden.simplestackextensions.fragmentsktx.backstack
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthKey(val authMethod: AuthMethod) : FragmentKey() {
    override fun instantiateFragment(): Fragment = AuthFragment()
}

@AndroidEntryPoint
class AuthFragment : KeyedFragment() {

    private val viewModel: AuthViewModel by viewModels()

    private val contentView by lazy { requireContext().lookupBottomSheetBackstack() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = createComposeView(requireContext()).apply {
        setContent {

            viewModel.onAuthMethodSelected(getKey<AuthKey>().authMethod)

            val authMethod by viewModel.authMethod.collectAsState()
            val email by viewModel.email.collectAsState()
            val password by viewModel.password.collectAsState()
            val authButtonEnabled by viewModel.buttonAuthEnabled.collectAsState()
            val errorMessage by viewModel.errorMessage.collectAsState()
            val viewState by viewModel.viewState.collectAsState()

            AuthScreen(
                authMethod = authMethod,
                email = email,
                password = password,
                authButtonEnabled = authButtonEnabled,
                errorMessage = errorMessage,
                viewState = viewState,
                onEmailInputChanged = viewModel::onEmailInputChanged,
                onPassInputChanged = viewModel::onPassInputChanged,
                onAuthClick = viewModel::onClickLogin,
                onCloseClick = { contentView.jumpToRoot() }
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigateToMainScreen
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    if (it.not()) return@collect
                    contentView.jumpToRoot()
                    backstack.setHistory(History.of(MainFlowKey()), StateChange.REPLACE)
                }
        }
    }

}