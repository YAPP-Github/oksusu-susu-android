package com.susu.feature.received.ledgeradd

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.susu.core.designsystem.component.appbar.SusuProgressAppBar
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.MediumButtonStyle
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.R
import kotlinx.coroutines.launch

enum class LedgerAddPage {
    CATEGORY,
    NAME,
    DATE,
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LedgerAddRoute(
    popBackStack: () -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { LedgerAddPage.entries.size })
    val scope = rememberCoroutineScope()

    LedgerAddScreen(
        pagerState = pagerState,
        onClickBack = popBackStack,
        onClickNextButton = {
            // TODO 임시 코드 입니다.
            scope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LedgerAddScreen(
    pagerState: PagerState = rememberPagerState(pageCount = { LedgerAddPage.entries.size }),
    onClickBack: () -> Unit = {},
    onClickNextButton: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .background(SusuTheme.colorScheme.background15)
            .fillMaxSize(),
    ) {
        Column {
            SusuProgressAppBar(
                leftIcon = {
                    BackIcon(onClickBack)
                },
                entireStep = pagerState.pageCount,
                currentStep = pagerState.currentPage + 1,
            )

            HorizontalPager(
                modifier = Modifier.weight(1f),
                state = pagerState,
                userScrollEnabled = false,
            ) { page ->
                when (LedgerAddPage.entries[page]) {
                    LedgerAddPage.CATEGORY -> SelectCategoryScreen()
                    LedgerAddPage.NAME -> InputNameScreen()
                    LedgerAddPage.DATE -> InputDateScreen()
                }
            }

            SusuFilledButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                shape = RectangleShape,
                color = FilledButtonColor.Black,
                style = MediumButtonStyle.height60,
                text = stringResource(id = R.string.word_save),
                onClick = onClickNextButton,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun ReceivedScreenPreview() {
    SusuTheme {
        LedgerAddScreen()
    }
}
