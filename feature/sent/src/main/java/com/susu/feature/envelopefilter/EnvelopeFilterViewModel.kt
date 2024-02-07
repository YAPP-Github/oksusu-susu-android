package com.susu.feature.envelopefilter

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.susu.core.model.Friend
import com.susu.core.ui.argument.EnvelopeFilterArgument
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.extension.decodeFromUri
import com.susu.core.ui.extension.encodeToUri
import com.susu.domain.usecase.envelope.GetEnvelopeFilterConfigUseCase
import com.susu.domain.usecase.friend.SearchFriendUseCase
import com.susu.feature.envelopefilter.component.roundToStep
import com.susu.feature.sent.navigation.SentRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class EnvelopeFilterViewModel @Inject constructor(
    private val searchFriendUseCase: SearchFriendUseCase,
    private val getEnvelopeFilterConfigUseCase: GetEnvelopeFilterConfigUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<EnvelopeFilterState, EnvelopeFilterSideEffect>(
    EnvelopeFilterState(),
) {
    private val argument = savedStateHandle.get<String>(SentRoute.FILTER_ENVELOPE_ARGUMENT)!!
    private var filter = EnvelopeFilterArgument()
    private var step = 0f

    fun initData() = viewModelScope.launch {
        filter = Json.decodeFromUri(argument)
        getEnvelopeFilterConfigUseCase()
            .onSuccess {
                val maxFromAmount = if (filter.isSent) it.minSentAmount else it.minReceivedAmount
                val maxToAmount = if (filter.isSent) it.maxSentAmount else it.maxReceivedAmount

                step = when {
                    maxToAmount <= 10_00 -> 1f
                    maxToAmount <= 10_000 -> 1000f
                    maxToAmount <= 1_000_000 -> 10_000f // 0원 ~ 100만원 범위, 1만원 간격
                    maxToAmount <= 5_000_000 -> 50_000f // 101만원 ~ 500만원 범위, 5만원 간격
                    else -> 10_0000f // 500만원 이상, 10만원 간격
                }

                intent {
                    copy(
                        selectedFriendList = filter.selectedFriendList.toPersistentList(),
                        maxFromAmount = maxFromAmount,
                        maxToAmount = maxToAmount,
                        fromAmount = filter.fromAmount,
                        toAmount = filter.toAmount,
                        isSent = filter.isSent,
                    )
                }
            }
    }

    fun updateName(searchKeyword: String) = intent {
        copy(
            searchKeyword = searchKeyword,
        )
    }

    fun getFriendList(search: String) = viewModelScope.launch {
        searchFriendUseCase(name = search)
            .onSuccess {
                intent {
                    copy(
                        friendList = it.map { it.friend }.toPersistentList(),
                    )
                }
            }
    }

    fun selectFriend(friend: Friend) = intent {
        if (friend in selectedFriendList) return@intent this
        copy(
            selectedFriendList = selectedFriendList.add(friend),
        )
    }

    fun unselectFriend(friend: Friend) = intent {
        if (friend !in selectedFriendList) return@intent this
        copy(
            selectedFriendList = selectedFriendList.remove(friend),
        )
    }

    fun popBackStack() = postSideEffect(EnvelopeFilterSideEffect.PopBackStack)
    fun popBackStackWithFilter() {
        val filter = EnvelopeFilterArgument(
            selectedFriendList = currentState.selectedFriendList,
            fromAmount = currentState.fromAmount,
            toAmount = currentState.toAmount,
        )

        postSideEffect(EnvelopeFilterSideEffect.PopBackStackWithFilter(Json.encodeToUri(filter)))
    }

    fun updateMoneyRange(fromAmount: Float?, toAmount: Float?) = intent {
        copy(
            toAmount = toAmount?.roundToStep(step, maxFromAmount, maxToAmount)?.toLong(),
            fromAmount = fromAmount?.roundToStep(step, maxFromAmount, maxToAmount)?.toLong(),
        )
    }

    private fun Float.roundToStep(step: Float, min: Long, max: Long): Float {
        val value = (this / step).roundToInt() * step
        return when {
            value < min -> min.toFloat()
            value > max -> max.toFloat()
            else -> value
        }
    }

    fun clearFilter() = intent {
        copy(
            selectedFriendList = persistentListOf(),
            fromAmount = null,
            toAmount = null,
        )
    }
}
