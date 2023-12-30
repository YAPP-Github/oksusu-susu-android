package com.susu.core.designsystem.component.appbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.R
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray50
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable

enum class RightActionsType {
    NONE, ICON, TEXT,
}

@Composable
fun SusuDefaultAppBar(
    modifier: Modifier = Modifier,
    containerHeight: Dp = 44.dp,
    leftIconType: LeftIconType,
    @DrawableRes leftIcon: Int,
    title: String? = null,
    titleColor: Color = Gray100,
    titleStyle: TextStyle = SusuTheme.typography.title_xs,
    rightActionsType: RightActionsType = RightActionsType.NONE,
    @DrawableRes rightStartIcon: Int? = null,
    rightStartIconContentDescription: String? = null,
    @DrawableRes rightEndIcon: Int? = null,
    rightEndIconContentDescription: String? = null,
    rightStartText: String? = null,
    rightEndText: String? = null,
    rightTextStyle: TextStyle = SusuTheme.typography.title_xxs,
    rightStartItemColor: Color = Gray100,
    rightEndItemColor: Color = Gray100,
    rightTextPadding: Dp = 16.dp,
    onClickBackButton: () -> Unit = {},
    onClickRightStartButton: () -> Unit = {},
    onClickRightEndButton: () -> Unit = {},
) {
    BasicAppBar(
        modifier = modifier,
        containerHeight = containerHeight,
        leftIconType = leftIconType,
        leftIcon = leftIcon,
        title = {
            title?.let {
                Text(
                    text = title,
                    style = titleStyle,
                    color = titleColor,
                    textAlign = TextAlign.Center,
                )
            }
        },
        actions = {
            when (rightActionsType) {
                RightActionsType.ICON -> {
                    Box(
                        modifier = modifier
                            .size(containerHeight)
                            .susuClickable(
                                rippleEnabled = false,
                                onClick = onClickRightStartButton,
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        rightStartIcon?.let {
                            Icon(
                                painter = painterResource(id = it),
                                contentDescription = rightStartIconContentDescription,
                                tint = rightStartItemColor,
                            )
                        }
                    }
                    Box(
                        modifier = modifier
                            .size(containerHeight)
                            .susuClickable(
                                rippleEnabled = false,
                                onClick = onClickRightEndButton,
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        rightEndIcon?.let {
                            Icon(
                                painter = painterResource(id = it),
                                contentDescription = rightEndIconContentDescription,
                                tint = rightEndItemColor,
                            )
                        }
                    }
                }
                RightActionsType.TEXT -> {
                    rightStartText?.let {
                        Text(
                            text = rightStartText,
                            style = rightTextStyle,
                            color = rightStartItemColor,
                            modifier = modifier
                                .padding(end = rightTextPadding)
                                .susuClickable(
                                    rippleEnabled = false,
                                    onClick = onClickRightStartButton
                                )
                        )
                    }
                    rightEndText?.let {
                        Text(
                            text = rightEndText,
                            style = rightTextStyle,
                            color = rightEndItemColor,
                            modifier = modifier
                                .padding(end = rightTextPadding)
                                .susuClickable(
                                    rippleEnabled = false,
                                    onClick = onClickRightEndButton
                                )
                        )
                    }
                }
                RightActionsType.NONE -> {}
            }
        },
        onClickBackButton = { onClickBackButton },
    )
}

@Preview(showBackground = true)
@Composable
fun SusuDefaultAppBarPreview() {
    SusuTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // 왼쪽 로고 + 타이틀 + 오른쪽 아이콘 2개
            SusuDefaultAppBar(
                leftIconType = LeftIconType.LOGO,
                leftIcon = R.drawable.ic_received_filled,
                title = "타이틀",
                rightActionsType = RightActionsType.ICON,
                rightStartIcon = R.drawable.ic_community_filled,
                rightStartIconContentDescription = "검색",
                rightEndIcon = R.drawable.ic_statistics_filled,
                rightEndIconContentDescription = "알림",
                onClickRightStartButton = { /*TODO*/ },
                onClickRightEndButton = { /*TODO*/ }
            )

            // 왼쪽 로고 + 오른쪽 아이콘 2개
            SusuDefaultAppBar(
                leftIconType = LeftIconType.LOGO,
                leftIcon = R.drawable.ic_received_filled,
                rightActionsType = RightActionsType.ICON,
                rightStartIcon = R.drawable.ic_community_filled,
                rightStartIconContentDescription = "검색",
                rightEndIcon = R.drawable.ic_statistics_filled,
                rightEndIconContentDescription = "알림",
                onClickRightStartButton = { /*TODO*/ },
                onClickRightEndButton = { /*TODO*/ }
            )

            // 왼쪽 뒤로가기 + 타이틀 + 오른쪽 아이콘 2개
            SusuDefaultAppBar(
                leftIconType = LeftIconType.BACKBUTTON,
                leftIcon = R.drawable.ic_arrow_left,
                onClickBackButton = { /*TODO*/ },
                title = "타이틀",
                rightActionsType = RightActionsType.ICON,
                rightStartIcon = R.drawable.ic_community_filled,
                rightStartIconContentDescription = "검색",
                rightEndIcon = R.drawable.ic_statistics_filled,
                rightEndIconContentDescription = "알림",
                onClickRightStartButton = { /*TODO*/ },
                onClickRightEndButton = { /*TODO*/ }
            )

            // 왼쪽 뒤로가기 + 타이틀
            SusuDefaultAppBar(
                leftIconType = LeftIconType.BACKBUTTON,
                leftIcon = R.drawable.ic_arrow_left,
                onClickBackButton = { /*TODO*/ },
                title = "타이틀",
            )

            // 왼쪽 뒤로가기 + 타이틀 + 오른쪽 아이콘 1개
            SusuDefaultAppBar(
                leftIconType = LeftIconType.BACKBUTTON,
                leftIcon = R.drawable.ic_arrow_left,
                onClickBackButton = { /*TODO*/ },
                rightActionsType = RightActionsType.ICON,
                title = "타이틀",
                rightEndIcon = R.drawable.ic_statistics_filled,
                rightEndItemColor = Gray50
            )

            // 왼쪽 뒤로가기 + 타이틀 + 오른쪽 텍스트 1개
            SusuDefaultAppBar(
                leftIconType = LeftIconType.BACKBUTTON,
                leftIcon = R.drawable.ic_arrow_left,
                onClickBackButton = { /*TODO*/ },
                rightActionsType = RightActionsType.TEXT,
                title = "타이틀",
                rightEndText = "등록",
            )

            // 왼쪽 뒤로가기 + 타이틀 + 오른쪽 텍스트 2개
            SusuDefaultAppBar(
                leftIconType = LeftIconType.BACKBUTTON,
                leftIcon = R.drawable.ic_arrow_left,
                onClickBackButton = { /*TODO*/ },
                title = "타이틀",
                rightActionsType = RightActionsType.TEXT,
                rightStartText = "편집",
                rightEndText = "삭제"
            )

            // 왼쪽 뒤로가기 + 오른쪽 텍스트 2개
            SusuDefaultAppBar(
                leftIconType = LeftIconType.BACKBUTTON,
                leftIcon = R.drawable.ic_arrow_left,
                onClickBackButton = { /*TODO*/ },
                rightActionsType = RightActionsType.TEXT,
                rightStartText = "편집",
                rightEndText = "삭제"
            )

            // 왼쪽 뒤로가기
            SusuDefaultAppBar(
                leftIconType = LeftIconType.BACKBUTTON,
                leftIcon = R.drawable.ic_arrow_left,
                onClickBackButton = { /*TODO*/ },
            )
        }
    }
}
