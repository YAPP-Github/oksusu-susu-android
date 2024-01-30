package com.susu.feature.envelopeadd.content.memo

import com.susu.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MemoViewModel @Inject constructor() : BaseViewModel<MemoState, MemoSideEffect>(
    MemoState(),
) {
    fun updateMemo(memo: String?) = intent {
        postSideEffect(MemoSideEffect.UpdateParentMemo(memo))
        copy(memo = memo ?: "")
    }
}
