package com.susu.feature.received.ledgeradd.content.date

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.bottomsheet.datepicker.SusuDatePickerBottomSheet
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.feature.received.R
import com.susu.feature.received.ledgeradd.content.date.component.SelectDateRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = SusuTheme.spacing.spacing_xl,
                start = SusuTheme.spacing.spacing_m,
                end = SusuTheme.spacing.spacing_m,
            ),
    ) {
        // TODO Annotated Text 사용
        Text(
            text = "고모부의 장례식은 언제인가요",
            style = SusuTheme.typography.title_m,
        )

        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_m))

        SelectDateRow(
            suffix = stringResource(R.string.ledger_add_screen_from),
        )

        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxs))

        SelectDateRow(
            suffix = stringResource(R.string.ledger_add_screen_until),
        )
    }

    SusuDatePickerBottomSheet(maximumContainerHeight = 346.dp)
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F6F6)
@Composable
fun DateContentPreview() {
    SusuTheme {
        DateContent()
    }
}
