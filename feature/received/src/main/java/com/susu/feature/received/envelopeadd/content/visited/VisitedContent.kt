package com.susu.feature.received.envelopeadd.content.visited

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.susu.core.designsystem.component.button.GhostButtonColor
import com.susu.core.designsystem.component.button.MediumButtonStyle
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.component.button.SusuGhostButton
import com.susu.core.designsystem.theme.Gray60
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.util.AnnotatedText
import com.susu.feature.received.R

@Composable
fun VisitedContentRoute(
    viewModel: VisitedViewModel = hiltViewModel(),
    categoryName: String,
    updateParentVisited: (Boolean?) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is VisitedSideEffect.UpdateParentVisited -> updateParentVisited(sideEffect.visited)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.updateCategoryName(categoryName)
        viewModel.updateVisited(uiState.visited)
    }

    VisitedContent(
        uiState = uiState,
        onClickVisitedButton = { viewModel.updateVisited(true) },
        onClickNotVisitedButton = { viewModel.updateVisited(false) },
    )
}

@Composable
fun VisitedContent(
    uiState: VisitedState = VisitedState(),
    onClickVisitedButton: () -> Unit = {},
    onClickNotVisitedButton: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = SusuTheme.spacing.spacing_m,
                vertical = SusuTheme.spacing.spacing_xl,
            ),
    ) {
        AnnotatedText(
            originalText = stringResource(R.string.visited_content_title, uiState.categoryName),
            originalTextStyle = SusuTheme.typography.title_m,
            targetTextList = listOf(stringResource(R.string.visited_content_title_highlight, uiState.categoryName)),
            spanStyle = SusuTheme.typography.title_m.copy(Gray60).toSpanStyle(),
        )
        Spacer(
            modifier = Modifier
                .size(SusuTheme.spacing.spacing_xxl),
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
        ) {
            if (uiState.visited == true) {
                SusuFilledButton(
                    color = FilledButtonColor.Orange,
                    style = MediumButtonStyle.height60,
                    text = stringResource(id = com.susu.core.ui.R.string.word_yes),
                    modifier = Modifier.fillMaxWidth(),
                )
            } else {
                SusuGhostButton(
                    color = GhostButtonColor.Black,
                    style = MediumButtonStyle.height60,
                    text = stringResource(id = com.susu.core.ui.R.string.word_yes),
                    onClick = onClickVisitedButton,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            if (uiState.visited == false) {
                SusuFilledButton(
                    color = FilledButtonColor.Orange,
                    style = MediumButtonStyle.height60,
                    text = stringResource(id = com.susu.core.ui.R.string.word_no),
                    modifier = Modifier.fillMaxWidth(),
                )
            } else {
                SusuGhostButton(
                    color = GhostButtonColor.Black,
                    style = MediumButtonStyle.height60,
                    text = stringResource(id = com.susu.core.ui.R.string.word_no),
                    onClick = onClickNotVisitedButton,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F6F6)
@Composable
fun VisitedContentPreview() {
    SusuTheme {
        VisitedContent()
    }
}
