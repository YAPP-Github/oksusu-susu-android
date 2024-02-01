package com.susu.domain.repository

interface BlockRepository {

    suspend fun blockUser(
        targetId: Long,
    )
}
