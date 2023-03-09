package com.example.fbiprojet;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class InternetActivity extends AppCompatActivity {
    WebView wb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);
        wb = (WebView) findViewById(R.id.webInternet);
        wb.setWebViewClient(new InternetWebViewClient());
        wb.loadUrl("https://www.fbi.gov/services");
        wb.getSettings().setJavaScriptEnabled(true);
    }
    @Override
    public void onBackPressed() {
        if (wb.canGoBack()) {
            wb.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
