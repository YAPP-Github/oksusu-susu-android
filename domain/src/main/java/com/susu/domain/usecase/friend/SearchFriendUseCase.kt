package com.susu.domain.usecase.friend

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.FriendRepository
import javax.inject.Inject

class SearchFriendUseCase @Inject constructor(
    private val friendRepository: FriendRepository,
) {
    suspend operator fun invoke(name: String) = runCatchingIgnoreCancelled {
        friendRepository.searchFriend(
            name = name,
        )
    }
}
