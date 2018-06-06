package com.example.luhongcheng.secondclass;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.luhongcheng.R;

import java.util.HashMap;
import java.util.Map;

public class SecondclassDisplayActvivity extends AppCompatActivity {

    private String newsUrl;
    String str;
    Map<String,String> extraHeaders = new HashMap<String, String>();
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_display);

        newsUrl = getIntent().getStringExtra("news_url");
        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());


        webView.loadUrl(newsUrl);

    }



}
