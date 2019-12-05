package com.example.qurbatask.mviBase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

enum class FlowType {
    SWITCH,
    SUSPEND,
    PARALLEL
}

@FlowPreview
@ExperimentalCoroutinesApi
inline fun Flow<MviAction<MviResult>>.onAction(type: FlowType, crossinline transform: suspend (action: MviAction<MviResult>) -> Flow<MviResult>): Flow<MviResult> {
    return when(type) {
        FlowType.SWITCH -> flatMapLatest { action -> transform(action) }
        FlowType.SUSPEND -> flatMapConcat { action -> transform(action) }
        FlowType.PARALLEL -> flatMapMerge { action -> transform(action) }
    }
}