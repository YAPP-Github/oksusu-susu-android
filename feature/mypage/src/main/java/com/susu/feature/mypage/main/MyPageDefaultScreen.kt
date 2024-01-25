package com.susu.feature.mypage.main

import android.content.Context
import android.os.Environment
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.LogoIcon
import com.susu.core.designsystem.component.appbar.icon.NotificationIcon
import com.susu.core.designsystem.component.button.GhostButtonColor
import com.susu.core.designsystem.component.button.SmallButtonStyle
import com.susu.core.designsystem.component.button.SusuGhostButton
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray20
import com.susu.core.designsystem.theme.Gray50
import com.susu.core.designsystem.theme.Gray60
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.DialogToken
import com.susu.core.ui.R
import com.susu.core.ui.SnackbarToken
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.extension.susuClickable
import com.susu.feature.mypage.main.component.MyPageMenuItem

@Composable
fun MyPageDefaultRoute(
    padding: PaddingValues,
    viewModel: MyPageViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit,
    navigateToInfo: () -> Unit,
    navigateToSocial: () -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit,
    onShowDialog: (DialogToken) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit,
) {
    val context = LocalContext.current

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            MyPageEffect.NavigateToLogin -> navigateToLogin()
            MyPageEffect.NavigateToInfo -> navigateToInfo()
            MyPageEffect.NavigateToSocial -> navigateToSocial()
            is MyPageEffect.ShowSnackbar -> onShowSnackbar(SnackbarToken(message = sideEffect.msg))
            MyPageEffect.ShowLogoutDialog -> {
                onShowDialog(
                    DialogToken(
                        title = context.getString(com.susu.feature.mypage.R.string.dialog_logout_title),
                        dismissText = context.getString(R.string.word_cancel),
                        confirmText = context.getString(com.susu.feature.mypage.R.string.dialog_logout_confirm),
                        onConfirmRequest = viewModel::logout,
                    ),
                )
            }

            MyPageEffect.ShowExportDialog -> {
                onShowDialog(
                    DialogToken(
                        title = context.getString(com.susu.feature.mypage.R.string.dialog_export_title),
                        text = context.getString(com.susu.feature.mypage.R.string.dialog_export_detail, Environment.DIRECTORY_DOWNLOADS),
                        dismissText = context.getString(R.string.word_cancel),
                        confirmText = context.getString(com.susu.feature.mypage.R.string.dialog_export_confirm),
                        onConfirmRequest = viewModel::export,
                    ),
                )
            }

            MyPageEffect.ShowWithdrawDialog -> {
                onShowDialog(
                    DialogToken(
                        title = context.getString(com.susu.feature.mypage.R.string.dialog_withdraw_title),
                        text = context.getString(com.susu.feature.mypage.R.string.dialog_withdraw_detail),
                        dismissText = context.getString(R.string.word_cancel),
                        confirmText = context.getString(com.susu.feature.mypage.R.string.dialog_withdraw_confirm),
                        onConfirmRequest = viewModel::withdraw,
                    ),
                )
            }

            MyPageEffect.ShowExportSuccessSnackbar -> {
                onShowSnackbar(
                    SnackbarToken(
                        message = context.getString(com.susu.feature.mypage.R.string.snackbar_success_export),
                    ),
                )
            }

            MyPageEffect.ShowLogoutSuccessSnackbar -> {
                onShowSnackbar(
                    SnackbarToken(
                        message = context.getString(com.susu.feature.mypage.R.string.snackbar_success_logout),
                    ),
                )
            }

            MyPageEffect.ShowWithdrawSuccessSnackbar -> {
                onShowSnackbar(
                    SnackbarToken(
                        message = context.getString(com.susu.feature.mypage.R.string.snackbar_success_withdraw),
                    ),
                )
            }

            is MyPageEffect.HandleException -> handleException(sideEffect.throwable, sideEffect.retry)
        }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MyPageDefaultScreen(
        padding = padding,
        uiState = uiState,
        onLogout = viewModel::showLogoutDialog,
        onWithdraw = viewModel::showWithdrawDialog,
        onExport = viewModel::showExportDialog,
        navigateToInfo = navigateToInfo,
        navigateToSocial = navigateToSocial,
    )
}

@Composable
fun MyPageDefaultScreen(
    padding: PaddingValues,
    uiState: MyPageState = MyPageState(),
    onLogout: () -> Unit = {},
    onWithdraw: () -> Unit = {},
    onExport: () -> Unit = {},
    navigateToInfo: () -> Unit = {},
    navigateToSocial: () -> Unit = {},
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        SusuDefaultAppBar(
            modifier = Modifier.padding(SusuTheme.spacing.spacing_xs),
            leftIcon = { LogoIcon() },
            actions = { NotificationIcon() },
        )

        MyPageMenuItem(
            titleText = uiState.userName,
            titleTextColor = Gray100,
            titleTextStyle = SusuTheme.typography.title_m,
            action = {
                Row(
                    modifier = Modifier.susuClickable(onClick = navigateToInfo),
                ) {
                    Text(
                        text = stringResource(com.susu.feature.mypage.R.string.mypage_default_my_info),
                        style = SusuTheme.typography.title_xxs,
                        color = Gray60,
                    )
                    Spacer(modifier = Modifier.width(SusuTheme.spacing.spacing_xxs))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = stringResource(com.susu.feature.mypage.R.string.mypage_default_show_my_info),
                        tint = Gray60,
                    )
                }
            },
            padding = PaddingValues(SusuTheme.spacing.spacing_m),
            actionItemPadding = SusuTheme.spacing.spacing_xxl,
        )

        MyPageDivider()

        MyPageMenuItem(
            titleText = stringResource(com.susu.feature.mypage.R.string.mypage_social_title),
            onMenuClick = navigateToSocial,
        )
        MyPageMenuItem(
            titleText = stringResource(com.susu.feature.mypage.R.string.mypage_export_title),
            onMenuClick = onExport,
        )
        MyPageMenuItem(
            titleText = stringResource(com.susu.feature.mypage.R.string.mypage_privacy_policy),
        )

        MyPageDivider()

        MyPageMenuItem(
            titleText = stringResource(com.susu.feature.mypage.R.string.mypage_app_version),
            action = {
                Text(text = stringResource(com.susu.feature.mypage.R.string.mypage_update), style = SusuTheme.typography.title_xxs, color = Gray60)
            },
        )

        MyPageDivider()

        MyPageMenuItem(
            titleText = stringResource(com.susu.feature.mypage.R.string.mypage_logout_title),
            onMenuClick = onLogout,
        )
        MyPageMenuItem(
            titleText = stringResource(com.susu.feature.mypage.R.string.mypage_withdraw_title),
            onMenuClick = onWithdraw,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(color = Gray20)
                .padding(SusuTheme.spacing.spacing_m),
        ) {
            Text(
                modifier = Modifier.align(Alignment.TopStart),
                text = stringResource(com.susu.feature.mypage.R.string.mypage_app_version) + " ${getAppVersion(context)}",
                style = SusuTheme.typography.title_xxxs,
                color = Gray50,
            )
            SusuGhostButton(
                modifier = Modifier.align(Alignment.Center),
                color = GhostButtonColor.Orange,
                style = SmallButtonStyle.height40,
                text = stringResource(com.susu.feature.mypage.R.string.mypage_feedback),
            )
        }
    }
}

private fun getAppVersion(context: Context): String {
    try {
        val packageInfo = context.packageManager.getPackageInfo(
            context.packageName, 0,
        )

        if (packageInfo != null) {
            return packageInfo.versionName
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return ""
}

@Composable
fun MyPageDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 8.dp,
    color: Color = Gray20,
) {
    HorizontalDivider(
        modifier = modifier,
        thickness = thickness,
        color = color,
    )
}

@Preview(showBackground = true)
@Composable
fun MyPageDefaultScreenPreview() {
    SusuTheme {
        MyPageDefaultScreen(padding = PaddingValues(0.dp))
    }
}
