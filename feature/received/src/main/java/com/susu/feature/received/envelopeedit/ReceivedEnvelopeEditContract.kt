package com.susu.feature.received.envelopeedit

import com.susu.core.model.Envelope
import com.susu.core.model.Relationship
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import com.susu.feature.received.envelopeadd.content.relationship.RelationShipSideEffect
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class ReceivedEnvelopeEditState(
    val envelope: Envelope = Envelope(),
    val relationshipConfig: PersistentList<Relationship> = persistentListOf(Relationship()),
    val showCustomRelationButton: Boolean = false,
    val showDateBottomSheet: Boolean = false,
    val isRelationSaved: Boolean = false,
) : UiState {
}

sealed interface ReceivedEnvelopeEditSideEffect : SideEffect {
    data object PopBackStack : ReceivedEnvelopeEditSideEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : ReceivedEnvelopeEditSideEffect
}
