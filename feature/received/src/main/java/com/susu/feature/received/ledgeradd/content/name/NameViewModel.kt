package com.susu.feature.received.ledgeradd.content.name

import androidx.lifecycle.viewModelScope
import com.susu.core.ui.USER_INPUT_REGEX_INCLUDE_NUMBER
import com.susu.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NameViewModel @Inject constructor() : BaseViewModel<NameState, NameSideEffect>(
    NameState(),
) {
    fun updateName(name: String) {
        if (!USER_INPUT_REGEX_INCLUDE_NUMBER.matches(name)) { // 한글, 영문 0~10 글자
            if (name.length > 10) { // 길이 넘친 경우
                postSideEffect(NameSideEffect.ShowNotValidSnackbar)
            }
            return // 특수문자는 입력 안 됨
        }

        intent {
            postSideEffect(NameSideEffect.UpdateParentName(name))
            copy(name = name)
        }
    }

    fun showKeyboardIfTextEmpty() = viewModelScope.launch {
        if (currentState.name.isEmpty()) {
            delay(400L)
            postSideEffect(NameSideEffect.ShowKeyboard)
        }
    }
}
