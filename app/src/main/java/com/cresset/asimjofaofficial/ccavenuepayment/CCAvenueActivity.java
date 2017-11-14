package com.cresset.asimjofaofficial.ccavenuepayment;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cresset.asimjofaofficial.R;

public class CCAvenueActivity extends AppCompatActivity{
    private WebView webView;
    private ProgressBar progressBar;
    private LinearLayout layoutProgress;
    String orderId,orderAmount;
    //private String url = "https://www.asimjofa.com/Plugins/PaymentCCAvenue/MobileRequest/28211";

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
            public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setMessage(R.string.notification_error_ssl_cert_invalid);
                builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.proceed();
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.cancel();
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                layoutProgress.setVisibility(View.VISIBLE);
                progressBar.setIndeterminate(true);
                super.onPageStarted(view, url, favicon);
            }
        });
        if(isOnline()) {
            //webView.loadUrl("https://www.asimjofa.com/CustomeCode/EsseyPassMobileCompleted/" + orderId);
            webView.loadUrl("https://www.asimjofa.com/Plugins/PaymentCCAvenue/MobileRequest/"+ orderId);
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



    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}


