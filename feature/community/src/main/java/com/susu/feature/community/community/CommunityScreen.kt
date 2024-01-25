package com.susu.feature.community.community

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.LogoIcon
import com.susu.core.designsystem.component.appbar.icon.SearchIcon
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.component.button.SusuFloatingButton
import com.susu.core.designsystem.component.button.XSmallButtonStyle
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray20
import com.susu.core.designsystem.theme.Gray30
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.feature.community.R
import com.susu.feature.community.community.component.MostPopularVoteCard
import com.susu.feature.community.community.component.VoteCard

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
                                MostPopularVoteCard()
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
                    VoteCard()
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
