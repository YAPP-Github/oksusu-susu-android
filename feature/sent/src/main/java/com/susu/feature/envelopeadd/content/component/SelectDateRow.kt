package com.susu.feature.envelopeadd.content.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray30
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.util.AnnotatedText
import com.susu.feature.sent.R
import java.time.LocalDateTime

private val currentDate = LocalDateTime.now()

@Composable
fun SelectDateRow(
    modifier: Modifier = Modifier,
    year: Int? = null,
    month: Int? = null,
    day: Int? = null,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
        verticalAlignment = Alignment.Bottom,
    ) {
        if (year == null || month == null || day == null) {
            AnnotatedText(
                originalText = stringResource(
                    R.string.sent_envelope_add_date,
                    currentDate.year,
                    currentDate.month.value,
                    currentDate.dayOfMonth,
                ),
                targetTextList = listOf(
                    currentDate.year.toString(),
                    currentDate.month.value.toString(),
                    currentDate.dayOfMonth.toString(),
                ),
                originalTextStyle = SusuTheme.typography.title_xl,
                spanStyle = SusuTheme.typography.title_xl.copy(Gray30).toSpanStyle(),
            )
        } else {
            Text(
                text = stringResource(
                    R.string.sent_envelope_add_date,
                    year,
                    month,
                    day,
                ),
                style = SusuTheme.typography.title_xl,
                color = Gray100,
            )
        }
    }
}

@Preview
@Composable
fun SelectDateRowPreview() {
    SusuTheme {
        SelectDateRow()
    }
}
