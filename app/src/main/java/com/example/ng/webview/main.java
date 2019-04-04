package com.example.ng.webview;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;
import android.os.Handler;

public class main extends AppCompatActivity {

    WebView myWebView;
    private static final String TAG = main.class.getSimpleName();

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            hideSystemUI();

            try {
                //Allow access to sites hosted by Appventure
                if (Uri.parse(url).getHost().equals("appventure.nushigh.edu.sg")) {
                    Log.d(TAG, "return false");
                    return false;
                }
            } catch (Exception e) {
                //Prevent Exceptions i.e. clicking email addresses
                e.printStackTrace();
                Log.e("IO", "IO" + e);
                return true;
            }
            //Show Dialog and prevent user from navigating outside of Appventure
            AlertDialog.Builder builder1 = new AlertDialog.Builder(main.this);
            builder1.setMessage(R.string.Dialog);
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
            return true;
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialising Web View
        myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("http://appventure.nushigh.edu.sg");
        myWebView.setWebViewClient(new MyWebViewClient());
        myWebView.setWebChromeClient(new WebChromeClient());
        //Disable long clicking --> WebSearch
        myWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        myWebView.setLongClickable(false);
        //Disable action bar and system UI
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        hideSystemUI();
        //Enable JavaScript
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);


    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUI();
    }

    public void onBackPressed() {
        if (myWebView.canGoBack()) {
            myWebView.goBack();
            return;
        }
        // Otherwise defer to system default behavior.
        super.onBackPressed();
    }

    private void hideSystemUI() {
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }
}


