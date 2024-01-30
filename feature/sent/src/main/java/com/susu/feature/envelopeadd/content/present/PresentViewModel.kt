package com.susu.feature.envelopeadd.content.present

import com.susu.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PresentViewModel @Inject constructor() : BaseViewModel<PresentState, PresentSideEffect>(
    PresentState(),
) {
    fun updatePresent(present: String?) = intent {
        postSideEffect(PresentSideEffect.UpdateParentPresent(present))
        copy(present = present ?: "")
    }
}
