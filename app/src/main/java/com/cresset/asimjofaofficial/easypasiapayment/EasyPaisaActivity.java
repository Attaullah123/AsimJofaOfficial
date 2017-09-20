package com.cresset.asimjofaofficial.easypasiapayment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cresset.asimjofaofficial.R;

import java.util.Collection;
import java.util.Map;


public class EasyPaisaActivity  extends AppCompatActivity{
    private WebView webView;
    private ProgressBar progressBar;
    private LinearLayout layoutProgress;
    String orderId,orderAmount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.easypaisa_payment);

        orderId = getIntent().getStringExtra("orderId");
        orderAmount = getIntent().getStringExtra("orderAmount");

        //button = (Button) findViewById(R.id.button);
        //instantiate the webview
        //this.webView = new WebView(this);
        webView = (WebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        layoutProgress = (LinearLayout) findViewById(R.id.layoutProgress);
        webView.setVisibility(View.GONE);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setDisplayZoomControls(false);
        //settings.setDefaultTextEncodingName("utf-8");

        //setContentView(this.webView);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                webView.setVisibility(View.VISIBLE);
                layoutProgress.setVisibility(View.GONE);
                progressBar.setIndeterminate(false);
                super.onPageFinished(view, url);

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                layoutProgress.setVisibility(View.VISIBLE);
                progressBar.setIndeterminate(true);
                super.onPageStarted(view, url, favicon);
            }
        });
        if(isOnline()) {
            webView.loadUrl("https://www.asimjofa.com/CustomeCode/EsseyPassMobileCompleted/" + orderId);
        } else {
            String summary = "<center><html><body><font color='black'>No Internet Connection</font></body></center></html>";
            webView.loadData(summary, "text/html", null);
            toast("Please First Connect with Internet ");
        }
    }
    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        // Check if the key event was the Back button and if there's history
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
//            webView.goBack();
//            return true;
//
//        }
//        // If it wasn't the Back key or there's no web page history, bubble up to the default
//        // system behavior (probably exit the activity)
//        return super.onKeyDown(keyCode, event);
//    }
}


