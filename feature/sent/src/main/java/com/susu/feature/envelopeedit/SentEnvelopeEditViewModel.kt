package com.susu.feature.envelopeedit

import androidx.lifecycle.viewModelScope
import com.susu.core.model.Category
import com.susu.core.model.Envelope
import com.susu.core.model.Friend
import com.susu.core.model.Relationship
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.util.currentDate
import com.susu.core.ui.util.getSafeLocalDateTime
import com.susu.domain.usecase.categoryconfig.GetCategoryConfigUseCase
import com.susu.domain.usecase.envelope.GetRelationShipConfigListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import javax.inject.Inject

@HiltViewModel
class SentEnvelopeEditViewModel @Inject constructor(
    private val getCategoryConfigUseCase: GetCategoryConfigUseCase,
    private val getRelationShipConfigListUseCase: GetRelationShipConfigListUseCase,
) : BaseViewModel<SentEnvelopeEditState, SentEnvelopeEditSideEffect>(
    SentEnvelopeEditState(),
) {

    fun initData() {
        // TODO: Argument로 받은 데이터로 설정. UI 개발을 위해 임의의 데이터 사용.
        val category = Category(
            id = 1,
            name = "결혼식",
        )
        val envelope = Envelope(
            amount = 150000,
            hasVisited = true,
            handedOverAt = currentDate.toKotlinLocalDateTime(),
            friend = Friend(
                name = "김철수",
            ),
            relationship = Relationship(
                id = 1,
                relation = "친구",
            ),
        )

        intent {
            copy(
                amount = envelope.amount,
                gift = envelope.gift,
                memo = envelope.memo,
                hasVisited = envelope.hasVisited,
                handedOverAt = envelope.handedOverAt.toJavaLocalDateTime(),
                friendName = envelope.friend.name,
                relationshipId = envelope.relationship.id,
                customRelationship = envelope.relationship.customRelation,
                phoneNumber = envelope.friend.phoneNumber.ifEmpty { null },
                categoryId = category.id,
                customCategory = category.customCategory,
            )
        }
    }

    fun getEnvelopConfig() {
        viewModelScope.launch {
            getRelationShipConfigListUseCase().onSuccess {
                intent { copy(relationshipConfig = it.dropLast(1)) }
            }
            getCategoryConfigUseCase().onSuccess {
                intent { copy(categoryConfig = it.dropLast(1)) }
            }
        }
    }

    fun popBackStack() = postSideEffect(SentEnvelopeEditSideEffect.PopBackStack)

    fun updateAmount(amount: Long) {
        intent { copy(amount = amount) }
    }

    fun updateGift(gift: String?) {
        intent { copy(gift = gift?.ifEmpty { null }) }
    }

    fun updateMemo(memo: String?) {
        intent { copy(memo = memo?.ifEmpty { null }) }
    }

    fun updateHasVisited(hasVisited: Boolean?) {
        intent {
            if (hasVisited == currentState.hasVisited) {
                copy(hasVisited = null)
            } else {
                copy(hasVisited = hasVisited)
            }
        }
    }

    fun updateHandedOverAt(year: Int, month: Int, day: Int) {
        intent { copy(handedOverAt = getSafeLocalDateTime(year = year, month = month, day = day)) }
    }

    fun updateFriendName(name: String) {
        intent { copy(friendName = name) }
    }

    fun updateRelationshipId(relationshipId: Long) {
        intent { copy(relationshipId = relationshipId) }
    }

    fun updateCustomRelationship(customRelationship: String) {
        intent { copy(customRelationship = customRelationship) }
    }

    fun updatePhoneNumber(phoneNumber: String?) {
        intent { copy(phoneNumber = phoneNumber?.ifEmpty { null }) }
    }

    fun updateCategoryId(categoryId: Int) {
        intent { copy(categoryId = categoryId) }
    }

    fun updateCustomCategory(customCategory: String?) {
        intent { copy(customCategory = customCategory) }
    }

    fun showCustomCategoryInput() {
        intent { copy(showCustomCategory = true) }
    }

    fun hideCustomCategoryInput() {
        intent { copy(showCustomCategory = false) }
    }

    fun showCustomRelationshipInput() {
        intent { copy(showCustomRelationship = true) }
    }

    fun hideCustomRelationshipInput() {
        intent { copy(showCustomRelationship = false) }
    }

    fun showDatePickerSheet() {
        intent { copy(showDatePickerSheet = true) }
    }

    fun hideDatePickerSheet() {
        intent { copy(showDatePickerSheet = false) }
    }
}
