package com.susu.feature.envelopeadd.content.relationship

import com.susu.core.model.Relationship
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class RelationShipState(
    val selectedRelationship: Relationship? = null,
    val relationshipConfig: PersistentList<Relationship> = persistentListOf(),
    val customRelationship: Relationship = Relationship(),
    val showTextFieldButton: Boolean = false,
    val isSavedCustomRelationShip: Boolean = false,
) : UiState {
    val isCustomRelationShipSelected = customRelationship == selectedRelationship
}

sealed interface RelationShipSideEffect : SideEffect {
    data object FocusCustomRelationShip : RelationShipSideEffect
    data class UpdateParentSelectedRelationShip(val relationShip: Relationship?) : RelationShipSideEffect
    data object ShowNotValidSnackbar : RelationShipSideEffect
}
