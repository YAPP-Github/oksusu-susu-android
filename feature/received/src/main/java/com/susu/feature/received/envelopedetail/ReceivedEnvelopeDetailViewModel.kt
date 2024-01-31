package com.susu.feature.received.envelopedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.susu.core.model.Envelope
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.extension.decodeFromUri
import com.susu.core.ui.extension.encodeToUri
import com.susu.domain.usecase.envelope.DeleteEnvelopeUseCase
import com.susu.domain.usecase.envelope.GetEnvelopeUseCase
import com.susu.feature.received.navigation.ReceivedRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class ReceivedEnvelopeDetailViewModel @Inject constructor(
    private val getEnvelopeUseCase: GetEnvelopeUseCase,
    private val deleteEnvelopeUseCase: DeleteEnvelopeUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ReceivedEnvelopeDetailState, ReceivedEnvelopeDetailSideEffect>(
    ReceivedEnvelopeDetailState(),
) {
    private val argument = savedStateHandle.get<String>(ReceivedRoute.ENVELOPE_ARGUMENT_NAME)!!
    private val ledgerId = savedStateHandle.get<String>(ReceivedRoute.LEDGER_ID_ARGUMENT_NAME)!!.toLong()

    private var envelope = Envelope()

    fun getEnvelope() = viewModelScope.launch {
        envelope = Json.decodeFromUri<Envelope>(argument)
        getEnvelopeUseCase(id = envelope.id)
            .onSuccess { envelope ->
                this@ReceivedEnvelopeDetailViewModel.envelope = envelope
                intent {
                    copy(
                        envelope = envelope,
                    )
                }
            }
    }

    fun navigateEnvelopeEdit() = postSideEffect(ReceivedEnvelopeDetailSideEffect.NavigateReceivedEnvelopeEdit(envelope, ledgerId))
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
