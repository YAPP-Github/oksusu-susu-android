package com.susu.feature.sent.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.component.button.XSmallButtonStyle
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray20
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.feature.sent.R

@Composable
fun SentHistoryCard(
    modifier: Modifier = Modifier,
    historyCount: Int,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = SusuTheme.spacing.spacing_xxs),
        colors = CardDefaults.cardColors(
            containerColor = Gray10,
        ),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(
            width = 1.dp,
            color = Gray20,
        )
    ) {
        Column(
            modifier = modifier
                .padding(
                    top = SusuTheme.spacing.spacing_xl,
                    bottom = SusuTheme.spacing.spacing_m,
                    start = SusuTheme.spacing.spacing_m,
                    end = SusuTheme.spacing.spacing_m,
                ),
        ) {
            repeat(historyCount) {
                SentHistoryItem(isSent = true)
                Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_xxs))
            }
            Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_xxs))
            SusuFilledButton(
                color = FilledButtonColor.Black,
                style = XSmallButtonStyle.height44,
                text = stringResource(R.string.sent_screen_envelope_history_show_all),
                modifier = modifier
                    .fillMaxWidth(),
                onClick = onClick,
            )
        }
    }
}
