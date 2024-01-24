package com.susu.feature.received.envelopeadd.content.relationship

import androidx.lifecycle.viewModelScope
import com.susu.core.model.Relationship
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.envelope.GetRelationShipConfigListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RelationShipViewModel @Inject constructor(
    private val getRelationShipConfigUseCase: GetRelationShipConfigListUseCase,
) : BaseViewModel<RelationShipState, RelationShipSideEffect>(
    RelationShipState(),
) {
    private val parentSelectedRelationShip
        get() = with(currentState) {
            if (selectedRelationship == customRelationship && (customRelationship.customRelation.isNullOrEmpty() || isSavedCustomRelationShip.not())) {
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

    fun updateCustomRelationShipText(text: String) = intent {
        copy(
            selectedRelationship = customRelationship.copy(customRelation = text),
            customRelationship = customRelationship.copy(customRelation = text),
        )
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

    fun toggleTextFieldSaved() = intent {
        copy(
            isSavedCustomRelationShip = !isSavedCustomRelationShip,
        )
    }

    fun updateParentSelectedRelationShip(relationShip: Relationship? = parentSelectedRelationShip) = postSideEffect(
        RelationShipSideEffect.UpdateParentSelectedRelationShip(relationShip),
    )
}
