package com.susu.feature.navigator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.compose.NavHost
import com.susu.core.designsystem.component.navigation.SusuNavigationBar
import com.susu.core.designsystem.component.navigation.SusuNavigationItem
import com.susu.feature.community.navigation.communityNavGraph
import com.susu.feature.loginsignup.navigation.loginSignupNavGraph
import com.susu.feature.mypage.navigation.myPageNavGraph
import com.susu.feature.received.navigation.receivedNavGraph
import com.susu.feature.sent.navigation.sentNavGraph
import com.susu.feature.statistics.navigation.statisticsNavGraph
import kotlin.enums.EnumEntries

@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier,
    navigator: MainNavigator = rememberMainNavigator(),
) {
    Scaffold(
        modifier = modifier,
        content = { innerPadding ->
            NavHost(
                navController = navigator.navController,
                startDestination = navigator.startDestination,
            ) {
                loginSignupNavGraph()

                sentNavGraph(
                    padding = innerPadding,
                )

                receivedNavGraph(
                    padding = innerPadding,
                )

                statisticsNavGraph(
                    padding = innerPadding,
                )

                communityNavGraph(
                    padding = innerPadding,
                )

                myPageNavGraph(
                    padding = innerPadding,
                )
            }
        },
        bottomBar = {
            MainBottomBar(
                visible = navigator.shouldShowBottomBar(),
                currentTab = navigator.currentTab,
                entries = MainNavigationTab.entries,
                onClickItem = navigator::navigate,
            )
        },
    )
}

@Composable
private fun MainBottomBar(
    visible: Boolean,
    currentTab: MainNavigationTab?,
    entries: EnumEntries<MainNavigationTab>,
    onClickItem: (MainNavigationTab) -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideIn { IntOffset(0, it.height) },
        exit = fadeOut() + slideOut { IntOffset(0, it.height) },
    ) {
        SusuNavigationBar {
            entries.forEach { tab ->
                tab.run {
                    SusuNavigationItem(
                        selected = tab == currentTab,
                        label = stringResource(id = labelId),
                        selectedIcon = selectedIconId,
                        unselectedIcon = unselectedIconId,
                        onClick = { onClickItem(tab) },
                    )
                }
            }
        }
    }
}
