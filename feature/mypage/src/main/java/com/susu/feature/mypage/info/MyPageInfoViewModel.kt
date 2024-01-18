package com.susu.feature.mypage.info

import androidx.lifecycle.viewModelScope
import com.susu.core.ui.Gender

import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.mypage.GetUserUseCase
import com.susu.domain.usecase.mypage.PatchUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageInfoViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val patchUserUseCase: PatchUserUseCase,
) : BaseViewModel<MyPageInfoState, MyPageInfoEffect>(MyPageInfoState()) {

    init {
        viewModelScope.launch {
            intent { copy(isLoading = true) }
            getUserUseCase().onSuccess {
                intent {
                    copy(
                        userName = it.name,
                        userGender = when (it.gender) {
                            "M" -> Gender.MALE
                            "F" -> Gender.FEMALE
                            else -> Gender.NONE
                        },
                        userBirth = it.birth,
                    )
                }
            }.onFailure {
                postSideEffect(MyPageInfoEffect.ShowSnackBar(it.message ?: ""))
            }
            intent { copy(isLoading = false) }
        }
    }

    fun startEdit() {
        intent {
            copy(
                isEditing = true,
                editName = "",
                editGender = uiState.value.userGender,
                editBirth = uiState.value.userBirth,
            )
        }
    }

    fun showDatePicker() {
        intent { copy(showDatePicker = true) }
    }

    fun updateName(name: String) {
        intent { copy(editName = name) }
    }

    fun updateGender(gender: Gender) {
        if (gender == uiState.value.editGender) { // 선택된 성별 재선택 시 선택 취소 처리
            intent { copy(editGender = Gender.NONE) }
        } else {
            intent { copy(editGender = gender) }
        }
    }

    fun updateBirth(birth: Int) {
        intent { copy(editBirth = birth, showDatePicker = false) }
    }

    fun completeEdit() {
        viewModelScope.launch {
            intent { copy(isLoading = true) }
            patchUserUseCase(name = uiState.value.editName, gender = uiState.value.editGender.content, birth = uiState.value.editBirth)
                .onSuccess {
                    intent {
                        copy(
                            userName = it.name,
                            userBirth = it.birth,
                            userGender = when (it.gender) {
                                "M" -> Gender.MALE
                                "F" -> Gender.FEMALE
                                else -> Gender.NONE
                            },
                        )
                    }
                }.onFailure {
                    Timber.d(it)
                    postSideEffect(MyPageInfoEffect.ShowSnackBar(it.message ?: ""))
                }
            intent { copy(isLoading = false, isEditing = false) }
        }
    }

    fun cancelEdit() {
        intent { copy(isEditing = false) }
    }
}
