package com.susu.feature.envelopeedit

import com.susu.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SentEnvelopeEditViewModel @Inject constructor() : BaseViewModel<SentEnvelopeEditState, SentEnvelopeEditSideEffect>(
    SentEnvelopeEditState(),
) {
    fun popBackStack() = postSideEffect(SentEnvelopeEditSideEffect.PopBackStack)
}
