package com.susu.core.designsystem.component.appbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.R
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.ui.extension.susuClickable

enum class LeftIconType {
    LOGO, BACKBUTTON
}

@Composable
fun BasicAppBar(
    modifier: Modifier = Modifier,
    containerHeight: Dp,
    leftIconType: LeftIconType,
    leftIconPadding: Dp = 10.dp,
    @DrawableRes leftIcon: Int,
    title: @Composable RowScope.() -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
    onClickBackButton: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(containerHeight)
            .background(Gray10), // TODO: 디자인 시스템에서는 투명, Preview에서 확인하기 위해 설정 (지울 예정)
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // TODO: AppBar의 왼쪽 영역은 거의 고정이라 생각되어 Basic으로 뺌 (지울 예정)
            when (leftIconType) {
                LeftIconType.LOGO -> {
                    Image(
                        painter = painterResource(id = leftIcon),
                        contentDescription = stringResource(R.string.app_bar_app_logo),
                        modifier = modifier.padding(leftIconPadding),
                    )
                }

                LeftIconType.BACKBUTTON -> {
                    Box(
                        modifier = modifier
                            .size(containerHeight)
                            .susuClickable(
                                rippleEnabled = false,
                                onClick = onClickBackButton,
                            ),
                    ) {
                        Icon(
                            painter = painterResource(id = leftIcon),
                            contentDescription = stringResource(R.string.app_bar_back_button),
                            tint = Gray100,
                            modifier = modifier.align(Alignment.Center),
                        )
                    }
                }
            }

            Spacer(modifier = modifier.weight(1f))

            Row(content = actions)
        }
        Row(
            modifier = modifier.fillMaxWidth(),
            content = title,
            horizontalArrangement = Arrangement.Center,
        )
    }
}
