package com.susu.feature.statistics.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray20
import com.susu.core.designsystem.theme.Gray50
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable
import com.susu.feature.statistics.StatisticsTab

@Composable
fun StatisticsTab(
    modifier: Modifier = Modifier,
    selectedTab: StatisticsTab = StatisticsTab.MY,
    onTabSelect: (StatisticsTab) -> Unit = {},
) {
    Row(
        modifier = modifier
            .background(color = Gray10, shape = RoundedCornerShape(4.dp))
            .padding(SusuTheme.spacing.spacing_xxxxs),
    ) {
        StatisticsTabItem(
            modifier = Modifier.weight(1f),
            text = stringResource(id = StatisticsTab.MY.stringId),
            isSelected = selectedTab == StatisticsTab.MY,
            onClick = { onTabSelect(StatisticsTab.MY) },
        )
        Spacer(modifier = Modifier.width(SusuTheme.spacing.spacing_xxxxs))
        StatisticsTabItem(
            modifier = Modifier.weight(1f),
            text = stringResource(id = StatisticsTab.AVERAGE.stringId),
            isSelected = selectedTab == StatisticsTab.AVERAGE,
            onClick = { onTabSelect(StatisticsTab.AVERAGE) },
        )
    }
}

@Composable
fun StatisticsTabItem(
    isSelected: Boolean,
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize().background(
            color = if (isSelected) {
                Gray20
            } else {
                Gray10
            },
            shape = RoundedCornerShape(4.dp),
        ).susuClickable(onClick = onClick),
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            style = SusuTheme.typography.title_xxxs,
            color = if (isSelected) {
                Gray100
            } else {
                Gray50
            },
        )
    }
}
