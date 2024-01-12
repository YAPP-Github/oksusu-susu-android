package com.susu.feature.loginsignup.signup.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.button.GhostButtonColor
import com.susu.core.designsystem.component.button.MediumButtonStyle
import com.susu.core.designsystem.component.button.SusuGhostButton
import com.susu.core.designsystem.theme.Gray60
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.feature.loginsignup.signup.Gender
import java.time.LocalDate

@Composable
fun AdditionalContent(
    modifier: Modifier = Modifier,
    description: String = "",
    selectedGender: Gender = Gender.NONE,
    selectedYear: Int = -1,
    onGenderSelect: (Gender) -> Unit = {},
    onYearClick: () -> Unit = {},
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 24.dp),
    ) {
        Text(text = description, style = SusuTheme.typography.title_m)
        Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xxl))
        Text(text = "성별", style = SusuTheme.typography.title_xxxs, color = Gray60)
        Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xxs))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
        ) {
            SusuGhostButton(
                modifier = Modifier.weight(1f),
                text = stringResource(com.susu.core.ui.R.string.word_male),
                color = GhostButtonColor.Black,
                style = MediumButtonStyle.height60,
                isClickable = true,
                isActive = selectedGender == Gender.MALE,
                onClick = { onGenderSelect(Gender.MALE) },
            )
            SusuGhostButton(
                modifier = Modifier.weight(1f),
                text = stringResource(com.susu.core.ui.R.string.word_female),
                color = GhostButtonColor.Black,
                style = MediumButtonStyle.height60,
                isClickable = true,
                isActive = selectedGender == Gender.FEMALE,
                onClick = { onGenderSelect(Gender.FEMALE) },
            )
        }
        Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xl))
        Text(text = stringResource(com.susu.core.ui.R.string.word_birth), style = SusuTheme.typography.title_xxxs, color = Gray60)
        SusuGhostButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(
                id = com.susu.core.designsystem.R.string.word_year_format,
                if (selectedYear < 0) LocalDate.now().year else selectedYear,
            ),
            color = GhostButtonColor.Black,
            style = MediumButtonStyle.height60,
            isClickable = true,
            isActive = selectedYear > 0,
            onClick = onYearClick,
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun AdditionalContentPreview() {
    SusuTheme {
        AdditionalContent(
            modifier = Modifier.fillMaxSize(),
            selectedGender = Gender.MALE,
        )
    }
}
