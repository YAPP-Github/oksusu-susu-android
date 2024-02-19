package com.susu.feature.envelopeadd

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.susu.core.model.Category
import com.susu.core.model.Friend
import com.susu.core.model.Relationship
import com.susu.core.ui.MONEY_MAX_VALUE
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.extension.decodeFromUri
import com.susu.domain.usecase.envelope.CreateSentEnvelopeUseCase
import com.susu.feature.sent.navigation.SentRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class EnvelopeAddViewModel @Inject constructor(
    private val createSentEnvelopeUseCase: CreateSentEnvelopeUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<EnvelopeAddState, EnvelopeAddEffect>(EnvelopeAddState()) {
    private val friendArgument = runCatching {
        savedStateHandle.get<String>(SentRoute.FRIEND_ARGUMENT_NAME)?.let {
            Json.decodeFromUri<Friend>(it)
        }
    }.getOrNull()

    private var money: Long = 0
    private var friendId: Long? = null
    private var relationShip: Relationship? = null
    private var category: Category? = null
    private var date: LocalDateTime? = null
    private var moreStep: List<EnvelopeAddStep> = emptyList()
    private var hasVisited: Boolean? = null
    private var present: String? = null
    private var phoneNumber: String? = null
    private var memo: String? = null

    fun initData() {
        friendArgument?.let {
            updateFriendId(it.id)
            updateName(it.name)
            updatePhoneNumber(it.phoneNumber.ifEmpty { null })
            intent { copy(fromEnvelope = true) }
        } ?: intent { copy(fromEnvelope = false) }
    }

    private fun createEnvelope() {
        viewModelScope.launch {
            createSentEnvelopeUseCase(
                param = CreateSentEnvelopeUseCase.Param(
                    friendId = friendId,
                    friendName = currentState.friendName,
                    phoneNumber = phoneNumber,
                    relationshipId = relationShip?.id,
                    customRelation = relationShip?.customRelation,
                    amount = money,
                    gift = present,
                    memo = memo,
                    handedOverAt = date!!.toKotlinLocalDateTime(),
                    hasVisited = hasVisited,
                    category = category!!,
                ),
            ).onSuccess {
                postSideEffect(EnvelopeAddEffect.PopBackStackWithRefresh)
            }.onFailure {
                postSideEffect(EnvelopeAddEffect.HandleException(it, ::createEnvelope))
            }
        }
    }

    fun goNextStep() {
        when (uiState.value.currentStep) {
            EnvelopeAddStep.MONEY -> {
                if (friendArgument == null) {
                    intent { copy(currentStep = EnvelopeAddStep.NAME) }
                } else {
                    intent { copy(currentStep = EnvelopeAddStep.EVENT) }
                }
            }

            EnvelopeAddStep.NAME -> {
                intent {
                    if (friendId == null) {
                        copy(currentStep = EnvelopeAddStep.RELATIONSHIP)
                    } else {
                        copy(currentStep = EnvelopeAddStep.EVENT)
                    }
                }
            }

            EnvelopeAddStep.RELATIONSHIP -> intent { copy(currentStep = EnvelopeAddStep.EVENT) }
            EnvelopeAddStep.EVENT -> intent { copy(currentStep = EnvelopeAddStep.DATE) }
            EnvelopeAddStep.DATE -> intent { copy(currentStep = EnvelopeAddStep.MORE) }
            else -> goNextMoreStep()
        }
    }

    private fun goNextMoreStep() {
        val moreStep = moreStep.filter { uiState.value.currentStep.ordinal < it.ordinal }.minOrNull()

        if (moreStep == null) {
            createEnvelope()
        } else {
            intent { copy(currentStep = moreStep) }
        }
    }

    fun goPrevStep() {
        when (uiState.value.currentStep) {
            EnvelopeAddStep.MONEY -> postSideEffect(EnvelopeAddEffect.PopBackStack)
            EnvelopeAddStep.NAME -> intent { copy(currentStep = EnvelopeAddStep.MONEY) }
            EnvelopeAddStep.RELATIONSHIP -> intent { copy(currentStep = EnvelopeAddStep.NAME) }
            EnvelopeAddStep.EVENT -> {
                val prevStep = when {
                    friendId == null -> EnvelopeAddStep.RELATIONSHIP
                    friendArgument != null -> EnvelopeAddStep.MONEY
                    else -> EnvelopeAddStep.NAME
                }
                intent { copy(currentStep = prevStep) }
            }

            EnvelopeAddStep.DATE -> intent { copy(currentStep = EnvelopeAddStep.EVENT) }
            EnvelopeAddStep.MORE -> intent { copy(currentStep = EnvelopeAddStep.DATE) }
            else -> goPrevMoreStep()
        }
    }

    private fun goPrevMoreStep() {
        val moreStep = moreStep.filter { uiState.value.currentStep.ordinal > it.ordinal }.maxOrNull()

        if (moreStep == null) {
            intent {
                copy(
                    currentStep = EnvelopeAddStep.MORE,
                    buttonEnabled = true,
                )
            }
        } else {
            intent { copy(currentStep = moreStep) }
        }
    }

    fun updateMoney(money: Long) = intent {
        this@EnvelopeAddViewModel.money = money
        copy(buttonEnabled = money in 1L..MONEY_MAX_VALUE)
    }

    fun updateName(name: String) = intent {
        copy(friendName = name, buttonEnabled = name.isNotEmpty())
    }

    fun updateFriendId(friendId: Long?) {
        this.friendId = friendId
    }

    fun updateSelectedRelationShip(relationShip: Relationship?) = intent {
        this@EnvelopeAddViewModel.relationShip = relationShip
        copy(
            buttonEnabled = relationShip != null,
        )
    }

    fun updateSelectedCategory(category: Category?) = intent {
        this@EnvelopeAddViewModel.category = category
        copy(
            buttonEnabled = category != null,
        )
    }

    fun updateMoreStep(moreStep: List<EnvelopeAddStep>) {
        this@EnvelopeAddViewModel.moreStep = moreStep
    }

    fun updateHasVisited(hasVisited: Boolean?) = intent {
        this@EnvelopeAddViewModel.hasVisited = hasVisited
        copy(
            buttonEnabled = hasVisited != null,
        )
    }

    fun updatePresent(present: String?) = intent {
        this@EnvelopeAddViewModel.present = present
        copy(
            buttonEnabled = !present.isNullOrEmpty(),
        )
    }

    fun updatePhoneNumber(phoneNumber: String?) = intent {
        this@EnvelopeAddViewModel.phoneNumber = phoneNumber
        copy(
            buttonEnabled = !phoneNumber.isNullOrEmpty(),
        )
    }

    fun updateMemo(memo: String?) = intent {
        this@EnvelopeAddViewModel.memo = memo
        copy(
            buttonEnabled = !memo.isNullOrEmpty(),
        )
    }

    fun updateDate(date: LocalDateTime?) = intent {
        this@EnvelopeAddViewModel.date = date
        copy(
            buttonEnabled = date != null,
        )
    }

    fun logBackButtonClickEvent() {
        postSideEffect(EnvelopeAddEffect.LogClickBackButton(currentState.currentStep))
    }

    fun logNextButtonClickEvent() {
        postSideEffect(EnvelopeAddEffect.LogClickNextButton(currentState.currentStep))
    }
}
