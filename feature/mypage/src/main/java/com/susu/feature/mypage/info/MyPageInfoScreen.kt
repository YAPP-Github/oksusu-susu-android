package com.susu.feature.mypage.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.SmallButtonStyle
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.component.textfield.SusuBasicTextField
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable
import com.susu.feature.mypage.info.component.MyPageInfoItem
import com.susu.feature.mypage.main.MyPageViewModel

@Composable
fun MyPageInfoRoute(
    padding: PaddingValues,
    viewModel: MyPageViewModel = hiltViewModel(), // hiltViewModel로 주입하면 백스택에 있는 MyPageViewModel이 오는가? 새로 생성되는가?
    popBackStack: () -> Unit
) {
    MyPageInfoScreen(padding = padding, popBackStack = popBackStack)
}

@Composable
fun MyPageInfoScreen(
    padding: PaddingValues = PaddingValues(),
    isEditing: Boolean = false,
    popBackStack: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SusuDefaultAppBar(
            leftIcon = {
                BackIcon(onClick = popBackStack)
            },
            title = "내 정보",
            actions = {
                if (isEditing) {
                    Text(
                        modifier = Modifier.padding(end = SusuTheme.spacing.spacing_xs),
                        text = "등록",
                        style = SusuTheme.typography.title_xxs,
                        color = Gray100,
                    )
                } else {
                    Text(
                        modifier = Modifier.padding(end = SusuTheme.spacing.spacing_xs),
                        text = "편집",
                        style = SusuTheme.typography.title_xxs,
                        color = Gray100,
                    )
                }
            },
        )
        Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_m))
        // TODO: 실행 이후 이미지로 바꾸기
        Spacer(
            modifier = Modifier
                .size(88.dp)
                .background(color = Gray100),
        )
//        Image(
//            modifier = Modifier.size(88.dp),
//            painter = painterResource(id = com.susu.core.ui.R.drawable.img_default_profile),
//            contentDescription = "프로필 이미지",
//        )
        Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_m))
        MyPageInfoItem(
            title = "이름",
        ) {
            if (isEditing) {
                SusuBasicTextField(
                    modifier = Modifier.width(IntrinsicSize.Min),
                    text = "XXX",
                    textStyle = SusuTheme.typography.title_xs,
                    onTextChange = {},
                    textColor = Gray40,
                )
            } else {
                Text(text = "OOO", style = SusuTheme.typography.title_xs, color = Gray100)
            }
        }
        MyPageInfoItem(
            title = "출생년도",
        ) {
            if (isEditing) {
                Text(
                    modifier = Modifier.susuClickable(
                        onClick = { // TODO: 날짜 선택 바텀시트 오픈
                        },
                    ),
                    text = "2024",
                    style = SusuTheme.typography.title_xs,
                    color = Gray40,
                )
            } else {
                Text(text = "2024", style = SusuTheme.typography.title_xs, color = Gray100)
            }
        }
        MyPageInfoItem(
            title = "성별",
        ) {
            if (isEditing) {
                Row(
                    modifier = Modifier.weight(1f),
                ) {
                    Spacer(modifier = Modifier.width(64.dp))
                    SusuFilledButton(
                        modifier = Modifier.weight(1f).padding(vertical = SusuTheme.spacing.spacing_xxxxs),
                        text = "여성",
                        isClickable = true,
                        isActive = false,
                        color = FilledButtonColor.Orange,
                        style = SmallButtonStyle.height32,
                    )
                    Spacer(modifier = Modifier.width(SusuTheme.spacing.spacing_xxs))
                    SusuFilledButton(
                        modifier = Modifier.weight(1f).padding(vertical = SusuTheme.spacing.spacing_xxxxs),
                        text = "남성",
                        isClickable = true,
                        isActive = false,
                        color = FilledButtonColor.Orange,
                        style = SmallButtonStyle.height32,
                    )
                }
            } else {
                Text(
                    text = "미선택",
                    style = SusuTheme.typography.title_xs,
                    color = Gray100,
                )
            }
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

@Preview(showBackground = true)
@Composable
fun MyPageInfoScreenEditPreview() {
    SusuTheme {
        MyPageInfoScreen(isEditing = true)
    }
}
