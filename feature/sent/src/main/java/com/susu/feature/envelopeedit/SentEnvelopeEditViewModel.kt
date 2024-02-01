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
import kotlinx.collections.immutable.toPersistentList
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
            id = 5,
            name = "커스텀",
            customCategory = "커스텀",
        )
        val envelope = Envelope(
            amount = 150000,
            hasVisited = true,
            handedOverAt = currentDate.toKotlinLocalDateTime(),
            friend = Friend(
                name = "김철수",
            ),
            relationship = Relationship(
                id = 5,
                relation = "나",
                customRelation = "나",
            ),
        )

        viewModelScope.launch {
            val relationshipConfigResult = getRelationShipConfigListUseCase()
            val categoryConfigResult = getCategoryConfigUseCase()

            if (relationshipConfigResult.isSuccess && categoryConfigResult.isSuccess) {
                val relationshipConfig = relationshipConfigResult.getOrDefault(emptyList()).toPersistentList()
                val categoryConfig = categoryConfigResult.getOrDefault(emptyList()).toPersistentList()
                intent {
                    copy(
                        categoryConfig = categoryConfig,
                        relationshipConfig = relationshipConfig,
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
                        showCustomCategory = category.id == categoryConfig.last().id,
                        customCategorySaved = category.id == categoryConfig.last().id,
                        showCustomRelationship = envelope.relationship.id == relationshipConfig.last().id,
                        customRelationshipSaved = envelope.relationship.id == relationshipConfig.last().id,
                    )
                }
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
        intent {
            copy(
                showCustomCategory = true,
                categoryId = categoryConfig.last().id,
                customCategorySaved = false,
            )
        }
        postSideEffect(SentEnvelopeEditSideEffect.FocusCustomCategory)
    }

    fun toggleCustomCategoryInputSaved() = intent {
        copy(
            customCategorySaved = !customCategorySaved,
        )
    }

    fun hideCustomCategoryInput() {
        intent { copy(showCustomCategory = false) }
    }

    fun showCustomRelationshipInput() {
        intent {
            copy(
                showCustomRelationship = true,
                relationshipId = relationshipConfig.last().id,
                customRelationshipSaved = false,
            )
        }
        postSideEffect(SentEnvelopeEditSideEffect.FocusCustomRelationship)
    }

    fun toggleCustomRelationshipInputSaved() = intent {
        copy(
            customRelationshipSaved = !customRelationshipSaved,
        )
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
