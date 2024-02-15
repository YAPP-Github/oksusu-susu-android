package com.susu.feature.mypage.main

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.UpdateAvailability
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.LogoIcon
import com.susu.core.designsystem.component.button.GhostButtonColor
import com.susu.core.designsystem.component.button.SmallButtonStyle
import com.susu.core.designsystem.component.button.SusuGhostButton
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray15
import com.susu.core.designsystem.theme.Gray20
import com.susu.core.designsystem.theme.Gray50
import com.susu.core.designsystem.theme.Gray60
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.DialogToken
import com.susu.core.ui.R
import com.susu.core.ui.SUSU_GOOGLE_FROM_URL
import com.susu.core.ui.SUSU_GOOGLE_PLAY_STORE_URL
import com.susu.core.ui.SnackbarToken
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.extension.susuClickable
import com.susu.feature.mypage.main.component.MyPageMenuItem

@Composable
fun MyPageDefaultRoute(
    padding: PaddingValues,
    viewModel: MyPageViewModel = hiltViewModel(),
    restartMainActivity: () -> Unit,
    navigateToInfo: () -> Unit,
    navigateToSocial: () -> Unit,
    navigateToPrivacyPolicy: () -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit,
    onShowDialog: (DialogToken) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit,
) {
    val context = LocalContext.current
    val appUpdateManager = remember { AppUpdateManagerFactory.create(context) }

    LaunchedEffect(key1 = Unit) {
        viewModel.getUserInfo()
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            viewModel.updateCanUpdate(appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE)
        }
    }

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            MyPageEffect.NavigateToLogin -> restartMainActivity()
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
        onClickFeedback = {
            runCatching {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(SUSU_GOOGLE_FROM_URL)))
            }.onFailure {
                onShowSnackbar(SnackbarToken(message = context.getString(com.susu.feature.mypage.R.string.snackbar_browser_not_found)))
            }
        },
        navigateToInfo = navigateToInfo,
        navigateToSocial = navigateToSocial,
        navigateToPrivacyPolicy = navigateToPrivacyPolicy,
    )
}

@Composable
fun MyPageDefaultScreen(
    padding: PaddingValues,
    uiState: MyPageState = MyPageState(),
    onLogout: () -> Unit = {},
    onWithdraw: () -> Unit = {},
    onExport: () -> Unit = {},
    onClickFeedback: () -> Unit = {},
    navigateToInfo: () -> Unit = {},
    navigateToSocial: () -> Unit = {},
    navigateToPrivacyPolicy: () -> Unit = {},
) {
    val context = LocalContext.current
    val currentAppVersion = remember { getAppVersion(context) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SusuTheme.colorScheme.background10)
            .padding(padding),
    ) {
        SusuDefaultAppBar(
            modifier = Modifier.padding(horizontal = SusuTheme.spacing.spacing_xs),
            leftIcon = { LogoIcon() },
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
            onMenuClick = navigateToPrivacyPolicy,
        )

        MyPageDivider()

        MyPageMenuItem(
            titleText = stringResource(com.susu.feature.mypage.R.string.mypage_app_version),
            rippleEnabled = uiState.canUpdate,
            action = {
                if (uiState.canUpdate) {
                    Text(
                        modifier = Modifier.susuClickable(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW).apply {
                                    data = Uri.parse(SUSU_GOOGLE_PLAY_STORE_URL)
                                    setPackage("com.android.vending") // Google Play 스토어 앱으로 연결되게 함.
                                }
                                context.startActivity(intent)
                            },
                        ),
                        text = stringResource(com.susu.feature.mypage.R.string.mypage_update),
                        style = SusuTheme.typography.title_xxs,
                        color = Gray60,
                    )
                } else {
                    Text(
                        text = stringResource(com.susu.feature.mypage.R.string.mypage_default_recent_version, currentAppVersion),
                        style = SusuTheme.typography.title_xxs,
                        color = Gray60,
                    )
                }
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
                .background(color = Gray15)
                .padding(SusuTheme.spacing.spacing_m),
        ) {
            Text(
                modifier = Modifier.align(Alignment.TopStart),
                text = stringResource(com.susu.feature.mypage.R.string.mypage_app_version) + " $currentAppVersion",
                style = SusuTheme.typography.title_xxxs,
                color = Gray50,
            )
            SusuGhostButton(
                modifier = Modifier.align(Alignment.Center),
                color = GhostButtonColor.Orange,
                style = SmallButtonStyle.height40,
                text = stringResource(com.susu.feature.mypage.R.string.mypage_feedback),
                onClick = onClickFeedback,
            )
        }
    }
}

private fun getAppVersion(context: Context): String = runCatching {
    val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
    packageInfo.versionName
}.getOrNull() ?: ""

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
