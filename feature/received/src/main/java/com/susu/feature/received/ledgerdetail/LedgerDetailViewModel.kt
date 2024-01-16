package com.susu.feature.received.ledgerdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.susu.core.model.Ledger
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.extension.decodeFromUri
import com.susu.core.ui.extension.encodeToUri
import com.susu.core.ui.util.to_yyyy_dot_MM_dot_dd
import com.susu.domain.usecase.ledger.DeleteLedgerUseCase
import com.susu.feature.received.navigation.ReceivedRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LedgerDetailViewModel @Inject constructor(
    private val deleteLedgerUseCase: DeleteLedgerUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<LedgerDetailState, LedgerDetailSideEffect>(
    LedgerDetailState(),
) {
    private val argument = savedStateHandle.get<String>(ReceivedRoute.LEDGER_ARGUMENT_NAME)!!
    private var ledger = Ledger()

    fun initData(backStackEntryLedgerUri: String?) {
        if (backStackEntryLedgerUri == null) {
            updateLedgerInfo(Json.decodeFromUri<Ledger>(argument))
            return
        }

        val backStackLedger = Json.decodeFromUri<Ledger>(backStackEntryLedgerUri)
        if (backStackLedger == Ledger()) {
            updateLedgerInfo(Json.decodeFromUri<Ledger>(argument))
            return
        }

        updateLedgerInfo(backStackLedger)
    }

    private fun updateLedgerInfo(ledger: Ledger) = intent {
        this@LedgerDetailViewModel.ledger = ledger
        ledger.let { ledger ->
            val category = ledger.category
            copy(
                name = ledger.title,
                money = ledger.totalAmounts,
                count = ledger.totalCounts,
                category = if (category.customCategory.isNullOrEmpty()) category.name else category.customCategory!!,
                startDate = ledger.startAt.toJavaLocalDateTime().to_yyyy_dot_MM_dot_dd(),
                endDate = ledger.endAt.toJavaLocalDateTime().to_yyyy_dot_MM_dot_dd(),
            )
        }
    }

    fun navigateLedgerEdit() = postSideEffect(LedgerDetailSideEffect.NavigateLedgerEdit(ledger))

    fun popBackStackWithLedger() = postSideEffect(LedgerDetailSideEffect.PopBackStackWithLedger(Json.encodeToUri(ledger)))
    fun showDeleteDialog() = postSideEffect(
        LedgerDetailSideEffect.ShowDeleteDialog(
            onConfirmRequest = ::deleteLedger,
        ),
    )

    private fun deleteLedger() = viewModelScope.launch {
        deleteLedgerUseCase(ledger.id)
            .onSuccess {
                postSideEffect(
                    LedgerDetailSideEffect.ShowDeleteSuccessSnackbar,
                    LedgerDetailSideEffect.PopBackStackWithDeleteLedgerId(ledger.id),
                )
            }
            .onFailure {

            }
    }
}
