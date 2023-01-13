package com.elvitalya.droiderhandbook.ui.main

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.children
import com.elvitalya.droiderhandbook.databinding.ActivityMainBinding
import com.elvitalya.droiderhandbook.ui.auth.SelectAuthMethodKey
import com.elvitalya.droiderhandbook.ui.core.*
import com.zhuinden.simplestack.GlobalServices
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.SimpleStateChanger
import com.zhuinden.simplestack.navigator.Navigator
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider
import dagger.hilt.android.AndroidEntryPoint

const val TAG = "check___"

@AndroidEntryPoint
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

        Navigator
            .configure()
            .setGlobalServices(globalServices)
            .setScopedServices(DefaultServiceProvider())
            .setStateChanger(SimpleStateChanger { stateChange ->

                hideKeyboard()
                fragmentStateChanger.handleStateChange(stateChange)
            })
            .install(this, binding.viewRoot, History.of(SelectAuthMethodKey()))

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

