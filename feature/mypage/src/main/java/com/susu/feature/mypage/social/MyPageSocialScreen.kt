package com.susu.feature.mypage.social

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
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
import com.susu.core.ui.SUSU_GOOGLE_FROM_URL
import com.susu.core.ui.SnsProviders
import com.susu.feature.mypage.R

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
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().background(SusuTheme.colorScheme.background10).padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SusuDefaultAppBar(
            leftIcon = {
                BackIcon(onClick = popBackStack)
            },
            title = stringResource(id = R.string.mypage_social_title),
        )
        Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_m))
        Text(text = stringResource(R.string.mypage_social_detail), style = SusuTheme.typography.text_xxs, color = Gray70)
        Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xxl))
        Row(
            horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_m),
        ) {
            SnsProviders.entries.forEach {
                // 추후 소셜 로그인 플랫폼 추가 시 수정
                SocialProvider(
                    isActive = when (it) {
                        SnsProviders.Kakao -> AuthApiClient.instance.hasToken()
                    },
                    snsProviders = it,
                )
            }
        }
        Spacer(modifier = Modifier.height(56.dp))
        Text(text = stringResource(R.string.mypage_social_when_problem), style = SusuTheme.typography.text_xxxs, color = Gray50)
        Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_s))
        SusuFilledButton(
            text = stringResource(R.string.mypage_social_question),
            color = FilledButtonColor.Black,
            style = XSmallButtonStyle.height36,
            isActive = false,
            isClickable = true,
            onClick = { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(SUSU_GOOGLE_FROM_URL))) },
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
