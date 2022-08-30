package com.gst.synccalender.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


/**
 * Created by gideon on 8/12/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
abstract class CoreViewModel<Event : UiEvent, State : UiState, Effect : UiEffect> :
    BindingViewModel() {

    fun <T> Flow<T>.asLiveDataOnViewModelScope(): LiveData<T> {
        return asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }

    private val initialState: State by lazy { createInitialState() }
    abstract fun createInitialState(): State

    private val currentState: State
        get() = uiState.value

    /**
     * For handling UiState we use StateFlow. StateFlow is just like LiveData but have initial value.
     * So we have always a state. It is also a kind of SharedFlow.
     * We always want to receive last view state when UI become visible.
     */
    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    /**
     * With the shared flow, events are broadcast to an unknown number (zero or more) of subscribers.
     * In the absence of a subscriber, any posted event is immediately dropped.
     * It is a design pattern to use for events that must be processed immediately or not at all.
     *
     *
     * For handling UiEvent we use SharedFlow. We want to drop event if there is not any subscriber.
     */
    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    /**
     * With the channel, each event is delivered to a single subscriber.
     * An attempt to post an event without subscribers will suspend as soon as the channel buffer becomes full,
     * waiting for a subscriber to appear. Posted events are never dropped by default.
     *
     * For handling UiEffect we use Channels.
     * Because Channels are hot and we do not need to show side effect again when orientation changed or UI become
     * visible again. Simply we want to replicate SingleLiveEvent behavior.
     */
    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeEvents()
    }

    /**
     * Start listening to Event
     */
    private fun subscribeEvents() {
        viewModelScope.launch {
            event.collect {
                handleEvent(it)
            }
        }
    }

    /**
     * Handle each event
     */
    abstract fun handleEvent(event: Event)


    /**
     * Set new Event
     */
    fun setEvent(event: Event) {
        val newEvent = event
        viewModelScope.launch { _event.emit(newEvent) }
    }


    /**
     * Set new Ui State
     */
    protected fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    /**
     * Set new Effect
     */
    fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel("")
    }
}