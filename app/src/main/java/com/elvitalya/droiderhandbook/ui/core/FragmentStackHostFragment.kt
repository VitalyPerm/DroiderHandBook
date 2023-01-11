package com.elvitalya.droiderhandbook.ui.core

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.elvitalya.droiderhandbook.R
import com.zhuinden.simplestack.SimpleStateChanger
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentStateChanger
import com.zhuinden.simplestackextensions.fragmentsktx.lookup

class FragmentStackHostFragment : Fragment(R.layout.fragment_stack_host) {

    companion object {
        private const val ARG_STACK_HOST_ID = "ARG_STACK_HOST_ID"

        fun newInstance(stackHostId: String): FragmentStackHostFragment {
            val bundle = Bundle()
            bundle.putString(ARG_STACK_HOST_ID, stackHostId)

            val fragment = FragmentStackHostFragment()
            fragment.arguments = bundle

            return fragment
        }
    }

    private lateinit var stateChanger: DefaultFragmentStateChanger

    private val stackHost by lazy {
        lookup<FragmentStackHost>(requireArguments().getString(ARG_STACK_HOST_ID)!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        stateChanger = AppStateChanger(childFragmentManager, R.id.viewRoot)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stackHost.backstack.setStateChanger(SimpleStateChanger { stateChange ->
            stateChanger.handleStateChange(stateChange)
        })

        stackHost.backstack.reattachStateChanger()
        stackHost.isActiveForBack = true
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            stackHost.isActiveForBack = false
            stackHost.backstack.detachStateChanger()
        } else {
            stackHost.backstack.reattachStateChanger()
            stackHost.isActiveForBack = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        stackHost.isActiveForBack = false
        stackHost.backstack.detachStateChanger()

        stackHost.backstack.executePendingStateChange()
    }
}