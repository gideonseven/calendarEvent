package com.gst.synccalender.features.calendar.auth

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gst.synccalender.R
import com.gst.synccalender.databinding.FragmentAuthBinding
import com.gst.synccalender.databinding.ViewStateErrorBinding
import com.gst.synccalender.features.calendar.auth.AuthContract.AuthEvent
import com.gst.synccalender.features.calendar.auth.AuthContract.AuthEffect.NavigateToCalendarFragment
import com.gst.synccalender.utils.AppFragment
import com.gst.synccalender.utils.network.Api
import com.gst.synccalender.utils.network.State
import com.gst.synccalender.utils.network.handleResponseState
import com.gst.synccalender.utils.network.observe
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import timber.log.Timber


/**
 * Created by gideon on 8/30/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@AndroidEntryPoint
class AuthFragment : AppFragment<FragmentAuthBinding>(R.layout.fragment_auth) {

    private val viewModel: AuthViewModel by viewModels()

    private val authorizeUrl: HttpUrl? =
        "https://accounts.google.com/o/oauth2/v2/auth".toHttpUrlOrNull()
            ?.newBuilder() //
            ?.addQueryParameter("client_id", Api.CLIENT_ID)
            ?.addQueryParameter("scope", Api.API_SCOPE)
            ?.addQueryParameter("redirect_uri", Api.REDIRECT_URI)
            ?.addQueryParameter("response_type", Api.CODE)
            ?.build()

    override fun getViewState() = binding.viewState

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
            webView.apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.loadWithOverviewMode = true
                settings.userAgentString = "Chrome/91.0.4472.164 Mobile"
                settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                webViewClient = object : WebViewClient() {

                    @Deprecated("Deprecated in Java", ReplaceWith("false"))
                    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                        // true -> we intercepting/block certain url to do what we want
                        // false -> otherwise

                        return if (url?.contains("oauth2redirect", ignoreCase = true) == true) {
                            Timber.e("=== $url")
                            val code = Uri.parse(url).getQueryParameter(
                                "code"
                            ) ?: ""
                            Timber.e("=== code $code")
                            //TODO GO TO MAIN FRAGMENT WHEN CODE
                            viewModel.mCode = code
                            viewModel.setEvent(AuthEvent.SubmitCode)
                            true
                        } else {
                            false
                        }
                    }

                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                    }
                }
            }

            //init loadUrl from bundle
            webView.loadUrl(authorizeUrl.toString())
        }

        viewLifecycleOwner.lifecycleScope.observe(
            lifecycle = lifecycle,
            state = { handleState() },
            effect = { handleEffect() }
        )
    }

    private suspend fun handleState() {
        viewModel.uiState.collect { uiState ->
            nonNullContext.handleResponseState(uiState.responseStateSubmitCode,
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
                            AuthEvent.SubmitCode
                        )
                    }
                }
            )
        }
    }

    private suspend fun handleEffect() {
        viewModel.effect.collect {
            when(it){
                is NavigateToCalendarFragment ->{
                    Timber.e("00000 IM READY TO NAVIGATE ")
                }
                else -> {
                    /** do nothing */
                }
            }
        }
    }
}