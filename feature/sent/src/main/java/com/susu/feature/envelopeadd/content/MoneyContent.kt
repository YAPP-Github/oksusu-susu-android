package com.susu.feature.envelopeadd.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.SmallButtonStyle
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.component.textfield.SusuPriceTextField
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.toMoneyFormat
import com.susu.feature.sent.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MoneyContent(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(
        horizontal = SusuTheme.spacing.spacing_m,
        vertical = SusuTheme.spacing.spacing_xl,
    ),
) {
    val moneyList = listOf(
        10000,
        30000,
        50000,
        100000,
        500000,
    )
    var clickedMoney by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        Text(
            text = stringResource(R.string.sent_envelope_add_money_title),
            style = SusuTheme.typography.title_m,
            color = Gray100,
        )
        Spacer(
            modifier = modifier
                .size(SusuTheme.spacing.spacing_m),
        )
        SusuPriceTextField(
            text = clickedMoney,
            onTextChange = { clickedMoney = it },
            placeholder = stringResource(R.string.sent_envelope_add_money_placeholder),
        )
        Spacer(
            modifier = modifier
                .size(SusuTheme.spacing.spacing_xxl),
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
            verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
        ) {
            for (money in moneyList) {
                SusuFilledButton(
                    color = FilledButtonColor.Orange,
                    style = SmallButtonStyle.height32,
                    text = money.toMoneyFormat() + stringResource(R.string.sent_envelope_add_money_won),
                    onClick = {
                        clickedMoney = money.toString()
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F6F6)
@Composable
fun MoneyContentPreview() {
    SusuTheme {
        MoneyContent()
    }
}
