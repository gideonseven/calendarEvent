package com.gst.synccalender.features.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.gst.synccalender.R
import com.gst.synccalender.databinding.FragmentCalendarBinding
import com.gst.synccalender.databinding.ViewStateErrorBinding
import com.gst.synccalender.utils.AppFragment
import com.gst.synccalender.utils.network.State
import com.gst.synccalender.utils.network.handleResponseState
import com.gst.synccalender.utils.network.observe
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class CalendarFragment : AppFragment<FragmentCalendarBinding>(R.layout.fragment_calendar) {

    private val viewModel: CalendarViewModel by viewModels()

    private val args: CalendarFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel.token = args.token
            buttonAdd.setOnClickListener {
                viewModel.setEvent(CalendarContract.CalendarEvent.SubmitCalendarEvent)
            }
        }

        viewLifecycleOwner.lifecycleScope.observe(
            lifecycle = lifecycle,
            state = { handleState() },
            effect = { handleEffect() }
        )
    }

    private suspend fun handleState() {
        viewModel.uiState.collect { uiState ->
            nonNullContext.handleResponseState(uiState.responseSubmitEvent,
                getUiStateFlow(),
                onLoading = {
                    updateUIStateFlow(State.LOADING)
                },
                onFailed = { _, model ->
                    updateUIStateFlow(State.ERROR)
                    val bindingError = binding.viewState.getView(State.ERROR)?.let { it ->
                        ViewStateErrorBinding.bind(it)
                    }
                    bindingError?.errorDescription?.text = model.message
                    bindingError?.errorRetry?.setOnClickListener {
                        viewModel.setEvent(
                            CalendarContract.CalendarEvent.SubmitCalendarEvent
                        )
                    }
                }
            )
        }
    }

    private suspend fun handleEffect() {
        viewModel.effect.collect {

        }
    }
}