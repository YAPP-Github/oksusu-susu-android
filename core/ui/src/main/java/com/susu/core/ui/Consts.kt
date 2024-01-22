package com.susu.core.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.persistentListOf

// TODO REMOVE
val alignList
    @Composable
    get() = listOf(
        stringResource(id = R.string.word_align_recently),
        stringResource(id = R.string.word_align_outdated),
        stringResource(id = R.string.word_align_high_amount),
        stringResource(id = R.string.word_align_low_amount),
    )

val moneyList = persistentListOf(10_000, 30_000, 50_000, 100_000, 500_000)

const val USER_NAME_MAX_LENGTH = 10
val nameRegex = Regex("[a-zA-Z가-힣]{0,10}")

const val INTENT_ACTION_DOWNLOAD_COMPLETE = "android.intent.action.DOWNLOAD_COMPLETE"

enum class SnsProviders(
    val path: String,
    @StringRes val nameId: Int,
    @DrawableRes val iconId: Int,
    val backgroundColor: Color,
) {
    Kakao(
        path = "KAKAO",
        nameId = R.string.sns_kakao,
        iconId = R.drawable.ic_kakao_login,
        backgroundColor = Color(0xFFFEE500),
    ),
    Naver(
        path = "",
        nameId = R.string.sns_naver,
        iconId = R.drawable.ic_kakao_login,
        backgroundColor = Color.Unspecified,
    ),
    Google(
        path = "",
        nameId = R.string.sns_google,
        iconId = R.drawable.ic_kakao_login,
        backgroundColor = Color.Unspecified,
    ),
}

enum class Gender(val content: String?) {
    NONE(null), MALE("M"), FEMALE("F")
}
