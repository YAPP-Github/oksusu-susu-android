package com.susu.feature.received.envelopeadd.content.money

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.SmallButtonStyle
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.component.textfield.SusuPriceTextField
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.extension.toMoneyFormat
import com.susu.core.ui.moneyList
import com.susu.feature.received.R

@Composable
fun MoneyContentRoute(
    viewModel: MoneyViewModel = hiltViewModel(),
    updateParentMoney: (Long) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is MoneySideEffect.UpdateParentMoney -> updateParentMoney(sideEffect.money)
        }
    }

    LaunchedEffect(key1 = Unit) {
        updateParentMoney(uiState.money.toLongOrNull() ?: 0)
    }

    MoneyContent(
        uiState = uiState,
        onTextChangeMoney = viewModel::updateMoney,
        onClickMoneyButton = viewModel::addMoney,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MoneyContent(
    uiState: MoneyState = MoneyState(),
    onTextChangeMoney: (String) -> Unit = {},
    onClickMoneyButton: (Int) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = SusuTheme.spacing.spacing_m,
                vertical = SusuTheme.spacing.spacing_xl,
            ),
    ) {
        Text(
            text = stringResource(R.string.money_content_title),
            style = SusuTheme.typography.title_m,
            color = Gray100,
        )
        Spacer(
            modifier = Modifier
                .size(SusuTheme.spacing.spacing_m),
        )
        SusuPriceTextField(
            text = uiState.money,
            onTextChange = onTextChangeMoney,
            placeholder = stringResource(R.string.money_content_placeholder),
        )
        Spacer(
            modifier = Modifier
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
                    text = stringResource(id = com.susu.core.ui.R.string.money_unit_format, money.toMoneyFormat()),
                    onClick = {
                        onClickMoneyButton(money)
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
