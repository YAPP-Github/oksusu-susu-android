package com.susu.feature.envelopeadd.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.susu.core.designsystem.component.textfield.SusuBasicTextField
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.Gray60
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.feature.envelopeadd.content.component.FriendListItem
import com.susu.feature.sent.R

@Composable
fun InputContent(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(
        horizontal = SusuTheme.spacing.spacing_m,
        vertical = SusuTheme.spacing.spacing_xl,
    ),
    name: String? = null,
    titleText: String,
    friendList: List<String> = emptyList(),
    placeholder: String,
) {
    var textFieldContent by remember { mutableStateOf("") }

    val title = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Gray60)) {
            append(name + stringResource(R.string.sent_envelope_add_phone_to))
        }
        withStyle(style = SpanStyle(color = Gray100)) {
            append(titleText)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        if (name == null) {
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

        Spacer(
            modifier = modifier
                .size(SusuTheme.spacing.spacing_m),
        )
        SusuBasicTextField(
            text = textFieldContent,
            onTextChange = { textFieldContent = it },
            placeholder = placeholder,
            placeholderColor = Gray40,
            modifier = modifier.fillMaxWidth(),
            keyboardOptions = if (name == null) KeyboardOptions.Default else KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_xl))

        if (friendList.isNotEmpty()) {
            // TODO: 친구 목록 서버 연동
            LazyColumn() {
                items(friendList) { friend ->
                    FriendListItem(friend)
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F6F6)
@Composable
fun NameContentPreview() {
    SusuTheme {
        val friendList = listOf("김철수", "국영수", "가나다")

        InputContent(
            titleText = "누구에게 보냈나요",
            placeholder = "이름을 입력해주세요",
            friendList = friendList,
        )
    }
}
