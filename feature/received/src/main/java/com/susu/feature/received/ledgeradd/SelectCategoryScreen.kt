package com.susu.feature.received.ledgeradd

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.susu.core.designsystem.component.button.GhostButtonColor
import com.susu.core.designsystem.component.button.MediumButtonStyle
import com.susu.core.designsystem.component.button.SusuGhostButton
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.feature.received.R

@Composable
fun SelectCategoryScreen() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = SusuTheme.spacing.spacing_xl,
                start = SusuTheme.spacing.spacing_m,
                end = SusuTheme.spacing.spacing_m,
            )
            .verticalScroll(scrollState),
    ) {
        Text(
            text = stringResource(R.string.select_category_screen_title),
            style = SusuTheme.typography.title_m,
        )

        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxl))

        Column(
            verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
        ) {
            SusuGhostButton(
                modifier = Modifier.fillMaxWidth(),
                color = GhostButtonColor.Black,
                style = MediumButtonStyle.height60,
                text = "결혼식",
            )

            SusuGhostButton(
                modifier = Modifier.fillMaxWidth(),
                color = GhostButtonColor.Black,
                style = MediumButtonStyle.height60,
                text = "돌잔치",
            )

            SusuGhostButton(
                modifier = Modifier.fillMaxWidth(),
                color = GhostButtonColor.Black,
                style = MediumButtonStyle.height60,
                text = "장례식",
            )

            SusuGhostButton(
                modifier = Modifier.fillMaxWidth(),
                color = GhostButtonColor.Black,
                style = MediumButtonStyle.height60,
                text = "생일 기념일",
            )

            SusuGhostButton(
                modifier = Modifier.fillMaxWidth(),
                color = GhostButtonColor.Black,
                style = MediumButtonStyle.height60,
                text = stringResource(com.susu.core.ui.R.string.word_input_yourself),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F6F6)
@Composable
fun SelectCategoryScreenPreview() {
    SusuTheme {
        SelectCategoryScreen()
    }
}
