package com.gank.io.gankdetail;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
    @BindView(R.id.progress_bar_gank_detail)
    ProgressBar progressBarGankDetail;
    @BindView(R.id.toolbar_gank_detail)
    Toolbar toolbarGankDetail;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank_detail);
        ButterKnife.bind(this);


        toolbarGankDetail.setTitle("Gank Detail");
        setSupportActionBar(toolbarGankDetail);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbarGankDetail.setNavigationOnClickListener(v -> finish());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, getTheme()));
        }

        initWebView();
    }

    private void initWebView() {
        String url = getIntent().getStringExtra(Contants.GANK_URL);
        webView.loadUrl(url);
        WebViewClient client = new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBarGankDetail.setVisibility(View.GONE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBarGankDetail.setVisibility(View.GONE);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        };
        webView.setWebViewClient(client);

    }
}
