package com.susu.feature.received.envelopeadd.content.phone

import androidx.lifecycle.viewModelScope
import com.susu.core.ui.base.BaseViewModel
import com.susu.feature.received.envelopeadd.content.present.PresentSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhoneViewModel @Inject constructor() : BaseViewModel<PhoneState, PhoneSideEffect>(
    PhoneState(),
) {
    fun updateName(name: String) = intent { copy(name = name) }
    fun updatePhone(phone: String?) = intent {
        postSideEffect(PhoneSideEffect.UpdateParentPhone(phone))
        copy(phone = phone ?: "")
    }

    fun showKeyboardIfTextEmpty() {
        if (currentState.phone.isEmpty()) {
            postSideEffect(PhoneSideEffect.ShowKeyboard)
        }
    }
}
