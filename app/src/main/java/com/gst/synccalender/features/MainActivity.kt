package com.gst.synccalender.features

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.gst.synccalender.R
import com.gst.synccalender.databinding.ActivityContainerBinding
import com.gst.synccalender.utils.AppActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppActivity<ActivityContainerBinding>(R.layout.activity_container) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
            val inflater = navHostFragment.navController.navInflater
            val graph = inflater.inflate(R.navigation.nav_calendar)
            val navController = navHostFragment.navController
            navController.setGraph(graph, intent.extras)
        }
    }
}