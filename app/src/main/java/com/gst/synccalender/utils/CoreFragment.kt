package com.gst.synccalender.utils

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.gst.synccalender.utils.network.State
import com.skydoves.bindables.BindingFragment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


/**
 * Created by gideon on 8/12/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
abstract class CoreFragment<T : ViewDataBinding> constructor(
    @LayoutRes contentLayoutId: Int
) : BindingFragment<T>(contentLayoutId) {

    // Closed property to store current UI state inside MutableStateFlow, also it is observed
    private val _uiStateFlow: MutableStateFlow<State> = MutableStateFlow(State.CONTENT)

    // Use this to get access into Context that attached into this. In advance, reduce getContext() and
    // requireContext() method. Note: This will be available after onAttach(Context) executed!
    lateinit var nonNullContext: Context


    override fun onAttach(context: Context) {
        super.onAttach(context)
        nonNullContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            _uiStateFlow
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    when (it) {
                        State.LOADING -> getViewState()?.viewState = (State.LOADING)
                        State.CONTENT -> getViewState()?.viewState = (State.CONTENT)
                        State.ERROR -> getViewState()?.viewState = (State.ERROR)
                        State.EMPTY -> getViewState()?.viewState = (State.EMPTY)
                    }
                }
        }
    }

    // Update UI state into StateFlow
    fun updateUIStateFlow(state: State) {
        _uiStateFlow.value = state
    }

    fun getUiStateFlow(): MutableStateFlow<State> = _uiStateFlow

    // Override this method to get the view state instance that connected into UI state
    open fun getViewState(): MultiStateView? = null
}