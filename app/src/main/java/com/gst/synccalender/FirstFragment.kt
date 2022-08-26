package com.gst.synccalender

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.gst.synccalender.databinding.FragmentFirstBinding
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

        //logging purpose
        permissions.entries.forEach {
            Timber.e(" === ${it.key} = ${it.value}")
        }

        //check all permission granted or not
        val isAllPermissionGranted = permissions.entries.all { it.value }
        if (isAllPermissionGranted) addEvent()
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
        binding.buttonAdd.setOnClickListener {
            startCalendarPermission()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // launching permission
    // https://stackoverflow.com/a/68320856
    private fun startCalendarPermission() {
        requestMultiplePermissions.launch(
            arrayOf(
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.WRITE_CALENDAR
            )
        )
    }

    private fun addEvent() {
        //startDate = 26 August 2022 11AM UTC
        //endDate = 27 August 2022 11AM UTC
        val startDate = CalendarHelper.parseRFC3339Calendar("2022-08-26T11:00:00+00:00")
        val endDate = CalendarHelper.parseRFC3339Calendar("2022-08-27T11:00:00+00:00")
        QueryHandler.insertEvent(
            context = requireContext(),
            startTime = startDate.timeInMillis,
            endTime = endDate.timeInMillis,
            title = "REMINDER PAY YOUR BILL",
            description = "FRIENDLY REMINDER DON'T FORGET YA!!"
        )
    }
}