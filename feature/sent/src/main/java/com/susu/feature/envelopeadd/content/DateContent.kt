package com.susu.feature.envelopeadd.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.bottomsheet.datepicker.SusuDatePickerBottomSheet
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray60
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable
import com.susu.feature.envelopeadd.content.component.SelectDateRow
import com.susu.feature.sent.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateContent(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(
        horizontal = SusuTheme.spacing.spacing_m,
        vertical = SusuTheme.spacing.spacing_xl,
    ),
    year: Int? = null,
    month: Int? = null,
    day: Int? = null,
    name: String,
) {
    var isSheetOpen by remember { mutableStateOf(false) }

    val title = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Gray60)) {
            append(name + stringResource(R.string.sent_envelope_add_date_to))
        }
        withStyle(style = SpanStyle(color = Gray100)) {
            append(stringResource(R.string.sent_envelope_add_date_title))
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        Text(
            text = title,
            style = SusuTheme.typography.title_m,
            color = Gray100,
        )
        Spacer(
            modifier = modifier
                .size(SusuTheme.spacing.spacing_m),
        )
        SelectDateRow(
            year = year,
            month = month,
            day = day,
            modifier = modifier.susuClickable(
                rippleEnabled = false,
                onClick = { isSheetOpen = true },
            )
        )
    }

    // DatePickerBottomSheet
    if (isSheetOpen) {
        SusuDatePickerBottomSheet(
            maximumContainerHeight = 346.dp,
            onDismissRequest = { _, _, _ -> isSheetOpen = false },
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F6F6)
@Composable
fun DateContentPreview() {
    SusuTheme {
        DateContent(
            name = "김철수",
        )
    }
}
