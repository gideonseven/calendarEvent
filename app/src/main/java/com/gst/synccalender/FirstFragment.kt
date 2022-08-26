package com.gst.synccalender

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.gst.synccalender.databinding.FragmentFirstBinding
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val requestMultiplePermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.entries.forEach {
            Timber.e(" === ${it.key} = ${it.value}")
        }
    }

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

        startCalendarPermission()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Ex. Launching ACCESS_FINE_LOCATION permission.
    private fun startCalendarPermission() {
        requestMultiplePermissions.launch(
            arrayOf(
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.WRITE_CALENDAR
            )
        )
    }
}