package com.susu.feature.community.voteadd

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.component.appbar.icon.DeleteText
import com.susu.core.designsystem.component.appbar.icon.EditText
import com.susu.core.designsystem.component.appbar.icon.RegisterText
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.component.button.XSmallButtonStyle
import com.susu.core.designsystem.component.textfield.SusuBasicTextField
import com.susu.core.designsystem.component.textfieldbutton.SusuTextFieldFillMaxButton
import com.susu.core.designsystem.component.textfieldbutton.TextFieldButtonColor
import com.susu.core.designsystem.component.textfieldbutton.style.MediumTextFieldButtonStyle
import com.susu.core.designsystem.component.textfieldbutton.style.TextFieldButtonStyle
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Orange60
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.feature.community.R

@Composable
fun VoteAddRoute() {
    VoteAddScreen()
}

@Composable
fun VoteAddScreen(
    onClickBack: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SusuTheme.colorScheme.background10),
    ) {
        SusuDefaultAppBar(
            leftIcon = {
                BackIcon(
                    onClick = onClickBack,
                )
            },
            title = stringResource(R.string.vote_add_screen_title),
            actions = {
                RegisterText(
                    modifier = Modifier.padding(end = SusuTheme.spacing.spacing_m),
                )
            },
        )

        Column(
            modifier = Modifier
                .padding(SusuTheme.spacing.spacing_m)
                .weight(1f, fill = false)
                .verticalScroll(rememberScrollState()),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxxxs)
            ) {
                listOf("결혼식", "장례식", "돌잔치", "생일 기념일", "자유").forEach {
                    SusuFilledButton(
                        color = FilledButtonColor.Black,
                        style = XSmallButtonStyle.height28,
                        text = it,
                    )
                }
            }

            Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_m))

            SusuBasicTextField(
                text = "",
                textStyle = SusuTheme.typography.text_xxs,
                maxLines = 10,
                placeholder = stringResource(R.string.vote_add_screen_textfield_placeholder),
            )

            Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xl))

            Column(
                verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs)
            ) {
                repeat(5) {
                    SusuTextFieldFillMaxButton(
                        style = MediumTextFieldButtonStyle.height52,
                        color = TextFieldButtonColor.Gray,
                        placeholder = stringResource(R.string.vote_add_screen_textfield_button_placeholder),
                    )
                }
            }
        }

        Icon(
            modifier = Modifier
                .imePadding()
                .clip(CircleShape)
                .background(Orange60)
                .padding(SusuTheme.spacing.spacing_xxxxs)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = com.susu.core.designsystem.R.drawable.ic_floating_button_add),
            contentDescription = stringResource(R.string.vote_add_screen_content_description_vote_add_button),
            tint = Gray10,
        )
    }
}

@Preview
@Composable
fun VoteAddScreenPreview() {
    SusuTheme {
        VoteAddScreen()
    }
}
