package com.susu.feature.received.ledgeradd.content.name

import androidx.lifecycle.viewModelScope
import com.susu.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NameViewModel @Inject constructor(
) : BaseViewModel<NameState, NameSideEffect>(
    NameState(),
) {
    fun updateName(name: String) = intent {
        postSideEffect(NameSideEffect.UpdateParentName(name))
        copy(name = name)
    }
}
