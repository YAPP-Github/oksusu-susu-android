package com.susu.domain.usecase.loginsignup

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.TermRepository
import javax.inject.Inject

class GetTermDetailUseCase @Inject constructor(
    private val termRepository: TermRepository,
) {

    suspend operator fun invoke(termId: Int) = runCatchingIgnoreCancelled {
        termRepository.getTermDetail(termId)
    }
}
