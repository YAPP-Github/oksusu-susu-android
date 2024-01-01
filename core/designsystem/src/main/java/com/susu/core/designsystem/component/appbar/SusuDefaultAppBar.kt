package com.susu.core.designsystem.component.appbar

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.R
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable

@Composable
fun SusuDefaultAppBar(
    modifier: Modifier = Modifier,
    leftIcon: @Composable () -> Unit,
    title: String? = null,
    actions: @Composable () -> Unit = {},
) {
    BasicAppBar(
        modifier = modifier,
        leftIcon = leftIcon,
        title = {
            title?.let {
                Text(
                    text = title,
                    style = SusuTheme.typography.title_xs,
                    color = Gray100,
                    textAlign = TextAlign.Center,
                )
            }
        },
        actions = actions,
    )
}

/**
 * SusuDefaultAppBar의 경우의 수
 * 1. 왼쪽 로고 + 타이틀 + 오른쪽 아이콘 2개
 * 2. 왼쪽 로고 + 오른쪽 아이콘 2개
 * 3. 왼쪽 뒤로가기 + 타이틀 + 오른쪽 아이콘 2개
 * 4. 왼쪽 뒤로가기 + 타이틀 + 오른쪽 아이콘 1개
 * 5. 왼쪽 뒤로가기 + 타이틀 + 오른쪽 텍스트 2개
 * 6. 왼쪽 뒤로가기 + 타이틀 + 오른족 텍스트 1개
 * 7. 왼쪽 뒤로가기 + 타이틀
 * 8. 왼쪽 뒤로가기 + 오른쪽 텍스트 2개
 * 9. 왼쪽 뒤로가기
 * */

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun SusuDefaultAppBarPreview() {
    SusuTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            // 1. 왼쪽 로고 + 타이틀 + 오른쪽 아이콘 2개
            LogoWithTwoIconsPreview()

            // 5. 왼쪽 뒤로가기 + 타이틀 + 오른쪽 텍스트 2개
            BackBtnWithTwoTextPreview()
        }
    }
}

@Composable
private fun LogoWithTwoIconsPreview(
    modifier: Modifier = Modifier,
    clickPadding: Dp = 10.dp,
) {
    SusuDefaultAppBar(
        leftIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_received_filled),
                contentDescription = "로고",
                modifier = modifier.padding(clickPadding),
            )
        },
        title = "타이틀",
        actions = {
            Box(
                modifier = modifier
                    .susuClickable(
                        rippleEnabled = true, // 클릭 영역 확인을 위해 true로 설정
                        onClick = {
                            Log.d("확인", "오른쪽 버튼1 클릭")
                        },
                    )
                    .padding(clickPadding),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_community_filled),
                    contentDescription = null,
                )
            }
            Box(
                modifier = modifier
                    .susuClickable(
                        rippleEnabled = true, // 클릭 영역 확인을 위해 true로 설정
                        onClick = {
                            Log.d("확인", "오른쪽 버튼2 클릭")
                        },
                    )
                    .padding(clickPadding),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_my_page_filled),
                    contentDescription = null,
                )
            }
        },
    )
}

@Composable
private fun BackBtnWithTwoTextPreview(
    modifier: Modifier = Modifier,
    clickPadding: Dp = 10.dp,
    textPadding: Dp = 16.dp,
) {
    SusuDefaultAppBar(
        leftIcon = {
            Box(
                modifier = modifier
                    .susuClickable(
                        rippleEnabled = true,
                        onClick = {
                            Log.d("확인", "왼쪽 뒤로가기 클릭")
                        },
                    )
                    .padding(clickPadding),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = "뒤로가기",
                )
            }
        },
        title = "타이틀",
        actions = {
            Text(
                text = "편집",
                style = SusuTheme.typography.title_xxs,
                color = Gray100,
                modifier = modifier
                    .padding(end = textPadding)
                    .susuClickable(
                        rippleEnabled = true,
                        onClick = {
                            Log.d("확인", "오른쪽 텍스트1 클릭")
                        },
                    ),
            )
            Text(
                text = "삭제",
                style = SusuTheme.typography.title_xxs,
                color = Gray100,
                modifier = modifier
                    .padding(end = textPadding)
                    .susuClickable(
                        rippleEnabled = true,
                        onClick = {
                            Log.d("확인", "오른쪽 텍스트2 클릭")
                        },
                    ),
            )
        },
    )
}
