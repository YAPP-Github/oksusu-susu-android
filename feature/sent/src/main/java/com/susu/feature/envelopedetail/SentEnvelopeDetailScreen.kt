package com.susu.feature.envelopedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable
import com.susu.feature.envelopedetail.component.DetailItem

@Composable
fun SentEnvelopeDetailScreen(
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .background(SusuTheme.colorScheme.background10),
    ) {
        Column {
            SusuDefaultAppBar(
                leftIcon = {
                    BackIcon()
                },
                actions = {
                    Text(
                        text = stringResource(id = com.susu.core.ui.R.string.word_edit),
                        style = SusuTheme.typography.title_xxs,
                        color = Gray100,
                        modifier = modifier
                            .susuClickable(
                                rippleEnabled = false,
                                onClick = {},
                            ),
                    )
                    Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_m))
                    Text(
                        text = stringResource(id = com.susu.core.ui.R.string.word_delete),
                        style = SusuTheme.typography.title_xxs,
                        color = Gray100,
                        modifier = modifier
                            .susuClickable(
                                rippleEnabled = false,
                                onClick = {},
                            ),
                    )
                    Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_m))
                },
            )
            // TODO: text 수정
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(
                        start = SusuTheme.spacing.spacing_m,
                        end = SusuTheme.spacing.spacing_m,
                        top = SusuTheme.spacing.spacing_xl,
                    )
                    .verticalScroll(scrollState),
            ) {
                Text(
                    text = "150,000원",
                    style = SusuTheme.typography.title_xxl,
                    color = Gray100,
                )
                Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_m))
                Column {
                    DetailItem(
                        categoryText = "경조사",
                        contentText = "결혼식",
                        isEmptyContent = false,
                    )
                    DetailItem(
                        categoryText = "이름",
                        contentText = "김철수",
                        isEmptyContent = false,
                    )
                    DetailItem(
                        categoryText = "나와의 관계",
                        contentText = "친구",
                        isEmptyContent = false,
                    )
                    DetailItem(
                        categoryText = "날짜",
                        contentText = "2023년 11월 25일",
                        isEmptyContent = false,
                    )
                    DetailItem(
                        categoryText = "방문 여부",
                        contentText = "예",
                        isEmptyContent = false,
                    )
                    DetailItem(
                        categoryText = "선물",
                        contentText = "한끼 식사",
                        isEmptyContent = true,
                    )
                    DetailItem(
                        categoryText = "연락처",
                        contentText = "01012345678",
                        isEmptyContent = true,
                    )
                    DetailItem(
                        categoryText = "메모",
                        contentText = "가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하",
                        isEmptyContent = true,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SentEnvelopeDetailScreenPreview() {
    SusuTheme {
        SentEnvelopeDetailScreen()
    }
}
