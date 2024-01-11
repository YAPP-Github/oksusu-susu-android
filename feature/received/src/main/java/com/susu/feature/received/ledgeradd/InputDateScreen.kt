package com.susu.feature.received.ledgeradd

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.susu.core.designsystem.theme.Gray80
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.util.AnnotatedText
import com.susu.feature.received.R

// TODO 디자인 변경 예정
@Composable
fun InputDateScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = SusuTheme.spacing.spacing_xl,
                start = SusuTheme.spacing.spacing_m,
                end = SusuTheme.spacing.spacing_m,
            ),
    ) {
        Text(
            text = "경조사 기간을 입력해주세요",
            style = SusuTheme.typography.title_m,
        )

        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_m))

        AnnotatedText(
            originalText = stringResource(R.string.ledger_edit_screen_from_date, 2023, 11, 25),
            targetTextList = listOf(
                stringResource(R.string.ledger_edit_screen_year),
                stringResource(R.string.ledger_edit_screen_month),
                stringResource(R.string.ledger_edit_screen_from_day),
            ),
            originalTextStyle = SusuTheme.typography.title_xl,
            spanStyle = SusuTheme.typography.title_xl.copy(Gray80).toSpanStyle(),
        )
        AnnotatedText(
            originalText = stringResource(R.string.ledger_edit_screen_until_date, 2023, 11, 25),
            targetTextList = listOf(
                stringResource(R.string.ledger_edit_screen_year),
                stringResource(R.string.ledger_edit_screen_month),
                stringResource(R.string.ledger_edit_screen_until_day),
            ),
            originalTextStyle = SusuTheme.typography.title_xl,
            spanStyle = SusuTheme.typography.title_xl.copy(Gray80).toSpanStyle(),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F6F6)
@Composable
fun InputDateScreenPreview() {
    SusuTheme {
        InputDateScreen()
    }
}
