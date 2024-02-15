package com.susu.core.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.susu.core.ui.util.currentDate
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

val USER_BIRTH_RANGE = 1930..currentDate.year
const val MONEY_MAX_VALUE = 2_000_000_000L // TODO: 정책 확정 시 99억으로 변경

const val INTENT_ACTION_DOWNLOAD_COMPLETE = "android.intent.action.DOWNLOAD_COMPLETE"
const val PRIVACY_POLICY_URL = "https://sites.google.com/view/team-oksusu/%ED%99%88"
const val SUSU_GOOGLE_FROM_URL = "https://forms.gle/FHky26kAQdde9RcD7"
const val SUSU_GOOGLE_PLAY_STORE_URL = "https://play.google.com/store/apps/details?id=com.oksusu.susu"

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
