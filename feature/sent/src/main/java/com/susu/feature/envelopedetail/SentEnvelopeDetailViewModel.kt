package com.susu.feature.envelopedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.util.to_yyyy_korYear_M_korMonth_d_korDay
import com.susu.domain.usecase.envelope.GetEnvelopeDetailUseCase
import com.susu.feature.sent.navigation.SentRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.toJavaLocalDateTime
import javax.inject.Inject

@HiltViewModel
class SentEnvelopeDetailViewModel @Inject constructor(
    private val getEnvelopeDetailUseCase: GetEnvelopeDetailUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<SentEnvelopeDetailState, SentEnvelopeDetailEffect>(
    SentEnvelopeDetailState(),
) {
    private val envelopeId = savedStateHandle.get<Long>(SentRoute.ENVELOPE_ID_ARGUMENT_NAME)!!
    fun getEnvelopeDetail(id: Long = envelopeId) = viewModelScope.launch {
        getEnvelopeDetailUseCase(id).onSuccess { envelopeDetail ->
            intent {
                copy(
                    money = envelopeDetail.envelope.amount.toInt(),
                    event = envelopeDetail.category.category,
                    name = envelopeDetail.friend.name,
                    relationship = envelopeDetail.relationship.relation,
                    date = envelopeDetail.envelope.handedOverAt.toJavaLocalDateTime().to_yyyy_korYear_M_korMonth_d_korDay(),
                    visited = envelopeDetail.envelope.hasVisited,
                    gift = envelopeDetail.envelope.gift!!,
                    phoneNumber = envelopeDetail.friend.phoneNumber,
                    memo = envelopeDetail.envelope.memo!!,
                )
            }
        }
    }

    fun navigateSentEnvelopeEdit() = postSideEffect(SentEnvelopeDetailEffect.NavigateEnvelopeEdit)
    fun popBackStack() = postSideEffect(SentEnvelopeDetailEffect.PopBackStack)
}
