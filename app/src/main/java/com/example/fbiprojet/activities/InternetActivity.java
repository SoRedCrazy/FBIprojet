package com.example.fbiprojet.activities;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fbiprojet.adapters.InternetWebViewClient;
import com.example.fbiprojet.R;

public class InternetActivity extends AppCompatActivity {
    WebView wb;

    /**
     * Lance la webview sur le sites du FBI
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);
        wb = (WebView) findViewById(R.id.webInternet);
        wb.setWebViewClient(new InternetWebViewClient());
        wb.loadUrl("https://www.fbi.gov/services");
        wb.getSettings().setJavaScriptEnabled(true);
    }

    /**
     * sert au retour de la pile des lien cliquer
     */
    @Override
    public void onBackPressed() {
        if (wb.canGoBack()) {
            wb.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
