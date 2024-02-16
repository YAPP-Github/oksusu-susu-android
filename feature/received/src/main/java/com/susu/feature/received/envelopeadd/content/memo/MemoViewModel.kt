package com.susu.feature.received.envelopeadd.content.memo

import androidx.lifecycle.viewModelScope
import com.susu.core.ui.base.BaseViewModel
import com.susu.feature.received.envelopeadd.content.present.PresentSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoViewModel @Inject constructor() : BaseViewModel<MemoState, MemoSideEffect>(
    MemoState(),
) {
    fun updateMemo(memo: String?) = intent {
        postSideEffect(MemoSideEffect.UpdateParentMemo(memo))
        copy(memo = memo ?: "")
    }

    fun showKeyboardIfTextEmpty() {
        if (currentState.memo.isEmpty()) {
            postSideEffect(MemoSideEffect.ShowKeyboard)
        }
    }
}
