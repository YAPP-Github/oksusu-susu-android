package com.susu.feature.received.envelopedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.susu.core.model.Envelope
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.extension.decodeFromUri
import com.susu.core.ui.extension.encodeToUri
import com.susu.domain.usecase.envelope.DeleteEnvelopeUseCase
import com.susu.feature.received.navigation.ReceivedRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReceivedEnvelopeDetailViewModel @Inject constructor(
    private val deleteEnvelopeUseCase: DeleteEnvelopeUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ReceivedEnvelopeDetailState, ReceivedEnvelopeDetailSideEffect>(
    ReceivedEnvelopeDetailState(),
) {
    private val argument = savedStateHandle.get<String>(ReceivedRoute.ENVELOPE_ARGUMENT_NAME)!!
    private var envelope = Envelope()

    fun getEnvelope() = viewModelScope.launch {
        envelope = Json.decodeFromUri<Envelope>(argument)
        Timber.tag("테스트").d("$envelope")
//        getLedgerUseCase(id = envelope.id)
//            .onSuccess { ledger ->
//                this@ReceivedReceivedEnvelopeDetailViewModel.envelope = ledger
//                intent {
//                    with(ledger) {
//                        val category = ledger.category
//                        copy(
//                            name = ledger.title,
//                            money = ledger.totalAmounts,
//                            count = ledger.totalCounts,
//                            category = if (category.customCategory.isNullOrEmpty()) category.name else category.customCategory!!,
//                            startDate = ledger.startAt.toJavaLocalDateTime().to_yyyy_dot_MM_dot_dd(),
//                            endDate = ledger.endAt.toJavaLocalDateTime().to_yyyy_dot_MM_dot_dd(),
//                        )
//                    }
//                }
//            }
    }

    fun navigateEnvelopeEdit() = postSideEffect(ReceivedEnvelopeDetailSideEffect.NavigateReceivedEnvelopeEdit(envelope))
    fun popBackStackWithEnvelope() = postSideEffect(ReceivedEnvelopeDetailSideEffect.PopBackStackWithReceivedEnvelope(Json.encodeToUri(envelope)))
    fun showDeleteDialog() = postSideEffect(
        ReceivedEnvelopeDetailSideEffect.ShowDeleteDialog(
            onConfirmRequest = ::deleteEnvelope,
        ),
    )

    private fun deleteEnvelope() = viewModelScope.launch {
        deleteEnvelopeUseCase(envelope.id)
            .onSuccess {
                postSideEffect(
                    ReceivedEnvelopeDetailSideEffect.ShowDeleteSuccessSnackbar,
                    ReceivedEnvelopeDetailSideEffect.PopBackStackWithDeleteReceivedEnvelopeId(envelope.id),
                )
            }
            .onFailure { throwable ->
                postSideEffect(ReceivedEnvelopeDetailSideEffect.HandleException(throwable, ::deleteEnvelope))
            }
    }
}
