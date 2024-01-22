package com.susu.domain.repository

interface ExcelRepository {
    suspend fun downloadEnvelopExcel(): Long
}
