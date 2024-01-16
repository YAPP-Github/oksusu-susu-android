package com.susu.core.ui

import androidx.compose.runtime.Composable
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

enum class SnsProviders(val path: String) {
    Kakao("KAKAO"),
}
