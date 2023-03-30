package com.example.fbiprojet;

import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class InternetWebViewClient extends WebViewClient {
    /**
     * permet de cree une pile de lien (cliquer) pour les retour
     * @param view
     * @param request
     * @return
     */
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return false;
    }
}
