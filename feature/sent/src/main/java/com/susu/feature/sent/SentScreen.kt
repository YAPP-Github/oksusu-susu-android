package com.susu.feature.sent

import androidx.compose.foundation.Image
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.button.GhostButtonColor
import com.susu.core.designsystem.component.button.SmallButtonStyle
import com.susu.core.designsystem.component.button.SusuFloatingButton
import com.susu.core.designsystem.component.button.SusuGhostButton
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray50
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable

@Composable
fun SentScreen(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    clickPadding: Dp = SusuTheme.spacing.spacing_xs,
) {
    // TODO: 수정 필요
    var isEmpty by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .background(SusuTheme.colorScheme.background15)
            .padding(padding)
            .fillMaxSize(),
    ) {
        Column {
            SusuDefaultAppBar(
                leftIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_susu_app),
                        contentDescription = stringResource(R.string.app_bar_logo),
                        modifier = modifier
                            .size(
                                width = 56.dp,
                                height = 24.dp,
                            )
                            .padding(
                                start = SusuTheme.spacing.spacing_xs,
                            ),
                    )
                },
                title = "보내요",
                actions = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = stringResource(R.string.app_bar_search),
                        modifier = modifier
                            .susuClickable(
                                rippleEnabled = false,
                                onClick = {},
                            )
                            .padding(clickPadding),
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_notification),
                        contentDescription = stringResource(R.string.app_bar_notification),
                        modifier = modifier
                            .susuClickable(
                                rippleEnabled = false,
                                onClick = {},
                            )
                            .padding(clickPadding),
                    )
                },
            )

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(SusuTheme.spacing.spacing_m),
                horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
            ) {
                SusuGhostButton(
                    color = GhostButtonColor.Black,
                    style = SmallButtonStyle.height32,
                    text = stringResource(R.string.order_latest),
                    leftIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_sort),
                            contentDescription = stringResource(R.string.order_sort),
                            tint = Gray100,
                            modifier = modifier.size(16.dp),
                        )
                    },
                )
                SusuGhostButton(
                    color = GhostButtonColor.Black,
                    style = SmallButtonStyle.height32,
                    text = stringResource(R.string.order_filter),
                    leftIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_filter),
                            contentDescription = stringResource(R.string.order_filter),
                            tint = Gray100,
                            modifier = modifier.size(16.dp),
                        )
                    },
                )
            }

            if (!isEmpty) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
                    contentPadding = PaddingValues(
                        start = SusuTheme.spacing.spacing_m,
                        end = SusuTheme.spacing.spacing_m,
                        bottom = SusuTheme.spacing.spacing_m,
                    ),
                ) {
                    // TODO: 수정 필요
                    items(8) {
                        EnvelopeCard()
                    }
                }
            } else {
                EmptyView()
            }
        }

        SusuFloatingButton(
            modifier = modifier
                .align(Alignment.BottomEnd)
                .padding(SusuTheme.spacing.spacing_l),
            onClick = {},
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
            text = stringResource(R.string.empty_view_title),
            color = Gray50,
            style = SusuTheme.typography.text_s,
        )
        Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_m))
        SusuGhostButton(
            color = GhostButtonColor.Black,
            style = SmallButtonStyle.height40,
            text = stringResource(R.string.empty_view_add_button),
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
