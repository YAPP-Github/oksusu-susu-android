package com.susu.feature.envelopeedit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.susu.core.model.EnvelopeDetail
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.extension.decodeFromUri
import com.susu.core.ui.util.getSafeLocalDateTime
import com.susu.domain.usecase.categoryconfig.GetCategoryConfigUseCase
import com.susu.domain.usecase.envelope.EditSentEnvelopeUseCase
import com.susu.domain.usecase.envelope.GetRelationShipConfigListUseCase
import com.susu.feature.sent.navigation.SentRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class SentEnvelopeEditViewModel @Inject constructor(
    private val getCategoryConfigUseCase: GetCategoryConfigUseCase,
    private val getRelationShipConfigListUseCase: GetRelationShipConfigListUseCase,
    private val editSentEnvelopeUseCase: EditSentEnvelopeUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<SentEnvelopeEditState, SentEnvelopeEditSideEffect>(
    SentEnvelopeEditState(),
) {
    private val envelopeDetail = Json.decodeFromUri<EnvelopeDetail>(
        savedStateHandle.get<String>(SentRoute.ENVELOPE_DETAIL_ARGUMENT_NAME)!!,
    )

    fun initData() {
        viewModelScope.launch {
            val relationshipConfigResult = getRelationShipConfigListUseCase()
            val categoryConfigResult = getCategoryConfigUseCase()

            if (relationshipConfigResult.isSuccess && categoryConfigResult.isSuccess) {
                val relationshipConfig = relationshipConfigResult.getOrDefault(emptyList()).toPersistentList()
                val categoryConfig = categoryConfigResult.getOrDefault(emptyList()).toPersistentList()
                with(envelopeDetail) {
                    intent {
                        copy(
                            categoryConfig = categoryConfig,
                            relationshipConfig = relationshipConfig,
                            amount = envelope.amount,
                            gift = envelope.gift,
                            memo = envelope.memo,
                            hasVisited = envelope.hasVisited,
                            handedOverAt = envelope.handedOverAt.toJavaLocalDateTime(),
                            friendName = friend.name,
                            relationshipId = relationship.id,
                            customRelationship = relationship.customRelation,
                            phoneNumber = friend.phoneNumber.ifEmpty { null },
                            categoryId = category.id,
                            customCategory = category.customCategory,
                            showCustomCategory = category.id == categoryConfig.last().id,
                            customCategorySaved = category.id == categoryConfig.last().id,
                            showCustomRelationship = relationship.id == relationshipConfig.last().id,
                            customRelationshipSaved = relationship.id == relationshipConfig.last().id,
                        )
                    }
                }
            }
        }
    }

    fun editEnvelope() {
        viewModelScope.launch {
            intent { copy(isLoading = true) }
            editSentEnvelopeUseCase(
                param = with(currentState) {
                    EditSentEnvelopeUseCase.Param(
                        envelopeId = envelopeDetail.envelope.id,
                        envelopeType = envelopeDetail.envelope.type,
                        friendId = envelopeDetail.friend.id,
                        friendName = friendName,
                        phoneNumber = phoneNumber,
                        relationshipId = relationshipId,
                        customRelation = customRelationship,
                        categoryId = categoryId,
                        customCategory = customCategory,
                        amount = amount,
                        gift = gift,
                        memo = memo,
                        handedOverAt = handedOverAt.toKotlinLocalDateTime(),
                        hasVisited = hasVisited,
                    )
                },
            ).onSuccess {
                postSideEffect(SentEnvelopeEditSideEffect.PopBackStackWithEditedFriendId(it.friend.id))
            }.onFailure {
                postSideEffect(SentEnvelopeEditSideEffect.HandleException(it, ::editEnvelope))
            }
            intent { copy(isLoading = false) }
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
