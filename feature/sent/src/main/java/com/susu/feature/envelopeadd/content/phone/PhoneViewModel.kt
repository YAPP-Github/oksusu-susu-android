package com.susu.feature.envelopeadd.content.phone

import com.susu.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
}
