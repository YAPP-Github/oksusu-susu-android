package com.susu.feature.received.envelopeadd.content.memo

import androidx.lifecycle.viewModelScope
import com.susu.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoViewModel @Inject constructor() : BaseViewModel<MemoState, MemoSideEffect>(
    MemoState(),
) {
    fun updateMemo(memo: String?) {
        if (memo != null && memo.length > 30) {
            postSideEffect(MemoSideEffect.ShowNotValidSnackbar)
            return
        }

        intent {
            postSideEffect(MemoSideEffect.UpdateParentMemo(memo?.trim()))
            copy(memo = memo ?: "")
        }
    }

    fun showKeyboardIfTextEmpty() = viewModelScope.launch {
        if (currentState.memo.isEmpty()) {
            delay(500L)
            postSideEffect(MemoSideEffect.ShowKeyboard)
        }
    }
}
