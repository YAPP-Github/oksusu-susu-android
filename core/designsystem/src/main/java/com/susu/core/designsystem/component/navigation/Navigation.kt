package com.susu.core.designsystem.component.navigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray20
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable

@Composable
fun SusuNavigationBar(
    modifier: Modifier = Modifier,
    dividerColor: Color = Gray20,
    dividerThickness: Dp = 1.dp,
    containerHeight: Dp = 56.dp,
    containerColor: Color = SusuTheme.colorScheme.background10,
    content: @Composable RowScope.() -> Unit,
) {
    Column {
        Divider(
            thickness = dividerThickness,
            color = dividerColor,
        )
        Row(
            modifier = modifier
                .height(containerHeight)
                .background(color = containerColor),
        ) {
            content()
        }
    }
}

@Composable
fun RowScope.SusuNavigationItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    label: String,
    @DrawableRes selectedIcon: Int,
    @DrawableRes unselectedIcon: Int,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .weight(1f)
            .fillMaxHeight()
            .susuClickable(rippleEnabled = false, onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val icon = if (selected) selectedIcon else unselectedIcon
        Icon(
            painter = painterResource(id = icon),
            contentDescription = label,
            tint = if (selected) Gray100 else Gray40,
        )
        Text(
            text = label,
            style = SusuTheme.typography.title_xxxxs,
            color = if (selected) Gray100 else Gray40,
        )
    }
}
