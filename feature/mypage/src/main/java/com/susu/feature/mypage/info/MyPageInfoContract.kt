package com.susu.feature.mypage.info

import com.susu.core.ui.Gender
import com.susu.core.ui.USER_INPUT_REGEX
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

sealed interface MyPageInfoEffect : SideEffect {
    data object PopBackStack : MyPageInfoEffect
    data object ShowNameNotValidSnackBar : MyPageInfoEffect
    data class ShowSnackBar(val msg: String) : MyPageInfoEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : MyPageInfoEffect
}

data class MyPageInfoState(
    val isLoading: Boolean = false,
    val isEditing: Boolean = false,
    val showDatePicker: Boolean = false,
    val userName: String = "",
    val userBirth: Int = 0,
    val userGender: Gender = Gender.NONE,
    val editName: String = "",
    val editBirth: Int = 0,
    val editGender: Gender = Gender.NONE,
) : UiState {
    val birthEdited: Boolean = userBirth != editBirth
    val isEditNameValid: Boolean = editName.isNotEmpty() && USER_INPUT_REGEX.matches(editName.trim())
}
