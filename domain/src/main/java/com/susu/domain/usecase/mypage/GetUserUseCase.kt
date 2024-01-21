package com.susu.domain.usecase.mypage

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke() = runCatchingIgnoreCancelled {
        userRepository.getUserInfo()
    }
}
