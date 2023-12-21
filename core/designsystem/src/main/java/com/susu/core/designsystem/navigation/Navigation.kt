package com.susu.core.designsystem.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray20
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.SusuTheme

private const val NAVIGATION_HEIGHT = 56
private const val DIVIDER_THICKNESS = 1
private const val TAB_COUNTS = 5

@Composable
fun SusuNavigation(
    modifier: Modifier = Modifier,
) {
    Scaffold(
        bottomBar = {
            SusuNavigationBar()
        },
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxWidth(),
        ) {}
    }
}

@Composable
fun SusuNavigationBar(
    modifier: Modifier = Modifier,
) {
    val tabs = listOf(
        NavigationItem.SENT,
        NavigationItem.RECEIVED,
        NavigationItem.STATISTICS,
        NavigationItem.COMMUNITY,
        NavigationItem.MYPAGE,
    )

    var selectedBottomNavItem by remember { mutableStateOf(0) }

    Column {
        Divider(
            thickness = DIVIDER_THICKNESS.dp,
            color = Gray20
        )
        Row(
            modifier = modifier
                .height(NAVIGATION_HEIGHT.dp)
                .background(color = SusuTheme.colorScheme.background10)
        ) {
            tabs.forEachIndexed { index, item ->
                SusuNavigationItem(
                    modifier = modifier
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                        ) { selectedBottomNavItem = index },
                    item = item,
                    selected = selectedBottomNavItem == index,
                )
            }
        }
    }
}

@Composable
fun SusuNavigationItem(
    modifier: Modifier = Modifier,
    item: NavigationItem,
    selected: Boolean,
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Column(
        modifier = modifier
            .width(screenWidth / TAB_COUNTS)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val icon = if (selected) item.selectedIcon else item.unselectedIcon
        Icon(
            painter = painterResource(id = icon),
            contentDescription = item.tabName,
            tint = if (selected) Gray100 else Gray40,
        )
        Text(
            text = item.tabName,
            style = SusuTheme.typography.title_xxxxs,
            color = if (selected) Gray100 else Gray40,
            modifier = modifier.padding(vertical = 3.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SusuNavigationPreview() {
    SusuTheme {
        SusuNavigation()
    }
}
