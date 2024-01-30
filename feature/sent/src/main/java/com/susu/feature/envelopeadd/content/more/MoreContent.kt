package com.susu.feature.envelopeadd.content.more

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
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray70
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.feature.envelopeadd.EnvelopeAddStep
import com.susu.feature.sent.R

@Composable
fun MoreContentRoute(
    viewModel: MoreViewModel = hiltViewModel(),
    updateParentMoreStep: (List<EnvelopeAddStep>) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is MoreSideEffect.UpdateParentMoreStep -> updateParentMoreStep(sideEffect.moreStep)
        }
    }

    MoreContent(
        uiState = uiState,
        onClickStepButton = viewModel::toggleStep,
    )
}

@Composable
fun MoreContent(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(
        horizontal = SusuTheme.spacing.spacing_m,
        vertical = SusuTheme.spacing.spacing_xl,
    ),
    uiState: MoreState = MoreState(),
    onClickStepButton: (EnvelopeAddStep) -> Unit = {},
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(scrollState),
    ) {
        Text(
            text = stringResource(id = R.string.sent_envelope_add_more_title),
            style = SusuTheme.typography.title_m,
            color = Gray100,
        )
        Spacer(
            modifier = Modifier
                .size(SusuTheme.spacing.spacing_xxxxs),
        )
        Text(
            text = stringResource(id = R.string.sent_envelope_add_more_subtitle),
            style = SusuTheme.typography.text_xs,
            color = Gray70,
        )
        Spacer(
            modifier = Modifier
                .size(SusuTheme.spacing.spacing_xxl),
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
        ) {
            moreStep.forEach { (step, stringRes) ->
                if (step in uiState.selectedMoreStop) {
                    SusuFilledButton(
                        color = FilledButtonColor.Orange,
                        style = MediumButtonStyle.height60,
                        text = stringResource(id = stringRes),
                        onClick = {
                            onClickStepButton(step)
                        },
                        modifier = modifier.fillMaxWidth(),
                    )
                } else {
                    SusuGhostButton(
                        color = GhostButtonColor.Black,
                        style = MediumButtonStyle.height60,
                        text = stringResource(id = stringRes),
                        onClick = {
                            onClickStepButton(step)
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
fun MoreContentPreview() {
    SusuTheme {
        MoreContent()
    }
}
