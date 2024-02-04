package com.susu.core.designsystem.component.dialog

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.GhostButtonColor
import com.susu.core.designsystem.component.button.SmallButtonStyle
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.component.button.SusuGhostButton
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray80
import com.susu.core.designsystem.theme.SusuTheme

@Composable
fun SusuDialog(
    title: String? = null,
    text: String? = null,
    confirmText: String = "",
    dismissText: String? = null,
    textAlign: TextAlign = TextAlign.Center,
    onConfirmRequest: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Gray10, shape = RoundedCornerShape(8.dp))
                .padding(SusuTheme.spacing.spacing_xl),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            title?.let {
                Text(
                    text = it,
                    style = SusuTheme.typography.title_xs,
                    color = Gray100,
                    maxLines = 2,
                )
                Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xxs))
            }
            text?.let {
                Text(
                    text = it,
                    style = SusuTheme.typography.text_xxs,
                    color = Gray80,
                    maxLines = 4,
                    textAlign = textAlign,
                )
            }
            Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xl))
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                dismissText?.let {
                    SusuGhostButton(
                        modifier = Modifier.weight(1f),
                        color = GhostButtonColor.Black,
                        style = SmallButtonStyle.height40,
                        text = it,
                        onClick = onDismissRequest,
                    )
                    Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xxs))
                }
                SusuFilledButton(
                    modifier = Modifier.weight(1f),
                    color = FilledButtonColor.Orange,
                    style = SmallButtonStyle.height40,
                    text = confirmText,
                    onClick = onConfirmRequest,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SusuDialogPreview() {
    SusuTheme {
        SusuDialog(
            title = "제목",
            text = "무지무지무지무지무지 긴 메세지 본문 무지무지무지무지무지 긴 메세지 본문 무지무지무지무지무지 긴 메세지 본문 무지무지무지무지무지 긴 메세지 본문 무지무지무지무지무지 긴 메세지 본문 무지무지무지무지무지 긴 메세지 본문",
            confirmText = "확인했어요",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SusuDialogLongTitlePreview() {
    SusuTheme {
        SusuDialog(
            title = "무지무지무지무지무지무지무지무지무지무지무지무지무지무지무지무지무지무지무지무지무지무지무지무지 긴 제목",
            text = "텍스트 메세지를 입력하세요",
            confirmText = "확인",
            dismissText = "닫기",
        )
    }
}
