package com.susu.feature.envelopefilter

import androidx.lifecycle.SavedStateHandle
import com.susu.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EnvelopeFilterViewModel @Inject constructor(
    @Suppress("detekt:UnusedPrivateProperty")
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<EnvelopeFilterState, EnvelopeFilterSideEffect>(
    EnvelopeFilterState(),
) {
//    private val argument = savedStateHandle.get<String>(ReceivedRoute.FILTER_ARGUMENT_NAME)!!
//    private var filter = FilterArgument()

    fun initData() {
        initFilter()
    }

    private fun initFilter() {
//        filter = Json.decodeFromUri(argument)
//        intent {
//            copy(
//                selectedCategoryList = filter.selectedCategoryList.toPersistentList(),
//                startAt = filter.startAt?.toJavaLocalDateTime(),
//                endAt = filter.endAt?.toJavaLocalDateTime(),
//            )
//        }
    }

    fun popBackStack() = postSideEffect(EnvelopeFilterSideEffect.PopBackStack)
    fun popBackStackWithFilter() {
//        val filter = FilterArgument(
//            selectedCategoryList = currentState.selectedCategoryList,
//            startAt = currentState.startAt?.toKotlinLocalDateTime(),
//            endAt = currentState.endAt?.toKotlinLocalDateTime(),
//        )
//
//        postSideEffect(EnvelopeFilterSideEffect.PopBackStackWithFilter(Json.encodeToUri(filter)))
    }
}
