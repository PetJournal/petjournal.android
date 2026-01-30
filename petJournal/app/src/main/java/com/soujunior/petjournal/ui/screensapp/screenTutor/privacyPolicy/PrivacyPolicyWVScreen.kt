package com.soujunior.petjournal.ui.screensapp.screenTutor.privacyPolicy

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun PrivacyPolicyWVScreen(url: String = "") {
    AndroidView(factory = { context ->
        WebView(context).apply {
            layoutParams =
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )

            webViewClient = WebViewClient()
            settings.javaScriptEnabled = false
            settings.domStorageEnabled = true

            loadUrl(url)
        }
    }, update = {
        it.loadUrl(url)
    })
}
