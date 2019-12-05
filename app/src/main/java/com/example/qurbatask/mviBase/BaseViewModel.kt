
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qurbatask.mviBase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

const val MVI_TAG = "Mvi"

@FlowPreview
@ExperimentalCoroutinesApi
abstract class BaseViewModel<E : MviEvent, S : MviState>(
    initialState: S,
    type: FlowType = FlowType.SWITCH,
    private val tag: String = MVI_TAG
) : ViewModel() {

    private val _uiEvents = Channel<E>(Channel.BUFFERED)
    private val _actions = Channel<MviAction<MviResult>>(Channel.BUFFERED)
    private val _results = BroadcastChannel<MviResult>(Channel.BUFFERED)

    private val _stateLiveData = MutableLiveData<S>()
    val stateLiveData: LiveData<S> = _stateLiveData

    var currentState: S = initialState
        private set

    private val eventsActions = _uiEvents
        .consumeAsFlow()
        .map { event -> eventToAction(event) }
        .onEach { intent -> _actions.send(intent) }
        .flowOn(Dispatchers.Default)
        .launchIn(viewModelScope)

    private val results = _actions
        .consumeAsFlow()
        .onEach { action -> Timber.tag(tag).d("Action -> $action") }
        .onAction(type) { action -> action.invoke() }
        .onEach { result -> Timber.tag(tag).d("Result -> $result") }
        .onEach { result -> _results.send(result) }
        .flowOn(Dispatchers.Default)
        .launchIn(viewModelScope)

    val resultsActions = _results
        .asFlow()
        .map { result -> resultToAction(result) }
        .filterNotNull()
        .onEach { action -> _actions.send(action) }
        .flowOn(Dispatchers.Default)
        .launchIn(viewModelScope)

    val states = _results.asFlow()
        .scan(initialState) { state, result -> resultToUiModel(state, result) }
        .onEach { newState -> Timber.tag(tag).d("State  -> $newState") }
        .distinctUntilChanged()
        .flowOn(Dispatchers.Default)
        .onEach { newState -> _stateLiveData.value = newState }
        .onEach { newState -> currentState = newState }
        .launchIn(viewModelScope)

    val effects = _results.asFlow()
        .map { result -> resultToEffect(result) }
        .onEach { effect -> Timber.tag(tag).d("Effect -> $effect") }

    fun sendEvent(uiEvent: E) = viewModelScope.launch {
        Timber.tag(tag).d("Event  -> $uiEvent")
        _uiEvents.send(uiEvent)
    }

    protected fun sendResult(result: MviResult) {
        viewModelScope.launch {
            _results.send(result)
        }
    }

    protected abstract suspend fun eventToAction(uiEvent: E): MviAction<MviResult>
    protected abstract suspend fun resultToUiModel(state: S, result: MviResult): S
    protected abstract suspend fun resultToAction(result: MviResult): MviAction<MviResult>?
    protected abstract suspend fun resultToEffect(result: MviResult): MviEffect?

}