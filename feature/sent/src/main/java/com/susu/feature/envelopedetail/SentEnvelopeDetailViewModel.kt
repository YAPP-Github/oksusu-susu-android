package com.susu.feature.envelopedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.util.to_yyyy_dot_MM_dot_dd
import com.susu.domain.usecase.envelope.GetEnvelopeDetailUseCase
import com.susu.feature.sent.navigation.SentRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
            val date = envelopeDetail.envelope.handedOverAt!!.to_yyyy_dot_MM_dot_dd().split(".")

            intent {
                copy(
                    money = envelopeDetail.envelope.amount.toInt(),
                    event = envelopeDetail.category.category,
                    name = envelopeDetail.friend.name,
                    relationship = envelopeDetail.relationship.relation,
                    date = date.joinToString(" ") { section ->
                        when (date.indexOf(section)) {
                            0 -> "${section}년"
                            1 -> "${section}월"
                            2 -> "${section}일"
                            else -> ""
                        }
                    },
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
