package com.susu.feature.received.envelopeedit

import com.susu.core.model.Envelope
import com.susu.core.model.Relationship
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class ReceivedEnvelopeEditState(
    val envelope: Envelope = Envelope(),
    val relationshipConfig: PersistentList<Relationship> = persistentListOf(Relationship()),
    val showCustomRelationButton: Boolean = false,
    val showDateBottomSheet: Boolean = false,
    val isRelationSaved: Boolean = false,
) : UiState {
    val buttonEnabled = when {
        envelope.friend.name.isEmpty() -> false
        envelope.relationship.id == relationshipConfig.last().id && isRelationSaved.not() -> false
        else -> true
    }
}

sealed interface ReceivedEnvelopeEditSideEffect : SideEffect {
    data object FocusCustomRelation : ReceivedEnvelopeEditSideEffect
    data object PopBackStack : ReceivedEnvelopeEditSideEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : ReceivedEnvelopeEditSideEffect
    data object ShowMoneyNotValidSnackbar : ReceivedEnvelopeEditSideEffect
    data object ShowNameNotValidSnackbar : ReceivedEnvelopeEditSideEffect
    data object ShowRelationshipNotValidSnackbar : ReceivedEnvelopeEditSideEffect
    data object ShowPresentNotValidSnackbar : ReceivedEnvelopeEditSideEffect
    data object ShowPhoneNotValidSnackbar : ReceivedEnvelopeEditSideEffect
    data object ShowMemoNotValidSnackbar : ReceivedEnvelopeEditSideEffect
}
