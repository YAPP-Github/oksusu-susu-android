package com.susu.feature.envelope

import com.susu.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SentEnvelopeViewModel @Inject constructor() : BaseViewModel<SentEnvelopeState, SentEnvelopeSideEffect>(
    SentEnvelopeState(),
) {
    fun popBackStack() = postSideEffect(SentEnvelopeSideEffect.PopBackStack)
}
