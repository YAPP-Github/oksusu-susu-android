package com.susu.feature.mypage.social

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakao.sdk.auth.AuthApiClient
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.component.button.XSmallButtonStyle
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray25
import com.susu.core.designsystem.theme.Gray50
import com.susu.core.designsystem.theme.Gray70
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.SnsProviders

@Composable
fun MyPageSocialRoute(
    padding: PaddingValues,
    popBackStack: () -> Unit,
) {
    MyPageSocialScreen(padding = padding, popBackStack = popBackStack)
}

@Composable
fun MyPageSocialScreen(
    padding: PaddingValues = PaddingValues(),
    popBackStack: () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SusuDefaultAppBar(
            leftIcon = {
                BackIcon(onClick = popBackStack)
            },
            title = "연동된 소셜 계정",
        )
        Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_m))
        Text(text = "연결된 SNS 계정으로 로그인할 수 있어요", style = SusuTheme.typography.text_xxs, color = Gray70)
        Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xxl))
        Row(
            horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_m),
        ) {
            SnsProviders.entries.forEach {
                // 추후 소셜 로그인 플랫폼 추가 시 수정
                SocialProvider(isActive = AuthApiClient.instance.hasToken(), snsProviders = it)
            }
        }
        Spacer(modifier = Modifier.height(56.dp))
        Text(text = "로그인에 문제가 있다면 팀 옥수수에게 알려주세요", style = SusuTheme.typography.text_xxxs, color = Gray50)
        Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_s))
        SusuFilledButton(
            text = "문의 남기기",
            color = FilledButtonColor.Black,
            style = XSmallButtonStyle.height36,
            isActive = false,
            isClickable = true,
        )
    }
}

@Composable
fun SocialProvider(
    isActive: Boolean,
    snsProviders: SnsProviders,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(color = if (isActive) snsProviders.backgroundColor else Gray25, shape = CircleShape)
                .padding(SusuTheme.spacing.spacing_s),
        ) {
            if (isActive) {
                Image(
                    modifier = Modifier.size(36.dp).align(Alignment.Center),
                    painter = painterResource(id = snsProviders.iconId),
                    contentDescription = null,
                )
            }
        }
        Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xxxxs))
        Text(
            text = stringResource(id = snsProviders.nameId),
            style = SusuTheme.typography.title_xxs,
            color = if (isActive) Gray100 else Gray50,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyPageSocialScreenPreview() {
    SusuTheme {
        MyPageSocialScreen()
    }
}
