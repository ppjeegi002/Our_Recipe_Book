package com.recipe_book.our_recipebook;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebSettings;
import  android.webkit.WebViewClient;

public class MainActivity5 extends AppCompatActivity {
private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        webView = findViewById(R.id.Wv);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Fetching...");
        pd.show();
        webView.setWebViewClient(new callBack());

        webView.loadUrl("https://www.allrecipes.com/");

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                pd.dismiss();

            }
        });

    }

    private class callBack extends WebViewClient {
        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return false;
        }
    }
}