package com.susu.feature.received.envelopeadd.content.relationship

import androidx.lifecycle.viewModelScope
import com.susu.core.model.RelationShip
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
            if (selectedRelationShip == customRelationShip && (customRelationShip.customRelation.isNullOrEmpty() || isSavedCustomRelationShip.not())) {
                null
            } else {
                selectedRelationShip
            }
        }

    fun getRelationShipConfig() = viewModelScope.launch {
        if (currentState.relationShipConfig.isNotEmpty()) return@launch

        getRelationShipConfigUseCase()
            .onSuccess {
                intent {
                    copy(
                        relationShipConfig = it.dropLast(1).toPersistentList(),
                        customRelationShip = it.last(),
                    )
                }
            }
            .onFailure { }
    }

    fun selectRelationShip(relationShip: RelationShip) = intent { copy(selectedRelationShip = relationShip) }

    fun selectCustomRelationShip() = intent {
        postSideEffect(RelationShipSideEffect.FocusCustomRelationShip)
        copy(selectedRelationShip = customRelationShip)
    }

    fun updateCustomRelationShipText(text: String) = intent {
        copy(
            selectedRelationShip = customRelationShip.copy(customRelation = text),
            customRelationShip = customRelationShip.copy(customRelation = text),
        )
    }

    fun showCustomRelationShipTextField() = intent {
        copy(
            showTextFieldButton = true,
            selectedRelationShip = customRelationShip,
        )
    }

    fun hideCustomRelationShipTextField() = intent {
        copy(
            isSavedCustomRelationShip = false,
            showTextFieldButton = false,
            selectedRelationShip = if (isCustomRelationShipSelected) null else selectedRelationShip,
            customRelationShip = customRelationShip.copy(customRelation = ""),
        )
    }

    fun toggleTextFieldSaved() = intent {
        copy(
            isSavedCustomRelationShip = !isSavedCustomRelationShip,
        )
    }

    fun updateParentSelectedRelationShip(relationShip: RelationShip? = parentSelectedRelationShip) = postSideEffect(
        RelationShipSideEffect.UpdateParentSelectedRelationShip(relationShip),
    )
}
