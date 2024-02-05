package com.susu.domain.repository

import com.susu.core.model.MyStatistics
import com.susu.core.model.SusuStatistics

interface StatisticsRepository {
    suspend fun getMyStatistics(): MyStatistics
    suspend fun getSusuStatistics(age: String, relationshipId: Int, categoryId: Int): SusuStatistics
}
