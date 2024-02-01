package com.susu.feature.statistics.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.text.AnimatedCounterText
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.Gray50
import com.susu.core.designsystem.theme.Gray60
import com.susu.core.designsystem.theme.Gray80
import com.susu.core.designsystem.theme.Orange60
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.toMoneyFormat
import com.susu.feature.statistics.R

@Composable
fun StatisticsVerticalItem(
    title: String,
    content: String,
    count: Int,
    modifier: Modifier = Modifier,
    isActive: Boolean = true,
) {
    Column(
        modifier = modifier
            .background(color = Gray10, shape = RoundedCornerShape(4.dp))
            .padding(SusuTheme.spacing.spacing_m),
    ) {
        Text(
            modifier = Modifier.align(Alignment.Start),
            text = title,
            style = SusuTheme.typography.title_xxs,
            color = Gray100,
        )
        Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xxs))
        if (isActive) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = content,
                style = SusuTheme.typography.title_l,
                color = Orange60,
            )
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(R.string.word_entire_count, count.toString()),
                style = SusuTheme.typography.title_xxxs,
                color = Gray60,
            )
        } else {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.word_unknown),
                style = SusuTheme.typography.title_l,
                color = Gray40,
            )
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(R.string.word_entire_count, stringResource(id = R.string.word_unknown)),
                style = SusuTheme.typography.title_xxxs,
                color = Gray40,
            )
        }
    }
}

@Composable
fun StatisticsHorizontalItem(
    title: String,
    name: String,
    money: Int,
    modifier: Modifier = Modifier,
    isActive: Boolean = true,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Gray10, shape = RoundedCornerShape(4.dp))
            .padding(SusuTheme.spacing.spacing_m),
    ) {
        Text(
            modifier = Modifier.align(Alignment.Start),
            text = title,
            style = SusuTheme.typography.title_xxs,
            color = Gray50,
        )
        Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xxxxs))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            if (isActive) {
                Text(text = name, style = SusuTheme.typography.title_s, color = Gray80)
                AnimatedCounterText(
                    number = money,
                    style = SusuTheme.typography.title_s,
                    color = Gray100,
                    postfix = stringResource(id = com.susu.core.designsystem.R.string.money_unit),
                )
            } else {
                Text(text = stringResource(id = R.string.word_unknown), style = SusuTheme.typography.title_s, color = Gray40)
                Text(
                    text = stringResource(id = com.susu.core.ui.R.string.money_unit_format, stringResource(id = R.string.word_unknown)),
                    style = SusuTheme.typography.title_s,
                    color = Gray40,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StatisticsItemPreview() {
    SusuTheme {
        Column {
            StatisticsVerticalItem(title = "자주 개발하는 시간", content = "밤", count = 1)
            StatisticsHorizontalItem(title = "이번달에 허투루 쓴 돈", name = "배달음식", money = 60000)
            StatisticsVerticalItem(title = "자주 개발하는 시간", content = "밤", count = 1, isActive = false)
            StatisticsHorizontalItem(title = "이번달에 허투루 쓴 돈", name = "배달음식", money = 60000, isActive = false)
        }
    }
}
