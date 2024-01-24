package com.susu.feature.mypage.info

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.component.bottomsheet.datepicker.SusuYearPickerBottomSheet
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.SmallButtonStyle
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.component.textfield.SusuBasicTextField
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.Gender
import com.susu.core.ui.SnackbarToken
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.extension.susuClickable
import com.susu.feature.mypage.R
import com.susu.feature.mypage.info.component.MyPageInfoItem

@Composable
fun MyPageInfoRoute(
    padding: PaddingValues,
    viewModel: MyPageInfoViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit,
) {
    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            MyPageInfoEffect.PopBackStack -> popBackStack()
            is MyPageInfoEffect.ShowSnackBar -> onShowSnackbar(SnackbarToken(message = sideEffect.msg))
            is MyPageInfoEffect.HandleException -> handleException(sideEffect.throwable, sideEffect.retry)
        }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.getUserInfo()
    }

    BackHandler {
        if (uiState.isEditing) {
            viewModel.cancelEdit()
        } else {
            popBackStack()
        }
    }

    MyPageInfoScreen(
        padding = padding,
        uiState = uiState,
        onNameChanged = viewModel::updateName,
        onBirthClick = viewModel::showDatePicker,
        onBirthSelect = viewModel::updateBirth,
        onGenderSelect = viewModel::updateGender,
        onEditStart = viewModel::startEdit,
        onEditCancel = viewModel::cancelEdit,
        onEditComplete = viewModel::completeEdit,
        popBackStack = popBackStack,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageInfoScreen(
    padding: PaddingValues = PaddingValues(),
    uiState: MyPageInfoState = MyPageInfoState(),
    onNameChanged: (String) -> Unit = {},
    onBirthClick: () -> Unit = {},
    onBirthSelect: (Int) -> Unit = {},
    onGenderSelect: (Gender) -> Unit = {},
    onEditStart: () -> Unit = {},
    onEditCancel: () -> Unit = {},
    onEditComplete: () -> Unit = {},
    popBackStack: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SusuDefaultAppBar(
                leftIcon = {
                    BackIcon(
                        onClick = if (uiState.isEditing) {
                            onEditCancel
                        } else {
                            popBackStack
                        },
                    )
                },
                title = stringResource(id = R.string.mypage_default_my_info),
                actions = {
                    if (uiState.isEditing) {
                        Text(
                            modifier = Modifier
                                .susuClickable(
                                    runIf = uiState.isEditNameValid,
                                    onClick = onEditComplete,
                                )
                                .padding(end = SusuTheme.spacing.spacing_m),
                            text = stringResource(id = com.susu.core.ui.R.string.word_enrollment),
                            style = SusuTheme.typography.title_xxs,
                            color = Gray100,
                        )
                    } else {
                        Text(
                            modifier = Modifier
                                .susuClickable(onClick = onEditStart)
                                .padding(end = SusuTheme.spacing.spacing_m),
                            text = stringResource(id = com.susu.core.ui.R.string.word_edit),
                            style = SusuTheme.typography.title_xxs,
                            color = Gray100,
                        )
                    }
                },
            )
            Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_m))
            Image(
                modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape),
                painter = painterResource(id = com.susu.core.ui.R.drawable.img_default_profile),
                contentDescription = stringResource(R.string.mypage_my_info_profile),
            )
            Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_m))
            MyPageInfoItem(
                title = "이름",
            ) {
                if (uiState.isEditing) {
                    SusuBasicTextField(
                        modifier = Modifier.weight(1f),
                        text = uiState.editName,
                        textStyle = SusuTheme.typography.title_xs.copy(textAlign = TextAlign.End),
                        placeholder = uiState.userName,
                        onTextChange = onNameChanged,
                        textColor = Gray100,
                    )
                } else {
                    Text(
                        text = uiState.userName,
                        style = SusuTheme.typography.title_xs,
                        color = Gray100,
                    )
                }
            }
            MyPageInfoItem(
                title = stringResource(id = com.susu.core.ui.R.string.word_birth),
            ) {
                if (uiState.isEditing) {
                    Text(
                        modifier = Modifier.susuClickable(
                            onClick = onBirthClick,
                        ),
                        text = uiState.editBirth.toString(),
                        style = SusuTheme.typography.title_xs,
                        color = if (uiState.birthEdited) Gray100 else Gray40,
                    )
                } else {
                    Text(text = uiState.userBirth.toString(), style = SusuTheme.typography.title_xs, color = Gray100)
                }
            }
            MyPageInfoItem(
                title = stringResource(id = com.susu.core.ui.R.string.word_gender),
            ) {
                if (uiState.isEditing) {
                    Row(
                        modifier = Modifier.weight(1f),
                    ) {
                        Spacer(modifier = Modifier.width(64.dp))
                        SusuFilledButton(
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = SusuTheme.spacing.spacing_xxxxs),
                            text = stringResource(id = com.susu.core.ui.R.string.word_female),
                            isClickable = true,
                            isActive = uiState.editGender == Gender.FEMALE,
                            color = FilledButtonColor.Orange,
                            style = SmallButtonStyle.height32,
                            onClick = { onGenderSelect(Gender.FEMALE) },
                        )
                        Spacer(modifier = Modifier.width(SusuTheme.spacing.spacing_xxs))
                        SusuFilledButton(
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = SusuTheme.spacing.spacing_xxxxs),
                            text = stringResource(id = com.susu.core.ui.R.string.word_male),
                            isClickable = true,
                            isActive = uiState.editGender == Gender.MALE,
                            color = FilledButtonColor.Orange,
                            style = SmallButtonStyle.height32,
                            onClick = { onGenderSelect(Gender.MALE) },
                        )
                    }
                } else {
                    Text(
                        text = when (uiState.userGender) {
                            Gender.NONE -> stringResource(id = com.susu.core.ui.R.string.word_not_select)
                            Gender.MALE -> stringResource(id = com.susu.core.ui.R.string.word_male)
                            Gender.FEMALE -> stringResource(id = com.susu.core.ui.R.string.word_female)
                        },
                        style = SusuTheme.typography.title_xs,
                        color = Gray100,
                    )
                }
            }
        }

        if (uiState.showDatePicker) {
            SusuYearPickerBottomSheet(
                maximumContainerHeight = 322.dp,
                onDismissRequest = { onBirthSelect(it) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyPageInfoScreenPreview() {
    SusuTheme {
        MyPageInfoScreen()
    }
}
