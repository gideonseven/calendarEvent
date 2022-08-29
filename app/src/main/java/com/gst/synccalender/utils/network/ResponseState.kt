package com.gst.synccalender.utils.network

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.CoroutineContext


/**
 * Created by gideon on 8/12/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
open class CoreRequestType

sealed class ResponseState<out Type : CoreRequestType, out Result : Any?> {
    class Loading<Type : CoreRequestType>(val type: Type) : ResponseState<Type, Nothing>()
    class Progress<Type : CoreRequestType>(val type: Type, val progress: Int) :
        ResponseState<Type, Nothing>()

    data class Success<Type : CoreRequestType, Result : Any?>(val type: Type, val value: Result?) :
        ResponseState<Type, Result>()

    class Fail<Type : CoreRequestType>(val type: Type, val errorModel: SimpleNetworkModel) :
        ResponseState<Type, Nothing>()

    class NoAuth<Type : CoreRequestType>(val type: Type) : ResponseState<Type, Nothing>()
    object Empty : ResponseState<Nothing, Nothing>()
}

// Dipakai Fragment/Activity untuk update UI dari [uiStateFlow] dan handling ResponseState
suspend inline fun <reified Type : CoreRequestType, reified Result> Context.handleResponseState(
    responseState: ResponseState<Type, Result>,
    uiStateFlow: MutableStateFlow<State>,
    noinline onLoading: suspend (Type) -> Unit,
    noinline onProgress: suspend (Type, Int) -> Unit = { _, _ -> },
    noinline onFailed: suspend (Type, SimpleNetworkModel) -> Unit = { _, _ -> },
    noinline onSuccess: suspend (Type, Result?) -> Unit = { _, _ -> },
    noinline onNotAuthorized: suspend (Type) -> Unit = {}
) {
    when (responseState) {
        is ResponseState.Loading -> {
            onLoading(responseState.type)
        }
        is ResponseState.Progress -> {
            onProgress(responseState.type, responseState.progress)
        }
        is ResponseState.Fail -> {
            uiStateFlow.value = State.CONTENT
            onFailed(responseState.type, responseState.errorModel)
        }
        is ResponseState.Success -> {
            uiStateFlow.value = State.CONTENT
            onSuccess(responseState.type, responseState.value)
        }
        is ResponseState.NoAuth -> {
            uiStateFlow.value = State.CONTENT
            onNotAuthorized(responseState.type)
        }
        else -> {
            /* Do nothing */
        }
    }
}

// Dipakai ViewModel untuk me-notify setelah request-nya selesai dan meng-handle ResponseState
suspend fun <Type : CoreRequestType, Result> Flow<ResponseState<Type, Result>>.handleResult(
    updateState: (ResponseState<Type, Result>) -> Unit,
    onFailed: (Type, SimpleNetworkModel) -> Unit = { _, _ -> },
    onSuccess: (Type, Result?) -> Unit = { _, _ -> },
    onNotAuthorized: (Type) -> Unit = {},
) {
    collect {
        updateState(it)
        when (it) {
            is ResponseState.Success -> onSuccess(it.type, it.value)
            is ResponseState.Fail -> onFailed(it.type, it.errorModel)
            is ResponseState.NoAuth -> onNotAuthorized(it.type)
            else -> {
                /* Do nothing */
            }
        }
    }
}

fun CoroutineScope.observe(
    lifecycle: Lifecycle,
    state: suspend () -> Unit,
    effect: suspend () -> Unit
) {
    //https://medium.com/androiddevelopers/a-safer-way-to-collect-flows-from-android-uis-23080b1f8bda
    launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                state.invoke()
            }

            launch {
                effect.invoke()
            }
        }
    }
}

fun ViewModel.launchRequest(
    cancellableRequest: Boolean = true,
    doRequest: suspend CoroutineScope.() -> Unit
): Job {
    var context: CoroutineContext = Dispatchers.IO

    if (!cancellableRequest) context += NonCancellable

    return viewModelScope.launch(context) {
        doRequest(this)
    }
}

fun <Type : CoreRequestType, Result, Mapper> Flow<ResponseState<Type, Result?>>.succeedMapper(mapper: (Result?) -> Mapper?): Flow<ResponseState<Type, Mapper?>> {
    val resource = this
    return flow {
        resource.collect {
            if (it is ResponseState.Success) {
                this.emit(ResponseState.Success(it.type, mapper(it.value)))
            }
        }
    }
}