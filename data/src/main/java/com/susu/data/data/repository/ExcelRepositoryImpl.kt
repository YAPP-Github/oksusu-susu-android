package com.susu.data.data.repository

import android.app.DownloadManager
import android.os.Environment
import androidx.core.net.toUri
import com.susu.core.model.exception.UnknownException
import com.susu.domain.repository.ExcelRepository
import com.susu.domain.repository.TokenRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class ExcelRepositoryImpl @Inject constructor(
    private val downloadManager: DownloadManager,
    private val tokenRepository: TokenRepository,
) : ExcelRepository {
    override suspend fun downloadEnvelopExcel(): Long {
        val token = tokenRepository.getAccessToken().firstOrNull() ?: throw UnknownException()

        val request = DownloadManager.Request(url.toUri())
            .setMimeType(mimeType)
            .setAllowedOverMetered(true)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setTitle(downloaderName)
            .addRequestHeader(headerTokenName, token)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

        return downloadManager.enqueue(request)
    }

    companion object {
        private const val url = "https://api.oksusu.site/api/v1/excel/all-envelopes"
        private const val mimeType = "application/vnd.ms-excel"
        private const val downloaderName = "수수"
        private const val headerTokenName = "X-SUSU-AUTH-TOKEN"
        private const val fileName = "수수_기록.xlsx"
    }
}
