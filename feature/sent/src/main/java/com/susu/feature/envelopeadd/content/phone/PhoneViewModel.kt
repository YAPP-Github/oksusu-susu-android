package com.susu.feature.envelopeadd.content.phone

import com.susu.core.ui.base.BaseViewModel
import com.susu.feature.envelopeadd.content.name.NameEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhoneViewModel @Inject constructor() : BaseViewModel<PhoneState, PhoneSideEffect>(
    PhoneState(),
) {
    fun updateName(name: String) = intent { copy(name = name) }
    fun updatePhone(phone: String?) {
        if (phone != null && phone.length > 11) {
            postSideEffect(PhoneSideEffect.ShowNotValidSnackbar)
            return
        }

        intent {
            postSideEffect(PhoneSideEffect.UpdateParentPhone(phone))
            copy(phone = phone ?: "")
        }
    }

    fun showKeyboardIfTextEmpty() {
        if (currentState.phone.isEmpty()) {
            postSideEffect(PhoneSideEffect.ShowKeyboard)
        }
    }
}
