package com.susu.domain.usecase.mypage

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.UserRepository
import javax.inject.Inject

class PatchUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(name: String, gender: String?, birth: Int?) = runCatchingIgnoreCancelled {
        userRepository.patchUserInfo(name = name, gender = gender, birth = birth)
    }
}
