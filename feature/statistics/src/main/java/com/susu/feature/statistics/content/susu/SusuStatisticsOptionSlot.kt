package com.susu.feature.statistics.content.susu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.text.AnimatedCounterText
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray50
import com.susu.core.designsystem.theme.Gray80
import com.susu.core.designsystem.theme.Orange10
import com.susu.core.designsystem.theme.Orange60
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable
import com.susu.feature.statistics.R

@Composable
fun SusuStatisticsOptionSlot(
    modifier: Modifier = Modifier,
    age: String = "",
    relationship: String = "",
    category: String = "",
    money: Long = 0L,
    title: String = "",
    isBlind: Boolean = false,
    onAgeClick: () -> Unit = {},
    onRelationshipClick: () -> Unit = {},
    onCategoryClick: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxWidth()
            .background(color = Gray10, shape = RoundedCornerShape(4.dp))
            .padding(SusuTheme.spacing.spacing_m),
    ) {
        Text(
            text = title,
            style = SusuTheme.typography.title_xxs,
            color = Gray50,
        )
        Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xxs))
        Column(
            modifier = Modifier.fillMaxWidth().background(color = Orange10, shape = RoundedCornerShape(4.dp))
                .padding(horizontal = SusuTheme.spacing.spacing_s, vertical = SusuTheme.spacing.spacing_xxs),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OptionSlot(text = age, onClick = onAgeClick)
                Spacer(modifier = Modifier.width(SusuTheme.spacing.spacing_xxxxs))
                Text(
                    text = stringResource(R.string.word_statistics_is),
                    style = SusuTheme.typography.title_xxs,
                    color = Gray80,
                )
                Spacer(modifier = Modifier.width(SusuTheme.spacing.spacing_m))
                OptionSlot(text = relationship, onClick = onRelationshipClick)
                Spacer(modifier = Modifier.width(SusuTheme.spacing.spacing_xxxxs))
                OptionSlot(text = category, onClick = onCategoryClick)
                Spacer(modifier = Modifier.width(SusuTheme.spacing.spacing_xxxxs))
                Text(
                    text = stringResource(R.string.word_statistics_to),
                    style = SusuTheme.typography.title_xxs,
                    color = Gray80,
                )
            }
            Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xxs))
            Row(
                verticalAlignment = Alignment.Bottom,
            ) {
                if (isBlind) {
                    Text(
                        text = stringResource(id = R.string.word_unknown) +
                            stringResource(id = com.susu.core.designsystem.R.string.money_unit),
                        style = SusuTheme.typography.title_s,
                        color = Orange60,
                    )
                } else {
                    AnimatedCounterText(
                        number = money,
                        style = SusuTheme.typography.title_s,
                        color = Orange60,
                        postfix = stringResource(id = com.susu.core.designsystem.R.string.money_unit),
                    )
                }

                Spacer(modifier = Modifier.width(SusuTheme.spacing.spacing_xxs))
                Text(
                    text = stringResource(R.string.word_statistics_send),
                    style = SusuTheme.typography.title_xxs,
                    color = Gray80,
                )
            }
        }
    }
}

@Composable
fun OptionSlot(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier.background(color = Gray10, shape = RoundedCornerShape(4.dp))
            .susuClickable(onClick = onClick)
            .padding(
                horizontal = SusuTheme.spacing.spacing_xxs,
                vertical = SusuTheme.spacing.spacing_xxxxs,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = text, color = Orange60, style = SusuTheme.typography.title_xxxs)
        Spacer(modifier = Modifier.width(SusuTheme.spacing.spacing_xxxxs))
        Icon(
            painter = painterResource(id = R.drawable.ic_statistics_arrow_down),
            tint = Orange60,
            contentDescription = stringResource(R.string.word_select_option),
        )
    }
}

@Preview
@Composable
fun SusuStatisticsOptionSlotPreview() {
    SusuTheme {
        SusuStatisticsOptionSlot(
            title = "제목",
            age = "20대",
            relationship = "친구",
            category = "결혼식",
            money = 50000,
        )
    }
}
