package com.susu.domain.repository

import com.susu.core.model.OnboardVote

interface OnboardRepository {
    suspend fun getOnboardVote(): OnboardVote
}
