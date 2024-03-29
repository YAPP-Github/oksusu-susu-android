package com.susu.feature.envelopeadd.content.phone

import androidx.lifecycle.viewModelScope
import com.susu.core.ui.PHONE_NUM_REGEX
import com.susu.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhoneViewModel @Inject constructor() : BaseViewModel<PhoneState, PhoneSideEffect>(
    PhoneState(),
) {
    fun updateName(name: String) = intent { copy(name = name) }
    fun updatePhone(phone: String?) {
        if (phone != null) {
            if (!PHONE_NUM_REGEX.matches(phone)) return
            if (phone.length > 11) {
                postSideEffect(PhoneSideEffect.ShowNotValidSnackbar)
                return
            }
        }

        intent {
            postSideEffect(PhoneSideEffect.UpdateParentPhone(phone))
            copy(phone = phone ?: "")
        }
    }

    fun showKeyboardIfTextEmpty() = viewModelScope.launch {
        if (currentState.phone.isEmpty()) {
            delay(500L)
            postSideEffect(PhoneSideEffect.ShowKeyboard)
        }
    }
}
