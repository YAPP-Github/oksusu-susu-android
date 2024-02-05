package com.susu.domain.usecase.block

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.BlockRepository
import javax.inject.Inject

class BlockUserUseCase @Inject constructor(
    private val blockRepository: BlockRepository,
) {
    suspend operator fun invoke(id: Long) = runCatchingIgnoreCancelled {
        blockRepository.blockUser(id)
    }
}
