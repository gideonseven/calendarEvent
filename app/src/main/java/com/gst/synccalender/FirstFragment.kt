package com.gst.synccalender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gst.synccalender.databinding.FragmentFirstBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.Duration.Companion.milliseconds

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //startDate = 26 August 2022 3AM UTC
        //endDate = 27 August 2022 3AM UTC
        val startDate =  CalendarHelper.parseRFC3339Calendar("2022-08-26T3:00:00+00:00")
        val endDate =  CalendarHelper.parseRFC3339Calendar("2022-08-27T3:00:00+00:00")
            binding.buttonFirst.setOnClickListener {
            QueryHandler.insertEvent(requireContext(), startDate.timeInMillis, endDate.timeInMillis, "TAGIHAN BAYAR WEE")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}