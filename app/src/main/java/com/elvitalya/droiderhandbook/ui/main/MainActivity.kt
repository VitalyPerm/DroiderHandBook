package com.elvitalya.droiderhandbook.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.children
import com.elvitalya.droiderhandbook.databinding.ActivityMainBinding
import com.elvitalya.droiderhandbook.ui.auth.AuthKey
import com.elvitalya.droiderhandbook.ui.core.*
import com.zhuinden.simplestack.GlobalServices
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.SimpleStateChanger
import com.zhuinden.simplestack.navigator.Navigator
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "check___"
    }

    private val bottomSheetBackstack by lazy {
        lookupBottomSheetBackstack()
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fixes issue when starting the app again from icon on launcher
        if (isTaskRoot.not()
            && intent.hasCategory(Intent.CATEGORY_LAUNCHER)
            && intent.action != null
            && intent.action == Intent.ACTION_MAIN
        ) {
            finish()
            return
        }

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

//        val history = when {
            // todo add history
//            !authDataSource.isAuthorized() && BuildConfig.ENDPOINT_CHANGE_ENABLED -> History.of(
//                EndpointChangeKey()
//            )
//            authDataSource.isAuthorized() && videoRoomKey != null -> History.of(
//                UpdateKey(),
//                videoRoomKey
//            )
//            else -> History.of(UpdateKey())
  //      }

        val globalServices = GlobalServices
            .builder()
            .addService(AppBottomSheetView.BACKSTACK_TAG, AppBottomSheetView.createBackstack())
            .build()

        Navigator
            .configure()
            .setGlobalServices(globalServices)
            .setScopedServices(DefaultServiceProvider())
            .setStateChanger(SimpleStateChanger { stateChange ->

                // hideKeyboard()
                fragmentStateChanger.handleStateChange(stateChange)
            })
            .install(this, binding.viewRoot, History.of(AuthKey()))

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
}

/*
before

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    companion object {
        const val TAG = "check___"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            var isRegistered by remember { mutableStateOf(false) }
            isRegistered = Firebase.auth.currentUser != null
            DroiderHandBookTheme {
                Crossfade(
                    targetState = isRegistered,
                    animationSpec = tween(1000)
                ) { registered ->
                    if (registered) MainNavHost()
                    else SignInScreen(
                        onSuccess = {
                            isRegistered = true
                            Toast.makeText(context, getString(R.string.success), Toast.LENGTH_SHORT)
                                .show()
                        }
                    )
                }
            }
        }

    }
}
 */
