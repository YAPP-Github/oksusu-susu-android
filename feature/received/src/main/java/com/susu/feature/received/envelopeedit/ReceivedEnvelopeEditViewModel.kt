package com.susu.feature.received.envelopeedit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.susu.core.model.Envelope
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.extension.decodeFromUri
import com.susu.core.ui.extension.encodeToUri
import com.susu.domain.usecase.envelope.DeleteEnvelopeUseCase
import com.susu.domain.usecase.envelope.GetEnvelopeUseCase
import com.susu.domain.usecase.envelope.GetRelationShipConfigListUseCase
import com.susu.feature.received.navigation.ReceivedRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReceivedEnvelopeEditViewModel @Inject constructor(
    private val getRelationShipConfigListUseCase: GetRelationShipConfigListUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ReceivedEnvelopeEditState, ReceivedEnvelopeEditSideEffect>(
    ReceivedEnvelopeEditState(),
) {
    private val argument = savedStateHandle.get<String>(ReceivedRoute.ENVELOPE_ARGUMENT_NAME)!!

    private var isFirstVisited: Boolean = true

    fun initData() = viewModelScope.launch {
        if (isFirstVisited.not()) return@launch
        isFirstVisited = false

        val envelope = Json.decodeFromUri<Envelope>(argument)

        getRelationShipConfigListUseCase()
            .onSuccess {
                intent {
                    copy(
                        envelope = envelope,
                        relationshipConfig = it.toPersistentList(),
                        showCustomRelationButton = it.last().id == envelope.relationship.id,
                        isRelationSaved = it.last().id == envelope.relationship.id,
                    )
                }
            }
    }


    fun popBackStack() = postSideEffect(ReceivedEnvelopeEditSideEffect.PopBackStack)

}
