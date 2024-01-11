package com.susu.feature.envelopedetail

import com.susu.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SentEnvelopeDetailViewModel @Inject constructor() : BaseViewModel<SentEnvelopeDetailState, SentEnvelopeDetailSideEffect>(
    SentEnvelopeDetailState(),
) {
    fun popBackStack() = postSideEffect(SentEnvelopeDetailSideEffect.PopBackStack)
}
