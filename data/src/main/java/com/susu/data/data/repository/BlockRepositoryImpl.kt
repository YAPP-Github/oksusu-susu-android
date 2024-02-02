package com.susu.data.data.repository

import com.susu.data.remote.api.BlockService
import com.susu.data.remote.model.request.BlockUserRequest
import com.susu.domain.repository.BlockRepository
import javax.inject.Inject

class BlockRepositoryImpl @Inject constructor(
    private val blockService: BlockService,
) : BlockRepository {
    override suspend fun blockUser(targetId: Long) = blockService.blockUser(
        BlockUserRequest(
            targetId = targetId,
        ),
    ).getOrThrow()
}
