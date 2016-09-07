package com.gank.io.today;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.gank.io.R;

public class GankDetailActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBarLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = getIntent().getStringExtra("GANK_URL");
        setContentView(R.layout.activity_gank_detail);
        webView = (WebView) findViewById(R.id.web_gank_detail);
        progressBarLoad = (ProgressBar) findViewById(R.id.progressbar_load);
        webView.loadUrl(url);
        WebViewClient client = new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBarLoad.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBarLoad.setVisibility(View.GONE);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                if (progressBarLoad.getVisibility() == View.VISIBLE){
                    progressBarLoad.setVisibility(View.GONE);
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                progressBarLoad.setVisibility(View.GONE);
            }
        };
        webView.setWebViewClient(client);

    }
}
