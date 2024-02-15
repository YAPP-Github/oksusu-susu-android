package com.susu.feature.envelopeadd.content.present

import com.susu.core.ui.USER_INPUT_REGEX_LONG
import com.susu.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PresentViewModel @Inject constructor() : BaseViewModel<PresentState, PresentSideEffect>(
    PresentState(),
) {
    fun updatePresent(present: String?) {
        if (present != null && !USER_INPUT_REGEX_LONG.matches(present)) {
            if (present.length > 30) {
                postSideEffect(PresentSideEffect.ShowNotValidSnackbar)
            }
            return
        }

        intent {
            postSideEffect(PresentSideEffect.UpdateParentPresent(present))
            copy(present = present ?: "")
        }
    }
}
