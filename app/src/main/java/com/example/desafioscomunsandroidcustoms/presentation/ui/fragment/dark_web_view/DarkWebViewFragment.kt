package com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.dark_web_view

import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.databinding.FragmentDarkWebViewBinding
import com.example.desafioscomunsandroidcustoms.util.viewBinding

// 1 - ADICIONAR DEPENDÊNCIA NO build.gradle
// 2 - CERTIFICAR-SE QUE SEU APP TEM UM "THEME" QUE EXTENDE "DayNigth"
// 3 - USE A FEATURE FORCE_DARK  PARA DEIXAR SEU WEBVIEW DARK TAMBÉM
class DarkWebViewFragment : Fragment(R.layout.fragment_dark_web_view) {

    private val binding by viewBinding(FragmentDarkWebViewBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val mimeType = "text/html"
            val encoding = "utf-8"
            val htmlText = "<h1>My Dark WebView</h1>"
            forceDarkWebView.loadDataWithBaseURL(null, htmlText, mimeType, encoding, null)
            val inDarkMode =
                (requireContext().resources.configuration.uiMode and UI_MODE_NIGHT_MASK) == UI_MODE_NIGHT_YES
            if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK) && inDarkMode) {
                WebSettingsCompat.setForceDark(
                    forceDarkWebView.settings,
                    WebSettingsCompat.FORCE_DARK_ON
                )
            }
        }
    }
}