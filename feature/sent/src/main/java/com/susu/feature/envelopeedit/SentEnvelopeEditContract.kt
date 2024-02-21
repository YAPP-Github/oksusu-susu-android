package com.susu.feature.envelopeedit

import com.susu.core.model.Category
import com.susu.core.model.Relationship
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import com.susu.core.ui.util.currentDate
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDateTime

data class SentEnvelopeEditState(
    val isLoading: Boolean = false,
    val categoryConfig: PersistentList<Category> = persistentListOf(),
    val relationshipConfig: PersistentList<Relationship> = persistentListOf(),
    val amount: Long = 0L,
    val gift: String? = null,
    val memo: String? = null,
    val hasVisited: Boolean? = null,
    val handedOverAt: LocalDateTime = currentDate,
    val friendName: String = "",
    val relationshipId: Long? = null,
    val customRelationship: String? = null,
    val customRelationshipSaved: Boolean = false,
    val phoneNumber: String? = null,
    val categoryId: Int? = null,
    val customCategory: String? = null,
    val customCategorySaved: Boolean = false,
    val showCustomCategory: Boolean = false,
    val showCustomRelationship: Boolean = false,
    val showDatePickerSheet: Boolean = false,
) : UiState {
    val isSaveAvailable
        get() = amount > 0L && relationshipId != null && friendName.isNotEmpty() && categoryId != null &&
            (categoryId != categoryConfig.lastOrNull()?.id ||
                (categoryId == categoryConfig.lastOrNull()?.id && customCategorySaved)) &&
            (relationshipId != relationshipConfig.lastOrNull()?.id ||
                (relationshipId == relationshipConfig.lastOrNull()?.id && customRelationshipSaved))
}

sealed interface SentEnvelopeEditSideEffect : SideEffect {
    data object PopBackStack : SentEnvelopeEditSideEffect
    data class PopBackStackWithEditedFriendId(val id: Long) : SentEnvelopeEditSideEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : SentEnvelopeEditSideEffect
    data object FocusCustomCategory : SentEnvelopeEditSideEffect
    data object FocusCustomRelationship : SentEnvelopeEditSideEffect
    data object ShowMoneyNotValidSnackbar : SentEnvelopeEditSideEffect
    data object ShowNameNotValidSnackbar : SentEnvelopeEditSideEffect
    data object ShowRelationshipNotValidSnackbar : SentEnvelopeEditSideEffect
    data object ShowCategoryNotValidSnackbar : SentEnvelopeEditSideEffect
    data object ShowPresentNotValidSnackbar : SentEnvelopeEditSideEffect
    data object ShowMemoNotValidSnackbar : SentEnvelopeEditSideEffect
    data object ShowPhoneNotValidSnackbar : SentEnvelopeEditSideEffect
}
