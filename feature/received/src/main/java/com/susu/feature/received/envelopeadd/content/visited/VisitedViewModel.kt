package com.susu.feature.received.envelopeadd.content.visited

import com.susu.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VisitedViewModel @Inject constructor() : BaseViewModel<VisitedState, VisitedSideEffect>(
    VisitedState(),
) {
    fun updateCategoryName(name: String) = intent {
        copy(categoryName = name)
    }


    fun updateVisited(visited: Boolean?) = intent {
        postSideEffect(VisitedSideEffect.UpdateParentVisited(visited))
        copy(visited = visited)
    }
}
