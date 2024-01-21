package com.susu.domain.usecase.mypage

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.ExcelRepository
import javax.inject.Inject

class DownloadExcelUseCase @Inject constructor(
    private val excelRepository: ExcelRepository,
) {
    suspend operator fun invoke() = runCatchingIgnoreCancelled {
        excelRepository.downloadEnvelopExcel()
    }
}
