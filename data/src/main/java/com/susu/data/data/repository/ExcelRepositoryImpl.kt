package com.susu.data.data.repository

import android.app.DownloadManager
import android.os.Environment
import androidx.core.net.toUri
import com.susu.domain.repository.ExcelRepository
import com.susu.domain.repository.TokenRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class ExcelRepositoryImpl @Inject constructor(
    private val downloadManager: DownloadManager,
    private val tokenRepository: TokenRepository,
) : ExcelRepository {
    override suspend fun downloadEnvelopExcel(): Long {
        val token = tokenRepository.getAccessToken().firstOrNull() ?: return -1L
        val url = "https://api.oksusu.site/api/v1/excel/all-envelopes"

        val request = DownloadManager.Request(url.toUri())
            .setMimeType("application/vnd.ms-excel")
            .setAllowedOverMetered(true)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setTitle("수수")
            .addRequestHeader("X-SUSU-AUTH-TOKEN", token)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "수수_기록.xlsx")

        return downloadManager.enqueue(request)
    }
}
