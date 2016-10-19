package com.gank.io.gankdetail;

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
import com.gank.io.constant.Contants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GankDetailActivity extends AppCompatActivity {

    @BindView(R.id.web_gank_detail)
    WebView webView;
    @BindView(R.id.progressbar_load)
    ProgressBar progressBarLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank_detail);
        ButterKnife.bind(this);
        initWebView();
    }

    private void initWebView() {
        String url = getIntent().getStringExtra(Contants.GANK_URL);
        webView.loadUrl(url);
        WebViewClient client = new WebViewClient() {
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
                if (progressBarLoad.getVisibility() == View.VISIBLE) {
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
