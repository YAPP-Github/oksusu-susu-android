package com.susu.feature.envelopeadd.content.money

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.susu.core.ui.SnackbarToken
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.extension.toMoneyFormat
import com.susu.core.ui.moneyList
import com.susu.feature.sent.R

@Composable
fun MoneyContentRoute(
    viewModel: MoneyViewModel = hiltViewModel(),
    updateParentMoney: (Long) -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is MoneyEffect.UpdateParentMoney -> updateParentMoney(sideEffect.money)
            MoneyEffect.ShowNotValidSnackbar -> onShowSnackbar(
                SnackbarToken(message = context.getString(R.string.sent_snackbar_money_validation)),
            )
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
    padding: PaddingValues = PaddingValues(
        horizontal = SusuTheme.spacing.spacing_m,
        vertical = SusuTheme.spacing.spacing_xl,
    ),
    onTextChangeMoney: (String) -> Unit = {},
    onClickMoneyButton: (Int) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        Text(
            text = stringResource(R.string.sent_envelope_add_money_title),
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
            maxLines = 2,
            placeholder = stringResource(R.string.sent_envelope_add_money_placeholder),
        )
        Spacer(
            modifier = Modifier.size(SusuTheme.spacing.spacing_xxl),
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
                    onClick = { onClickMoneyButton(money) },
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
