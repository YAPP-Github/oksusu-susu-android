package com.susu.core.designsystem.component.snackbar

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.R
import com.susu.core.designsystem.theme.Blue40
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable

@Composable
fun SusuSnackbar(
    visible: Boolean,
    message: String,
    @DrawableRes actionIconId: Int? = null,
    actionIconContentDescription: String? = null,
    actionButtonText: String? = null,
    onClickActionButton: () -> Unit = {},
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp),
            contentAlignment = Alignment.BottomCenter,
        ) {
            SusuSnackbarContent(
                modifier = Modifier.imePadding(),
                message = message,
                actionIconId = actionIconId,
                actionIconContentDescription = actionIconContentDescription,
                actionButtonText = actionButtonText,
                onClickActionButton = onClickActionButton,
            )
        }
    }
}

@Composable
private fun SusuSnackbarContent(
    modifier: Modifier = Modifier,
    message: String,
    @DrawableRes actionIconId: Int? = null,
    actionIconContentDescription: String? = null,
    actionButtonText: String? = null,
    onClickActionButton: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .padding(SusuTheme.spacing.spacing_xl)
            .fillMaxWidth()
            .background(
                color = Color(0xF5333333),
                shape = RoundedCornerShape(4.dp),
            )
            .padding(
                horizontal = SusuTheme.spacing.spacing_m,
                vertical = SusuTheme.spacing.spacing_xxs,
            ),
        horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_m),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = message,
            style = SusuTheme.typography.text_xxs,
            color = Gray10,
        )

        if (actionIconId != null) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .susuClickable(
                        rippleEnabled = false,
                        onClick = onClickActionButton,
                    ),
                painter = painterResource(id = actionIconId),
                contentDescription = actionIconContentDescription,
                tint = Blue40,
            )
        }

        if (actionButtonText != null) {
            Text(
                modifier = Modifier.susuClickable(
                    rippleEnabled = false,
                    onClick = onClickActionButton,
                ),
                text = actionButtonText,
                style = SusuTheme.typography.title_xxs,
                color = Blue40,
            )
        }
    }
}

@Preview
@Composable
fun SusuSnackbarPreview() {
    SusuTheme {
        Column {
            SusuSnackbarContent(
                message = "텍스트 메세지를 입력하세요",
            )
            SusuSnackbarContent(
                message = "텍스트 메세지를 입력하세요 텍스트 메세지를 입력하세요 텍스트 메세지를 입력하세요",
            )
            SusuSnackbarContent(
                message = "텍스트 메세지를 입력하세요",
                actionButtonText = "액션 버튼",
            )
            SusuSnackbarContent(
                message = "텍스트 메세지를 입력하세요 텍스트 메세지를 입력하세요 텍스트 메세지를 입력하세요",
                actionButtonText = "액션 버튼",
            )
            SusuSnackbarContent(
                message = "텍스트 메세지를 입력하세요",
                actionIconId = R.drawable.ic_floating_button_add,
            )
            SusuSnackbarContent(
                message = "텍스트 메세지를 입력하세요 텍스트 메세지를 입력하세요 텍스트 메세지를 입력하세요",
                actionIconId = R.drawable.ic_floating_button_add,
            )
        }
    }
}
