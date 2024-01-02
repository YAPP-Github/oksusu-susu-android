package com.susu.feature.navigator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.feature.loginsignup.navigation.LoginSignupRoute
import com.susu.feature.navigator.initialization.InitialRoute
import com.susu.feature.navigator.initialization.MainContract
import com.susu.feature.navigator.initialization.MainViewModel
import com.susu.feature.received.navigation.ReceivedRoute
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var uiState: MainContract.MainState by mutableStateOf(MainContract.MainState())

        // Update the uiState
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    uiState = it
                    Timber.d(uiState.toString())
                }
            }
        }

        splashScreen.setKeepOnScreenCondition { uiState.isLoading }

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            SusuTheme {
                if (uiState.isLoading.not()) {
                    MainScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .windowInsetsPadding(
                                WindowInsets.systemBars.only(
                                    WindowInsetsSides.Vertical,
                                ),
                            ),
                        startDestination = when (uiState.initialRoute) {
                            InitialRoute.SIGNUP_VOTE -> LoginSignupRoute.Parent.Vote.route
                            InitialRoute.LOGIN -> LoginSignupRoute.Parent.Login.route
                            InitialRoute.SIGNUP -> LoginSignupRoute.Parent.SignUp.route
                            InitialRoute.RECEIVED -> ReceivedRoute.route
                            InitialRoute.NONE -> LoginSignupRoute.Parent.route
                        },
                    )
                }
            }
        }
    }
}
