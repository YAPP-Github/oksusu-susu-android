package com.susu.feature.envelopeadd.content.relationship

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.GhostButtonColor
import com.susu.core.designsystem.component.button.MediumButtonStyle
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.component.button.SusuGhostButton
import com.susu.core.designsystem.component.textfieldbutton.SusuTextFieldFillMaxButton
import com.susu.core.designsystem.component.textfieldbutton.TextFieldButtonColor
import com.susu.core.designsystem.component.textfieldbutton.style.MediumTextFieldButtonStyle
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.model.Relationship
import com.susu.core.ui.SnackbarToken
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.feature.sent.R
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

@Composable
fun RelationshipContentRoute(
    viewModel: RelationshipViewModel = hiltViewModel(),
    updateParentSelectedRelation: (Relationship?) -> Unit = {},
    onShowSnackbar: (SnackbarToken) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is RelationShipSideEffect.UpdateParentSelectedRelationShip -> {
                updateParentSelectedRelation(sideEffect.relationShip)
            }

            RelationShipSideEffect.FocusCustomRelationShip -> scope.launch {
                awaitFrame()
                focusRequester.requestFocus()
            }

            RelationShipSideEffect.ShowNotValidSnackbar -> onShowSnackbar(
                SnackbarToken(
                    message = context.getString(R.string.sent_snackbar_relationship_validation),
                    extraPadding = PaddingValues(bottom = 60.dp),
                ),
            )
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getRelationShipConfig()
    }

    LaunchedEffect(
        key1 = uiState.selectedRelationship,
        key2 = uiState.isSavedCustomRelationShip,
    ) {
        snapshotFlow { uiState.selectedRelationship }
            .collect {
                viewModel.updateParentSelectedRelationShip()
            }
    }

    RelationshipContent(
        uiState = uiState,
        focusRequester = focusRequester,
        onClickRelationShipButton = viewModel::selectRelationShip,
        onClickCustomRelationShipButton = viewModel::showCustomRelationShipTextField,
        onClickCustomRelationShipTextFieldCloseIcon = viewModel::hideCustomRelationShipTextField,
        onClickCustomRelationShipTextField = viewModel::selectCustomRelationShip,
        onClickCustomRelationShipTextFieldClearIcon = { viewModel.updateCustomRelationShipText("") },
        onTextChangeCustomRelationShipTextField = viewModel::updateCustomRelationShipText,
        onClickTextFieldInnerButton = viewModel::toggleTextFieldSaved,
    )
}

@Composable
fun RelationshipContent(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(
        horizontal = SusuTheme.spacing.spacing_m,
        vertical = SusuTheme.spacing.spacing_xl,
    ),
    uiState: RelationShipState = RelationShipState(),
    focusRequester: FocusRequester = remember { FocusRequester() },
    onClickRelationShipButton: (Relationship) -> Unit = {},
    onClickCustomRelationShipButton: () -> Unit = {},
    onClickCustomRelationShipTextFieldCloseIcon: () -> Unit = {},
    onClickCustomRelationShipTextField: () -> Unit = {},
    onClickCustomRelationShipTextFieldClearIcon: () -> Unit = {},
    onTextChangeCustomRelationShipTextField: (String) -> Unit = {},
    onClickTextFieldInnerButton: () -> Unit = {},
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(scrollState),
    ) {
        Text(
            text = stringResource(id = R.string.sent_envelope_add_relationship_title),
            style = SusuTheme.typography.title_m,
            color = Gray100,
        )
        Spacer(
            modifier = modifier
                .size(SusuTheme.spacing.spacing_xxl),
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
        ) {
            uiState.relationshipConfig.forEach { relationship ->
                if (relationship == uiState.selectedRelationship) {
                    SusuFilledButton(
                        color = FilledButtonColor.Orange,
                        style = MediumButtonStyle.height60,
                        text = relationship.relation,
                        modifier = modifier.fillMaxWidth(),
                    )
                } else {
                    SusuGhostButton(
                        color = GhostButtonColor.Black,
                        style = MediumButtonStyle.height60,
                        text = relationship.relation,
                        onClick = {
                            onClickRelationShipButton(relationship)
                        },
                        rippleEnabled = false,
                        modifier = modifier.fillMaxWidth(),
                    )
                }
            }
            if (uiState.showTextFieldButton) {
                SusuTextFieldFillMaxButton(
                    color = if (uiState.isCustomRelationShipSelected) TextFieldButtonColor.Orange else TextFieldButtonColor.Black,
                    text = uiState.customRelationship.customRelation ?: "",
                    onTextChange = onTextChangeCustomRelationShipTextField,
                    focusRequester = focusRequester,
                    style = MediumTextFieldButtonStyle.height60,
                    isSaved = uiState.isSavedCustomRelationShip,
                    isFocused = uiState.customRelationship == uiState.selectedRelationship,
                    placeholder = stringResource(com.susu.core.ui.R.string.word_input_placeholder),
                    onClickCloseIcon = onClickCustomRelationShipTextFieldCloseIcon,
                    onClickClearIcon = onClickCustomRelationShipTextFieldClearIcon,
                    onClickButton = { onClickCustomRelationShipTextField() },
                    onClickFilledButton = onClickTextFieldInnerButton,
                )
            } else {
                SusuGhostButton(
                    modifier = Modifier.fillMaxWidth(),
                    color = GhostButtonColor.Black,
                    style = MediumButtonStyle.height60,
                    text = stringResource(com.susu.core.ui.R.string.word_input_yourself),
                    rippleEnabled = false,
                    onClick = onClickCustomRelationShipButton,
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F6F6)
@Composable
fun RelationshipContentPreview() {
    SusuTheme {
        RelationshipContent()
    }
}
