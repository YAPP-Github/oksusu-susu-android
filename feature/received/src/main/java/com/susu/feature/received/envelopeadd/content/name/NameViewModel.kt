package com.susu.feature.received.envelopeadd.content.name

import com.susu.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NameViewModel @Inject constructor() : BaseViewModel<NameState, NameSideEffect>(
    NameState(),
) {
    fun updateName(name: String) = intent {
        postSideEffect(NameSideEffect.UpdateParentName(name))
        copy(name = name)
    }
}
