package com.susu.feature.sent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.LogoIcon
import com.susu.core.designsystem.component.appbar.icon.NotificationIcon
import com.susu.core.designsystem.component.appbar.icon.SearchIcon
import com.susu.core.designsystem.component.button.GhostButtonColor
import com.susu.core.designsystem.component.button.SmallButtonStyle
import com.susu.core.designsystem.component.button.SusuFloatingButton
import com.susu.core.designsystem.component.button.SusuGhostButton
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray50
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.feature.sent.component.SentCard

@Composable
fun SentRoute(
    padding: PaddingValues,
    navigateSentEnvelope: () -> Unit,
    navigateSentEnvelopeAdd: () -> Unit,
) {
    SentScreen(
        padding = padding,
        onClickHistoryShowAll = navigateSentEnvelope,
        onClickAddEnvelope = navigateSentEnvelopeAdd,
    )
}

@Composable
fun SentScreen(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    onClickSearchIcon: () -> Unit = {},
    onClickNotificationIcon: () -> Unit = {},
    onClickHistoryShowAll: () -> Unit = {},
    onClickAddEnvelope: () -> Unit = {},
) {
    // TODO: 수정 필요 (확인을 위해 false로 설정)
    var isEmpty by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .background(SusuTheme.colorScheme.background15)
            .padding(padding)
            .fillMaxSize(),
    ) {
        Column {
            SusuDefaultAppBar(
                leftIcon = {
                    Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_xs))
                    LogoIcon()
                },
                title = stringResource(R.string.sent_screen_appbar_title),
                actions = {
                    SearchIcon(onClickSearchIcon)
                    NotificationIcon(onClickNotificationIcon)
                },
            )

            if (!isEmpty) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
                    contentPadding = PaddingValues(SusuTheme.spacing.spacing_m),
                ) {
                    // TODO: 수정 필요
                    item {
                        FilterSection(
                            padding = PaddingValues(
                                bottom = SusuTheme.spacing.spacing_xxs,
                            ),
                        )
                    }
                    items(8) {
                        SentCard(onClick = onClickHistoryShowAll)
                    }
                }
            } else {
                FilterSection(
                    padding = PaddingValues(SusuTheme.spacing.spacing_m),
                )
                EmptyView()
            }
        }

        SusuFloatingButton(
            modifier = modifier
                .align(Alignment.BottomEnd)
                .padding(SusuTheme.spacing.spacing_l),
            onClick = onClickAddEnvelope,
        )
    }
}

@Composable
fun FilterSection(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(padding),
        horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
    ) {
        SusuGhostButton(
            color = GhostButtonColor.Black,
            style = SmallButtonStyle.height32,
            text = stringResource(com.susu.core.ui.R.string.word_align_recently),
            leftIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sort),
                    contentDescription = stringResource(com.susu.core.ui.R.string.word_align_recently),
                    tint = Gray100,
                    modifier = modifier.size(16.dp),
                )
            },
        )
        SusuGhostButton(
            color = GhostButtonColor.Black,
            style = SmallButtonStyle.height32,
            text = stringResource(com.susu.core.ui.R.string.word_filter),
            leftIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = stringResource(com.susu.core.ui.R.string.word_filter),
                    tint = Gray100,
                    modifier = modifier.size(16.dp),
                )
            },
        )
    }
}

@Composable
fun EmptyView(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.sent_screen_empty_view_title),
            color = Gray50,
            style = SusuTheme.typography.text_s,
        )
        Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_m))
        SusuGhostButton(
            color = GhostButtonColor.Black,
            style = SmallButtonStyle.height40,
            text = stringResource(R.string.sent_screen_empty_view_add_button),
        )
    }
}

@Preview
@Composable
fun SentScreenPreview() {
    SusuTheme {
        SentScreen(padding = PaddingValues(0.dp))
    }
}
