package com.susu.feature.received.ledgerdetail

import androidx.lifecycle.SavedStateHandle
import com.susu.core.model.Ledger
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.extension.decodeFromUri
import com.susu.core.ui.util.to_yyyy_dot_MM_dot_dd
import com.susu.feature.received.navigation.ReceivedRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class LedgerDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<LedgerDetailState, LedgerDetailSideEffect>(
    LedgerDetailState(),
) {
    private val argument = savedStateHandle.get<String>(ReceivedRoute.LEDGER_ARGUMENT_NAME)!!
    private var ledger = Ledger()

    fun initData(backStackEntryLedger: String) {
        Json.decodeFromUri<Ledger>(backStackEntryLedger)
            .let { ledger ->
                if (ledger == Ledger()) return@let
                updateLedgerInfo(ledger)
            }

        updateLedgerInfo(Json.decodeFromUri<Ledger>(argument))
    }

    private fun updateLedgerInfo(ledger: Ledger) = intent {
        this@LedgerDetailViewModel.ledger = ledger
        ledger.let { ledger ->
            copy(
                name = ledger.title,
                money = ledger.totalAmounts,
                count = ledger.totalCounts,
                category = ledger.category.name,
                startDate = ledger.startAt.toJavaLocalDateTime().to_yyyy_dot_MM_dot_dd(),
                endDate = ledger.endAt.toJavaLocalDateTime().to_yyyy_dot_MM_dot_dd(),
            )
        }
    }

    fun navigateLedgerEdit() = postSideEffect(LedgerDetailSideEffect.NavigateLedgerEdit(ledger))
}
