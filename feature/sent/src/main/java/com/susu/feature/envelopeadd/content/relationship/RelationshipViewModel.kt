package com.susu.feature.envelopeadd.content.relationship

import androidx.lifecycle.viewModelScope
import com.susu.core.model.Relationship
import com.susu.core.ui.USER_INPUT_REGEX
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.envelope.GetRelationShipConfigListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RelationshipViewModel @Inject constructor(
    private val getRelationShipConfigUseCase: GetRelationShipConfigListUseCase,
) : BaseViewModel<RelationShipState, RelationShipSideEffect>(RelationShipState()) {

    private val parentSelectedRelationShip
        get() = with(currentState) {
            if (selectedRelationship == customRelationship &&
                (
                    customRelationship.customRelation.isNullOrEmpty() ||
                        isSavedCustomRelationShip.not()
                    )
            ) {
                null
            } else {
                selectedRelationship
            }
        }

    fun getRelationShipConfig() = viewModelScope.launch {
        if (currentState.relationshipConfig.isNotEmpty()) return@launch

        getRelationShipConfigUseCase()
            .onSuccess {
                intent {
                    copy(
                        relationshipConfig = it.dropLast(1).toPersistentList(),
                        customRelationship = it.last(),
                    )
                }
            }
            .onFailure { }
    }

    fun selectRelationShip(relationShip: Relationship) = intent { copy(selectedRelationship = relationShip) }

    fun selectCustomRelationShip() = intent {
        postSideEffect(RelationShipSideEffect.FocusCustomRelationShip)
        copy(selectedRelationship = customRelationship)
    }

    fun updateCustomRelationShipText(text: String) {
        if (!USER_INPUT_REGEX.matches(text)) { // 한글, 영문 0~10 글자
            if (text.length > 10) { // 길이 넘친 경우
                postSideEffect(RelationShipSideEffect.ShowNotValidSnackbar)
            }
            return // 특수문자는 입력 안 됨
        }

        intent {
            copy(
                selectedRelationship = customRelationship.copy(customRelation = text),
                customRelationship = customRelationship.copy(customRelation = text),
            )
        }
    }

    fun showCustomRelationShipTextField() = intent {
        copy(
            showTextFieldButton = true,
            selectedRelationship = customRelationship,
        )
    }

    fun hideCustomRelationShipTextField() = intent {
        copy(
            isSavedCustomRelationShip = false,
            showTextFieldButton = false,
            selectedRelationship = if (isCustomRelationShipSelected) null else selectedRelationship,
            customRelationship = customRelationship.copy(customRelation = ""),
        )
    }

    fun toggleTextFieldSaved() {
        intent {
            if (isSavedCustomRelationShip) {
                copy(
                    isSavedCustomRelationShip = false,
                )
            } else {
                copy(
                    isSavedCustomRelationShip = true,
                    customRelationship = customRelationship.copy(
                        customRelation = customRelationship.customRelation?.trim(),
                    ),
                )
            }
        }
    }

    fun updateParentSelectedRelationShip(relationShip: Relationship? = parentSelectedRelationShip) = postSideEffect(
        RelationShipSideEffect.UpdateParentSelectedRelationShip(relationShip),
    )
}
