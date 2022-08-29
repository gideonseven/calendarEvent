package com.gst.synccalender.feature

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.gst.synccalender.R
import com.gst.synccalender.databinding.ActivityMainBinding
import com.gst.synccalender.utils.network.Api
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.HttpUrl.Companion.toHttpUrl
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        //Manage the callback case:
        var code = ""
        var error = ""
        val data: Uri? = intent.data
        if (data != null && !data.scheme.isNullOrEmpty()) {
            if (Api.REDIRECT_URI_ROOT == data.scheme) {
                code = data.getQueryParameter(Api.CODE) ?: ""
                error = data.getQueryParameter(Api.ERROR_CODE) ?: ""
            }
        } else {
            getOauthChooseAccount()
        }

        Timber.e("=== *** code $code")
        Timber.e("=== *** error $error")


        binding.fabDelete.setOnClickListener {
            //TODO CLICK FAB
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun getOauthChooseAccount() {
        val authorizeURL = (Api.BASE_URL_OAUTH + "oauthchooseaccount").toHttpUrl().newBuilder()
            .addQueryParameter("client_id", Api.CLIENT_ID)
            .addQueryParameter("response_type", Api.CODE)
            .addQueryParameter("redirect_uri", Api.REDIRECT_URI)
            .addQueryParameter("scope", Api.API_SCOPE)
            .build()

        val i = Intent(Intent.ACTION_VIEW)
        Timber.e("the url is : $authorizeURL")
        i.data = Uri.parse(authorizeURL.toString())
        i.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(i)
        finish()
    }
}