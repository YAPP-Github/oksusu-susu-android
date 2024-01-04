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
