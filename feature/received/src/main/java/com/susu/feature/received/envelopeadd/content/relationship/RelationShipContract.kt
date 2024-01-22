package com.susu.feature.received.envelopeadd.content.relationship

import com.susu.core.model.RelationShip
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class RelationShipState(
    val selectedRelationShip: RelationShip? = null,
    val relationShipConfig: PersistentList<RelationShip> = persistentListOf(),
    val customRelationShip: RelationShip = RelationShip(),
    val showTextFieldButton: Boolean = false,
    val isSavedCustomRelationShip: Boolean = false,
) : UiState {
    val isCustomRelationShipSelected = customRelationShip == selectedRelationShip
}

sealed interface RelationShipSideEffect : SideEffect {
    data object FocusCustomRelationShip : RelationShipSideEffect
    data class UpdateParentSelectedRelationShip(val relationShip: RelationShip?) : RelationShipSideEffect
}
