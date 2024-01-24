package com.susu.domain.usecase.statistics

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.UserRepository
import javax.inject.Inject

class CheckAdditionalUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke() = runCatchingIgnoreCancelled {
        val userInfo = userRepository.getUserInfo()
        userInfo.birth in 1930..2030 && (userInfo.gender == "M" || userInfo.gender == "F")
    }
}
