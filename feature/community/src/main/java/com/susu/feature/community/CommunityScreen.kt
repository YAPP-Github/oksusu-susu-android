package com.susu.feature.community

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.LogoIcon
import com.susu.core.designsystem.component.appbar.icon.NotificationIcon
import com.susu.core.designsystem.component.appbar.icon.SearchIcon
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.GhostButtonColor
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.component.button.SusuFloatingButton
import com.susu.core.designsystem.component.button.SusuGhostButton
import com.susu.core.designsystem.component.button.XSmallButtonStyle
import com.susu.core.designsystem.theme.Blue60
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray15
import com.susu.core.designsystem.theme.Gray20
import com.susu.core.designsystem.theme.Gray30
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.Gray50
import com.susu.core.designsystem.theme.Gray60
import com.susu.core.designsystem.theme.Orange60
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable

@Composable
fun CommunityRoute(
    padding: PaddingValues,
    navigateVoteAdd: () -> Unit,
) {
    CommunityScreen(
        padding = padding,
        navigateVoteAdd = navigateVoteAdd,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CommunityScreen(
    padding: PaddingValues,
    onClickSearchIcon: () -> Unit = {},
    navigateVoteAdd: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier

                .background(SusuTheme.colorScheme.background10),
        ) {
            SusuDefaultAppBar(
                modifier = Modifier.padding(horizontal = SusuTheme.spacing.spacing_xs),
                leftIcon = {
                    LogoIcon()
                },
                title = stringResource(R.string.community_screen_title),
                actions = {
                    Row {
                        SearchIcon(onClickSearchIcon)
                    }
                },
            )

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = SusuTheme.spacing.spacing_m),
            ) {
                item {
                    Column {
                        Text(
                            modifier = Modifier.padding(start = SusuTheme.spacing.spacing_m),
                            text = stringResource(R.string.community_screen_most_popular_vote),
                            style = SusuTheme.typography.title_xxs,
                        )

                        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxs))

                        LazyRow(
                            contentPadding = PaddingValues(horizontal = SusuTheme.spacing.spacing_m),
                            horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_m),
                        ) {
                            items(count = 5) {
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Gray15)
                                        .size(
                                            width = 296.dp,
                                            height = 156.dp,
                                        )
                                        .padding(SusuTheme.spacing.spacing_m),
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Text(text = "결혼식", color = Gray60, style = SusuTheme.typography.title_xxxs)
                                        Icon(
                                            modifier = Modifier.size(20.dp),
                                            painter = painterResource(id = com.susu.core.ui.R.drawable.ic_arrow_right),
                                            contentDescription = null,
                                            tint = Gray50,
                                        )
                                    }

                                    Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxs))

                                    Text(
                                        text = "고등학교 동창이고 좀 애매하게 친한 사인데 축의금 얼마 내야 돼?",
                                        style = SusuTheme.typography.text_xxxs,
                                    )

                                    Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xs))

                                    SusuGhostButton(
                                        textModifier = Modifier.weight(1f),
                                        text = "12,430명 참여 중",
                                        textAlign = TextAlign.Center,
                                        color = GhostButtonColor.Black,
                                        style = XSmallButtonStyle.height44,
                                        isClickable = false,
                                        leftIcon = {
                                            Image(
                                                painter = painterResource(id = R.drawable.ic_vote),
                                                contentDescription = null,
                                            )
                                        },
                                    )
                                }
                            }
                        }

                    }
                }

                item {
                    Spacer(
                        modifier = Modifier
                            .size(SusuTheme.spacing.spacing_m),
                    )
                    HorizontalDivider(
                        thickness = SusuTheme.spacing.spacing_xxs,
                        color = Gray20,
                    )
                }

                stickyHeader {
                    Column(
                        modifier = Modifier
                            .background(Gray10)
                            .padding(
                                top = SusuTheme.spacing.spacing_m,
                                start = SusuTheme.spacing.spacing_m,
                                end = SusuTheme.spacing.spacing_m,
                                bottom = SusuTheme.spacing.spacing_xxs,
                            ),
                        verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xl),
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxxxs),
                        ) {
                            listOf("전체", "결혼식", "장례식", "돌잔치", "생일 기념일", "자유").forEach {
                                SusuFilledButton(
                                    color = FilledButtonColor.Black,
                                    style = XSmallButtonStyle.height28,
                                    text = it,
                                    isActive = true,
                                    onClick = { },
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(6.dp)
                                        .background(Gray30),
                                )

                                Text(
                                    text = stringResource(R.string.community_screen_vote_align_high),
                                    style = SusuTheme.typography.title_xxxs,
                                    color = Gray40,
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxxxs),
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_uncheck),
                                    contentDescription = null,
                                )

                                Text(
                                    text = stringResource(R.string.community_screen_show_my_article),
                                    style = SusuTheme.typography.title_xxxs,
                                    color = Gray40,
                                )
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_s))
                }

                items(10) {
                    Column(
                        modifier = Modifier
                            .padding(
                                start = SusuTheme.spacing.spacing_m,
                                end = SusuTheme.spacing.spacing_m,
                                bottom = SusuTheme.spacing.spacing_xxs,
                            )
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Gray15)
                            .padding(SusuTheme.spacing.spacing_m),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(text = "결혼식", color = Orange60, style = SusuTheme.typography.title_xxxs)
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    painter = painterResource(id = com.susu.core.ui.R.drawable.ic_arrow_right),
                                    contentDescription = null,
                                    tint = Orange60,
                                )
                            }

                            Text(
                                text = "10분 전",
                                style = SusuTheme.typography.text_xxxs,
                                color = Gray40,
                            )
                        }

                        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxs))

                        Text(
                            text = "고등학교 동창이고 좀 애매하게 친한 사인데 축의금 얼마 내야 돼?",
                            style = SusuTheme.typography.text_xxxs,
                        )

                        Column(
                            verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxxxs),
                        ) {
                            repeat(5) {
                                SusuGhostButton(
                                    textModifier = Modifier.weight(1f),
                                    text = "${it}만원",
                                    color = GhostButtonColor.Black,
                                    style = XSmallButtonStyle.height44,
                                    isClickable = false,
                                )
                            }
                        }

                        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_s))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = "8명 참여",
                                style = SusuTheme.typography.title_xxxs,
                                color = Blue60,
                            )

                            Image(
                                painter = painterResource(id = R.drawable.ic_report),
                                contentDescription = stringResource(com.susu.core.ui.R.string.content_description_report_button),
                            )
                        }
                    }
                }
            }
        }

        SusuFloatingButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(SusuTheme.spacing.spacing_l),
            imageResId = R.drawable.ic_vote_add,
            onClick = navigateVoteAdd,
        )
    }
}

@Preview
@Composable
fun CommunityScreenPreview() {
    SusuTheme {
        CommunityScreen(padding = PaddingValues(0.dp))
    }
}
