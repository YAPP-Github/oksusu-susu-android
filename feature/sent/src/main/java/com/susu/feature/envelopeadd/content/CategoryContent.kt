package com.susu.feature.envelopeadd.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.GhostButtonColor
import com.susu.core.designsystem.component.button.MediumButtonStyle
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.component.button.SusuGhostButton
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray60
import com.susu.core.designsystem.theme.Gray70
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.feature.sent.R

@Composable
fun CategoryContent(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(
        horizontal = SusuTheme.spacing.spacing_m,
        vertical = SusuTheme.spacing.spacing_xl,
    ),
    event: String? = null,
    titleText: String,
    hasSubTitle: Boolean = false,
    subTitleText: String = "",
    categoryList: List<String>,
) {
    val scrollState = rememberScrollState()
    var selectedItem by remember { mutableStateOf(-1) }

    val title = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Gray60)) {
            append(event + stringResource(R.string.sent_envelope_add_visited_to))
        }
        withStyle(style = SpanStyle(color = Gray100)) {
            append(titleText)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(scrollState),
    ) {
        if (event == null) {
            Text(
                text = titleText,
                style = SusuTheme.typography.title_m,
                color = Gray100,
            )
        } else {
            Text(
                text = title,
                style = SusuTheme.typography.title_m,
                color = Gray100,
            )
        }

        if (hasSubTitle) {
            Text(
                text = subTitleText,
                style = SusuTheme.typography.text_xs,
                color = Gray70,
                modifier = modifier.padding(top = SusuTheme.spacing.spacing_xxxxs)
            )
        }
        Spacer(
            modifier = modifier
                .size(SusuTheme.spacing.spacing_xxl),
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
        ) {
            categoryList.forEachIndexed { index, category ->
                if (selectedItem == index) {
                    SusuFilledButton(
                        color = FilledButtonColor.Orange,
                        style = MediumButtonStyle.height60,
                        text = category,
                        onClick = {
                            selectedItem = index
                        },
                        modifier = modifier.fillMaxWidth(),
                    )
                } else {
                    SusuGhostButton(
                        color = GhostButtonColor.Black,
                        style = MediumButtonStyle.height60,
                        text = category,
                        onClick = {
                            selectedItem = index
                        },
                        modifier = modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F6F6)
@Composable
fun CategoryContentPreview() {
    val categoryList = mutableListOf("친구", "가족", "친척", "동료", "직접 입력")

    SusuTheme {
        CategoryContent(
            event = "결혼식",
            titleText = "방문했나요?",
            categoryList = categoryList,
            hasSubTitle = false,
//            subTitleText = "복수로 선택하셔도 좋아요"
        )
    }
}
