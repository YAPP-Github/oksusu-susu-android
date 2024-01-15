package com.susu.domain.usecase.categoryconfig

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.CategoryConfigRepository
import com.susu.domain.repository.LedgerRepository
import java.time.LocalDateTime
import javax.inject.Inject

class GetCategoryConfigUseCase @Inject constructor(
    private val categoryConfigRepository: CategoryConfigRepository,
) {
    suspend operator fun invoke() = runCatchingIgnoreCancelled {
        categoryConfigRepository.getCategoryConfig()
    }
}
