package com.susu.feature.envelopeadd

import com.susu.core.model.Category
import com.susu.core.model.Relationship
import com.susu.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class EnvelopeAddViewModel @Inject constructor() : BaseViewModel<EnvelopeAddState, EnvelopeAddEffect>(EnvelopeAddState()) {

    private var money: Long = 0
    private var name: String = ""
    private var friendId: Long? = null
    private var relationShip: Relationship? = null
    private var category: Category? = null
    private var date: LocalDateTime? = null
    private var moreStep: List<EnvelopeAddStep> = emptyList()
    private var hasVisited: Boolean? = null
    private var present: String? = null
    private var phoneNumber: String? = null
    private var memo: String? = null

    fun goNextStep() {
        when (uiState.value.currentStep) {
            EnvelopeAddStep.MONEY -> intent { copy(currentStep = EnvelopeAddStep.NAME) }
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
            postSideEffect(EnvelopeAddEffect.CompleteEnvelopeAdd)
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
                intent {
                    if (friendId == null) {
                        copy(currentStep = EnvelopeAddStep.RELATIONSHIP)
                    } else {
                        copy(currentStep = EnvelopeAddStep.NAME)
                    }
                }
            }

            EnvelopeAddStep.DATE -> intent { copy(currentStep = EnvelopeAddStep.EVENT) }
            EnvelopeAddStep.MORE -> intent { copy(currentStep = EnvelopeAddStep.DATE) }
            else -> goPrevMoreStep()
        }
    }

    private fun goPrevMoreStep() {
        val moreStep = moreStep.filter { uiState.value.currentStep.ordinal > it.ordinal }.maxOrNull()

        if (moreStep == null) {
            intent { copy(currentStep = EnvelopeAddStep.MORE) }
        } else {
            intent { copy(currentStep = moreStep) }
        }
    }

    fun updateMoney(money: Long) = intent {
        this@EnvelopeAddViewModel.money = money
        copy(buttonEnabled = money > 0L)
    }

    fun updateName(name: String) = intent {
        this@EnvelopeAddViewModel.name = name
        copy(buttonEnabled = name.isNotEmpty())
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
}
