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
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.feature.loginsignup.social.KakaoLoginHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val uiState
        get() = viewModel.uiState.value

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition { uiState.showSplashScreen }

        if (uiState.isNavigating) {
            KakaoLoginHelper.getAccessToken { accessToken ->
                viewModel.navigate(
                    hasKakaoLoginHistory = KakaoLoginHelper.hasKakaoLoginHistory,
                    kakaoAccessToken = accessToken,
                )
            }
        }

        if (uiState.isInitializing) {
            viewModel.initCategoryConfig()
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            SusuTheme {
                MainScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(
                            WindowInsets.systemBars.only(
                                WindowInsetsSides.Vertical,
                            ),
                        ),
                    viewModel = viewModel,
                )
            }
        }
    }
}
