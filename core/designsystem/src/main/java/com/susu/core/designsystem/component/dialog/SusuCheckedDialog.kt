package com.susu.core.designsystem.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.GhostButtonColor
import com.susu.core.designsystem.component.button.SmallButtonStyle
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.component.button.SusuGhostButton
import com.susu.core.designsystem.component.util.CheckCircle
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.Gray80
import com.susu.core.designsystem.theme.SusuTheme

@Composable
fun SusuCheckedDialog(
    modifier: Modifier = Modifier,
    title: String? = null,
    text: String? = null,
    defaultChecked: Boolean = false, // dialog 노출 시 기본 체크 상태
    checkboxText: String = "",
    confirmText: String = "",
    dismissText: String? = null,
    isDimmed: Boolean = true,
    textAlign: TextAlign = TextAlign.Center,
    onConfirmRequest: (isChecked: Boolean) -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {
    val rootModifier = modifier
        .fillMaxSize()
        .background(color = if (isDimmed) Color.Black.copy(alpha = 0.16f) else Color.Transparent)
        .padding(horizontal = SusuTheme.spacing.spacing_xl)
    var isChecked by remember { mutableStateOf(defaultChecked) }

    Box(
        modifier = rootModifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
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
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CheckCircle(
                    isChecked = isChecked,
                    onCheckedChange = { isChecked = it },
                )
                Spacer(modifier = Modifier.width(SusuTheme.spacing.spacing_xxxxs))
                Text(
                    text = checkboxText,
                    style = SusuTheme.typography.title_xxs,
                    color = if (isChecked) Gray100 else Gray40,
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
                    onClick = { onConfirmRequest(isChecked) },
                )
            }
        }
    }
}

@Preview
@Composable
fun SusuCheckedDialogPreview() {
    SusuTheme {
        SusuCheckedDialog(
            title = "제목",
            text = "본문",
            defaultChecked = true,
            checkboxText = "체크박스 메세지",
            confirmText = "확인했어요",
        )
    }
}

@Preview
@Composable
fun SusuCheckedDialogFalsePreview() {
    SusuTheme {
        SusuCheckedDialog(
            title = "제목",
            text = "본문",
            checkboxText = "체크박스 메세지",
            confirmText = "확인",
            dismissText = "닫기",
        )
    }
}
