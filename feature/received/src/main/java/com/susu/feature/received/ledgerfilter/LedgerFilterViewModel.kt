package com.susu.feature.received.ledgerfilter

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.susu.core.model.Ledger
import com.susu.core.model.exception.NotFoundLedgerException
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.extension.decodeFromUri
import com.susu.core.ui.extension.encodeToUri
import com.susu.core.ui.util.to_yyyy_dot_MM_dot_dd
import com.susu.domain.usecase.ledger.DeleteLedgerUseCase
import com.susu.feature.received.navigation.ReceivedRoute
import com.susu.feature.received.navigation.argument.FilterArgument
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class LedgerFilterViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<LedgerFilterState, LedgerFilterSideEffect>(
    LedgerFilterState(),
) {
    private val argument = savedStateHandle.get<String>(ReceivedRoute.FILTER_ARGUMENT_NAME)!!
    private var filter = FilterArgument()

    fun initData() {
        filter = Json.decodeFromUri(argument)
        intent {
            copy(
                selectedCategoryList = filter.selectedCategoryList,
                startAt = filter.startAt?.toJavaLocalDateTime(),
                endAt = filter.endAt?.toJavaLocalDateTime(),
            )
        }
    }
}
