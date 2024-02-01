package com.susu.feature.envelopeedit

import com.susu.core.model.Category
import com.susu.core.model.Relationship
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import com.susu.core.ui.util.currentDate
import java.time.LocalDateTime

data class SentEnvelopeEditState(
    val isLoading: Boolean = false,
    val categoryConfig: List<Category> = emptyList(),
    val relationshipConfig: List<Relationship> = emptyList(),
    val amount: Long = 0L,
    val gift: String? = null,
    val memo: String? = null,
    val hasVisited: Boolean? = null,
    val handedOverAt: LocalDateTime = currentDate,
    val friendName: String = "",
    val relationshipId: Long = 0,
    val customRelationship: String? = null,
    val customRelationshipSaved: Boolean = false,
    val phoneNumber: String? = null,
    val categoryId: Int = 0,
    val customCategory: String? = null,
    val customCategorySaved: Boolean = false,
    val showCustomCategory: Boolean = false,
    val showCustomRelationship: Boolean = false,
    val showDatePickerSheet: Boolean = false,
) : UiState

sealed interface SentEnvelopeEditSideEffect : SideEffect {
    data object PopBackStack : SentEnvelopeEditSideEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : SentEnvelopeEditSideEffect
    data object FocusCustomCategory : SentEnvelopeEditSideEffect
    data object FocusCustomRelationship : SentEnvelopeEditSideEffect
}
