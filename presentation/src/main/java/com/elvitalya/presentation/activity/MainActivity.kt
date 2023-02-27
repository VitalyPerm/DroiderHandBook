package com.elvitalya.presentation.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.children
import com.elvitalya.presentation.core.*
import com.elvitalya.presentation.databinding.ActivityMainBinding
import com.elvitalya.presentation.fragment.MainFlowKey
import com.elvitalya.presentation.fragment.SelectAuthMethodKey
import com.zhuinden.simplestack.GlobalServices
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.SimpleStateChanger
import com.zhuinden.simplestack.navigator.Navigator
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider

const val TAG = "check___"

class MainActivity : AppCompatActivity() {

    private val bottomSheetBackstack by lazy {
        lookupBottomSheetBackstack()
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Fix for window insets for fragment navigation
        binding.viewRoot.setOnApplyWindowInsetsListener { view, insets ->
            var consumed = false

            (view as ViewGroup).children.forEach { child ->
                val childResult = child.dispatchApplyWindowInsets(insets)
                if (childResult.isConsumed) {
                    consumed = true
                }
            }

            if (consumed) insets.consumeSystemWindowInsets() else insets
        }

        val fragmentStateChanger = AppStateChanger(supportFragmentManager, binding.viewRoot.id)


        val globalServices = GlobalServices
            .builder()
            .addService(AppBottomSheetView.BACKSTACK_TAG, AppBottomSheetView.createBackstack())
            .build()

        val history = if (true) MainFlowKey()
        else SelectAuthMethodKey()

        Navigator
            .configure()
            .setGlobalServices(globalServices)
            .setScopedServices(DefaultServiceProvider())
            .setStateChanger(SimpleStateChanger { stateChange ->

                hideKeyboard()
                fragmentStateChanger.handleStateChange(stateChange)
            })
            .install(this, binding.viewRoot, History.of(history))

        binding.viewAppBottomSheet.attachStateChanger()

        onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val bottomSheetTopKey = bottomSheetBackstack.top<FragmentKey>()
                    if (bottomSheetTopKey !is PlaceholderKey) {

                        if (bottomSheetBackstack.goBack()) {
                            return
                        }
                    }

                    if (!Navigator.onBackPressed(this@MainActivity)) {
                        finish()
                    }
                }
            })

    }

    private fun hideKeyboard() {
        val view = currentFocus ?: View(this)
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

