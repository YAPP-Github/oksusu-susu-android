package com.susu.feature.received.ledgeredit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.component.button.AddConditionButton
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.SmallButtonStyle
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.component.textfieldbutton.SusuTextFieldWrapContentButton
import com.susu.core.designsystem.component.textfieldbutton.TextFieldButtonColor
import com.susu.core.designsystem.component.textfieldbutton.style.SmallTextFieldButtonStyle
import com.susu.core.designsystem.theme.Gray80
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.util.AnnotatedText
import com.susu.feature.received.R
import com.susu.feature.received.ledgeredit.component.LedgerEditContainer

@Composable
fun LedgerEditRoute(
    @Suppress("unused")
    popBackStack: () -> Unit,
) {
    LedgerEditScreen()
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LedgerEditScreen() {
    Column(
        modifier = Modifier
            .background(SusuTheme.colorScheme.background15)
            .fillMaxSize(),
    ) {
        SusuDefaultAppBar(
            leftIcon = {
                BackIcon()
            },
        )

        Spacer(modifier = Modifier.size(23.dp))

        Column(
            modifier = Modifier
                .padding(horizontal = SusuTheme.spacing.spacing_m),
        ) {
            LedgerEditContainer(
                name = stringResource(id = com.susu.core.ui.R.string.word_event_name),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Text(
                        text = "고모부 장례식",
                        style = SusuTheme.typography.title_m,
                    )
                },
            )

            LedgerEditContainer(
                name = stringResource(id = com.susu.core.ui.R.string.word_category),
                verticalAlignment = Alignment.Top,
                content = {
                    FlowRow(
                        verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
                        horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
                    ) {
                        SusuFilledButton(
                            color = FilledButtonColor.Orange,
                            style = SmallButtonStyle.height32,
                            text = "결혼식",
                        )

                        SusuFilledButton(
                            color = FilledButtonColor.Orange,
                            style = SmallButtonStyle.height32,
                            text = "돌잔치",
                        )

                        SusuFilledButton(
                            color = FilledButtonColor.Orange,
                            style = SmallButtonStyle.height32,
                            text = "장례식",
                        )

                        SusuFilledButton(
                            color = FilledButtonColor.Orange,
                            style = SmallButtonStyle.height32,
                            text = "생일 기념일",
                        )

                        AddConditionButton(onClick = {})

                        SusuTextFieldWrapContentButton(
                            color = TextFieldButtonColor.Orange,
                            style = SmallTextFieldButtonStyle.height32,
                            text = "친척 장례식",
                            isSaved = true,
                        )
                    }
                },
            )

            LedgerEditContainer(
                name = stringResource(com.susu.core.ui.R.string.word_date),
                verticalAlignment = Alignment.Top,
                content = {
                    Column {
                        AnnotatedText(
                            originalText = stringResource(R.string.ledger_edit_screen_from_date, 2023, 11, 25),
                            targetTextList = listOf(
                                stringResource(R.string.ledger_edit_screen_year),
                                stringResource(R.string.ledger_edit_screen_month),
                                stringResource(R.string.ledger_edit_screen_from_day),
                            ),
                            originalTextStyle = SusuTheme.typography.title_m,
                            spanStyle = SusuTheme.typography.title_m.copy(Gray80).toSpanStyle(),
                        )
                        AnnotatedText(
                            originalText = stringResource(R.string.ledger_edit_screen_until_date, 2023, 11, 25),
                            targetTextList = listOf(
                                stringResource(R.string.ledger_edit_screen_year),
                                stringResource(R.string.ledger_edit_screen_month),
                                stringResource(R.string.ledger_edit_screen_until_day),
                            ),
                            originalTextStyle = SusuTheme.typography.title_m,
                            spanStyle = SusuTheme.typography.title_m.copy(Gray80).toSpanStyle(),
                        )
                    }
                },
            )
        }
    }
}

@Preview
@Composable
fun ReceivedScreenPreview() {
    SusuTheme {
        LedgerEditScreen()
    }
}
