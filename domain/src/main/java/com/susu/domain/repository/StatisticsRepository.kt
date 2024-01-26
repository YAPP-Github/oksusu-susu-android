package com.susu.domain.repository

import com.susu.core.model.MyStatistics
import com.susu.core.model.StatisticsOption

interface StatisticsRepository {
    suspend fun getMyStatistics(): MyStatistics
    suspend fun getStatisticOptionConfig(): StatisticsOption
}
