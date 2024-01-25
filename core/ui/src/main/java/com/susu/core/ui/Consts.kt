package com.susu.core.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

val alignList
    @Composable
    get() = listOf(
        stringResource(id = R.string.word_align_recently),
        stringResource(id = R.string.word_align_outdated),
        stringResource(id = R.string.word_align_high_amount),
        stringResource(id = R.string.word_align_low_amount),
    )

const val USER_NAME_MAX_LENGTH = 10
val nameRegex = Regex("[a-zA-Z가-힣]{0,10}")

val USER_BIRTH_RANGE = 1930..2030

const val INTENT_ACTION_DOWNLOAD_COMPLETE = "android.intent.action.DOWNLOAD_COMPLETE"
const val PRIVACY_POLICY_URL = "https://sites.google.com/view/team-oksusu/%ED%99%88"
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
}

enum class Gender(val content: String?) {
    NONE(null), MALE("M"), FEMALE("F")
}
