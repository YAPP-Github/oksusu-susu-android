package com.susu.domain.repository

import com.susu.core.model.MyStatistics

interface StatisticsRepository {
    suspend fun getMyStatistics(): MyStatistics
}
