package com.example.slack_task.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.slack_task.Constants;
import com.example.slack_task.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class webActivity extends AppCompatActivity {

    @BindView(R.id.webview)
    WebView webview;

    private ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);

        sharedPreferences=getSharedPreferences(Constants.NAME, Context.MODE_PRIVATE);


        Uri builtUri = Uri.parse(Constants.AUTH_URL)
                .buildUpon()
                .appendQueryParameter(Constants.CLIENT_ID_KEY, Constants.CLIENT_ID)
                .appendQueryParameter(Constants.SCOPE_KEY, Constants.SCOPE)
                .appendQueryParameter(Constants.REDIRECT_URI_KEY, Constants.REDIRECT_URI)
                .build();


        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        progressDialog = new ProgressDialog(webActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        webview.setWebChromeClient(new WebChromeClient());


        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                Log.d("WebView", "your current url when webpage loading.." + url);


            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("WebView", "your current url when webpage loading.. finish" + url);
                super.onPageFinished(view, url);

                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                Log.e("onPageFinished_url--- ", url);
                Uri uri = Uri.parse(url);
                String server = uri.getAuthority();
                String path = uri.getPath();
                String protocol = uri.getScheme();
                Set<String> args = uri.getQueryParameterNames();

                if (uri.getQueryParameter("code") != null) {


                    Log.e("parameters are", String.valueOf(uri.getQueryParameter("code")));
/*
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString(Constants.CODE,String.valueOf(uri.getQueryParameter("code")));
                    editor.apply();*/
/*
                    Intent intent = new Intent(webActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();*/

                    Intent intent = new Intent();
                    intent.putExtra("code", String.valueOf(uri.getQueryParameter("code")));
                    setResult(RESULT_OK, intent);
                    finish();


                }



            }

            @Override
            public void onLoadResource(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onLoadResource(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                System.out.println("when you click on any interlink on webview that time you got url :-" + url);
                return super.shouldOverrideUrlLoading(view, url);
            }


        });

        try {
            URL url = new URL(builtUri.toString());
            webview.loadUrl(String.valueOf(url));
            Log.e("auth_url_is", String.valueOf(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }



    }
}
