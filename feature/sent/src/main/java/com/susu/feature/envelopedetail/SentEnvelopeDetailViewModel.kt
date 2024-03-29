package com.susu.feature.envelopedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.envelope.DeleteEnvelopeUseCase
import com.susu.domain.usecase.envelope.GetEnvelopeDetailUseCase
import com.susu.feature.sent.navigation.SentRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SentEnvelopeDetailViewModel @Inject constructor(
    private val getEnvelopeDetailUseCase: GetEnvelopeDetailUseCase,
    private val deleteEnvelopeUseCase: DeleteEnvelopeUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<SentEnvelopeDetailState, SentEnvelopeDetailEffect>(
    SentEnvelopeDetailState(),
) {
    private val envelopeId = savedStateHandle.get<Long>(SentRoute.ENVELOPE_ID_ARGUMENT_NAME)!!
    fun getEnvelopeDetail(id: Long = envelopeId) = viewModelScope.launch {
        getEnvelopeDetailUseCase(id).onSuccess { envelopeDetail ->
            intent {
                copy(
                    envelopeDetail = envelopeDetail,
                )
            }
        }.onFailure {
            postSideEffect(SentEnvelopeDetailEffect.HandleException(it, ::getEnvelopeDetail))
        }
    }

    fun showDeleteDialog() {
        postSideEffect(SentEnvelopeDetailEffect.ShowDeleteDialog)
    }

    fun deleteEnvelope() = viewModelScope.launch {
        deleteEnvelopeUseCase(envelopeId).onSuccess {
            postSideEffect(SentEnvelopeDetailEffect.ShowDeleteSuccessSnackBar, SentEnvelopeDetailEffect.PopBackStack)
        }.onFailure {
            postSideEffect(SentEnvelopeDetailEffect.HandleException(it, ::deleteEnvelope))
        }
    }

    fun navigateSentEnvelopeEdit() = postSideEffect(
        SentEnvelopeDetailEffect.NavigateEnvelopeEdit(
            currentState.envelopeDetail,
        ),
    )

    fun popBackStack() = postSideEffect(SentEnvelopeDetailEffect.PopBackStack)
}
