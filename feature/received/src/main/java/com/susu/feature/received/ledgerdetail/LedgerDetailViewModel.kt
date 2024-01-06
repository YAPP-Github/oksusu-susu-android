package com.susu.feature.received.ledgerdetail

import androidx.lifecycle.SavedStateHandle
import com.susu.core.ui.base.BaseViewModel
import com.susu.feature.received.navigation.ReceivedRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LedgerDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<LedgerDetailState, LedgerDetailSideEffect>(
    LedgerDetailState(),
) {
    private val id = savedStateHandle.get<String>(ReceivedRoute.LEDGER_DETAIL_ARGUMENT_NAME)!!.toInt()

    init {
        Timber.tag("테스트").d("$id")
    }
}
