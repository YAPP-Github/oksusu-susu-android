package com.susu.feature.received.ledgeradd

import androidx.lifecycle.viewModelScope
import com.susu.core.model.Category
import com.susu.core.model.Ledger
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.extension.encodeToUri
import com.susu.domain.usecase.ledger.CreateLedgerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class LedgerAddViewModel @Inject constructor(
    private val createLedgerUseCase: CreateLedgerUseCase,
) : BaseViewModel<LedgerAddState, LedgerAddSideEffect>(
    LedgerAddState(),
) {
    private var selectedCategory: Category? = null
    private var name: String = ""
    private var startAt: LocalDateTime? = null
    private var endAt: LocalDateTime? = null

    fun updateSelectedCategory(category: Category?) = intent {
        selectedCategory = category
        copy(
            buttonEnabled = selectedCategory != null,
        )
    }

    fun updateName(name: String) = intent {
        this@LedgerAddViewModel.name = name
        copy(
            buttonEnabled = name.isNotEmpty(),
        )
    }

    fun updateDate(startAt: LocalDateTime?, endAt: LocalDateTime?) = intent {
        this@LedgerAddViewModel.startAt = startAt
        this@LedgerAddViewModel.endAt = endAt
        copy(
            buttonEnabled = startAt != null && endAt != null,
        )
    }

    fun goToPrevStep() {
        when (currentState.currentStep) {
            LedgerAddStep.CATEGORY -> postSideEffect(LedgerAddSideEffect.PopBackStack)
            LedgerAddStep.NAME -> intent { copy(currentStep = LedgerAddStep.CATEGORY) }
            LedgerAddStep.DATE -> intent { copy(currentStep = LedgerAddStep.NAME) }
        }
    }

    fun goToNextStep() {
        when (currentState.currentStep) {
            LedgerAddStep.CATEGORY -> intent { copy(currentStep = LedgerAddStep.NAME) }
            LedgerAddStep.NAME -> intent { copy(currentStep = LedgerAddStep.DATE) }
            LedgerAddStep.DATE -> createLedger()
        }
    }

    private fun createLedger() = viewModelScope.launch {
        intent { copy(isLoading = true) }
        createLedgerUseCase(
            ledger = Ledger(
                title = name,
                startAt = startAt!!.toKotlinLocalDateTime(),
                endAt = endAt!!.toKotlinLocalDateTime(),
                category = selectedCategory!!,
            ),
        ).onSuccess { ledger ->
            postSideEffect(LedgerAddSideEffect.PopBackStackWithLedger(Json.encodeToUri(ledger)))
        }.onFailure {
            postSideEffect(LedgerAddSideEffect.HandleException(it, ::createLedger))
        }
        intent { copy(isLoading = false) }
    }

}
