package com.susu.feature.received.envelopeadd

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.susu.core.model.Ledger
import com.susu.core.model.Relationship
import com.susu.core.model.exception.AlreadyRegisteredFriendPhoneNumberException
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.extension.decodeFromUri
import com.susu.core.ui.extension.encodeToUri
import com.susu.domain.usecase.envelope.CreateReceivedEnvelopeUseCase
import com.susu.feature.received.navigation.ReceivedRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ReceivedEnvelopeAddViewModel @Inject constructor(
    private val createReceivedEnvelopeUseCase: CreateReceivedEnvelopeUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ReceivedEnvelopeAddState, ReceivedEnvelopeAddSideEffect>(
    ReceivedEnvelopeAddState(),
) {
    private val ledger = run {
        Json.decodeFromUri<Ledger>(savedStateHandle.get<String>(ReceivedRoute.LEDGER_ARGUMENT_NAME)!!)
    }
    val categoryName = ledger.category.customCategory ?: ledger.category.name
    val initDate = ledger.startAt.toJavaLocalDateTime()

    private val ledgerId = ledger.id

    private var money: Long = 0
    private var name: String = ""
    private var friendId: Long? = null
    private var relationShip: Relationship? = null
    private var date: LocalDateTime? = initDate
    private var moreStep: List<EnvelopeAddStep> = emptyList()
    private var hasVisited: Boolean? = null
    private var present: String? = null
    private var phoneNumber: String? = null
    private var memo: String? = null

    private val skipRelationshipStep
        get() = friendId != null

    private fun createEnvelope() = viewModelScope.launch {
        createReceivedEnvelopeUseCase(
            param = CreateReceivedEnvelopeUseCase.Param(
                friendId = friendId,
                friendName = name,
                categoryId = ledger.category.id.toLong(),
                customCategory = ledger.category.customCategory,
                phoneNumber = phoneNumber,
                relationshipId = relationShip?.id,
                customRelation = relationShip?.customRelation,
                ledgerId = ledgerId,
                amount = money,
                gift = present,
                memo = memo,
                handedOverAt = date!!.toKotlinLocalDateTime(),
                hasVisited = hasVisited,
            ),
        ).onSuccess {
            postSideEffect(ReceivedEnvelopeAddSideEffect.PopBackStackWithEnvelope(Json.encodeToUri(it)))
        }.onFailure { throwable ->
            when (throwable) {
                is AlreadyRegisteredFriendPhoneNumberException -> postSideEffect(ReceivedEnvelopeAddSideEffect.ShowSnackbar(throwable.message))
                else -> postSideEffect(ReceivedEnvelopeAddSideEffect.HandleException(throwable, ::createEnvelope))
            }
        }
    }

    fun goToPrevStep() = intent {
        val prevStep = when (currentStep) {
            EnvelopeAddStep.MONEY -> {
                postSideEffect(ReceivedEnvelopeAddSideEffect.PopBackStack)
                EnvelopeAddStep.MONEY
            }

            EnvelopeAddStep.NAME -> EnvelopeAddStep.MONEY
            EnvelopeAddStep.RELATIONSHIP -> EnvelopeAddStep.NAME
            EnvelopeAddStep.DATE -> {
                if (skipRelationshipStep) {
                    EnvelopeAddStep.NAME
                } else {
                    EnvelopeAddStep.RELATIONSHIP
                }
            }

            EnvelopeAddStep.MORE -> EnvelopeAddStep.DATE
            else -> goToPrevStepInMore(currentStep)
        }

        copy(
            currentStep = prevStep,
            buttonEnabled = prevStep == EnvelopeAddStep.MORE,
            lastPage = false,
        )
    }

    private fun goToPrevStepInMore(currentStep: EnvelopeAddStep): EnvelopeAddStep {
        if (moreStep.isEmpty()) {
            return EnvelopeAddStep.MORE
        }

        val prevStepIndex = run {
            val currentStepIndex = moreStep.indexOf(currentStep)
            if (currentStepIndex == -1) EnvelopeAddStep.MORE.ordinal else currentStepIndex - 1
        }

        return moreStep.getOrNull(prevStepIndex) ?: EnvelopeAddStep.MORE
    }

    fun goToNextStep() = intent {
        val nextStep = when (currentStep) {
            EnvelopeAddStep.MONEY -> EnvelopeAddStep.NAME
            EnvelopeAddStep.NAME -> {
                if (skipRelationshipStep) {
                    EnvelopeAddStep.DATE
                } else {
                    EnvelopeAddStep.RELATIONSHIP
                }
            }

            EnvelopeAddStep.RELATIONSHIP -> EnvelopeAddStep.DATE
            EnvelopeAddStep.DATE -> EnvelopeAddStep.MORE
            else -> goToNextStepInMore(currentStep)
        }

        if (nextStep == null) {
            createEnvelope()
            return@intent this
        }

        copy(
            currentStep = nextStep,
            lastPage = nextStep == moreStep.lastOrNull(),
        )
    }

    private fun goToNextStepInMore(currentStep: EnvelopeAddStep): EnvelopeAddStep? {
        if (moreStep.isEmpty() || currentState.lastPage) {
            return null
        }

        val nextStepIndex = when (currentStep) {
            EnvelopeAddStep.MORE -> 0
            else -> {
                val currentStepIndex = moreStep.indexOf(currentStep)
                if (currentStepIndex == -1) null else currentStepIndex + 1
            }
        } ?: return null

        return moreStep.getOrNull(nextStepIndex)
    }

    fun updateMoney(money: Long) = intent {
        this@ReceivedEnvelopeAddViewModel.money = money
        copy(
            buttonEnabled = money > 0,
        )
    }

    fun updateName(name: String) = intent {
        this@ReceivedEnvelopeAddViewModel.name = name
        copy(
            buttonEnabled = name.isNotEmpty(),
        )
    }

    fun updateFriendId(friendId: Long?) {
        this.friendId = friendId
    }

    fun updateSelectedRelationShip(relationShip: Relationship?) = intent {
        this@ReceivedEnvelopeAddViewModel.relationShip = relationShip
        copy(
            buttonEnabled = relationShip != null,
        )
    }

    fun updateMoreStep(moreStep: List<EnvelopeAddStep>) {
        this@ReceivedEnvelopeAddViewModel.moreStep = moreStep
    }

    fun updateHasVisited(hasVisited: Boolean?) = intent {
        this@ReceivedEnvelopeAddViewModel.hasVisited = hasVisited
        copy(
            buttonEnabled = hasVisited != null,
        )
    }

    fun updatePresent(present: String?) = intent {
        this@ReceivedEnvelopeAddViewModel.present = present
        copy(
            buttonEnabled = !present.isNullOrEmpty(),
        )
    }

    fun updatePhoneNumber(phoneNumber: String?) = intent {
        this@ReceivedEnvelopeAddViewModel.phoneNumber = phoneNumber
        copy(
            buttonEnabled = !phoneNumber.isNullOrEmpty(),
        )
    }

    fun updateMemo(memo: String?) = intent {
        this@ReceivedEnvelopeAddViewModel.memo = memo
        copy(
            buttonEnabled = !memo.isNullOrEmpty(),
        )
    }

    fun updateDate(date: LocalDateTime?) = intent {
        this@ReceivedEnvelopeAddViewModel.date = date
        copy(
            buttonEnabled = date != null,
        )
    }
}
