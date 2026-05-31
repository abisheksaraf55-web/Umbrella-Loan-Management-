package com.loanmanager.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {
    private WebView webView;

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fullscreen immersive
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        // Enable hardware acceleration
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
        );

        webView = new WebView(this);
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        setContentView(webView);

        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setDatabaseEnabled(true);
        ws.setAllowFileAccessFromFileURLs(true);
        ws.setAllowUniversalAccessFromFileURLs(true);
        ws.setLoadWithOverviewMode(true);
        ws.setUseWideViewPort(true);
        ws.setBuiltInZoomControls(false);
        ws.setDisplayZoomControls(false);
        ws.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        ws.setTextZoom(100);
        ws.setMediaPlaybackRequiresUserGesture(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ws.setSafeBrowsingEnabled(false);
        }

        webView.addJavascriptInterface(new AndroidBridge(), "AndroidBridge");
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                if (url.startsWith("file://") || url.startsWith("javascript:")) return false;
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                } catch (Exception ignored) {}
                return true;
            }
        });

        webView.loadUrl("file:///android_asset/index.html");
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) webView.goBack();
        else super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null) webView.onResume();
    }

    public class AndroidBridge {

        @JavascriptInterface
        public void saveFile(String filename, String content) {
            try {
                File dir;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    dir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
                } else {
                    dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                }
                if (dir != null && !dir.exists()) dir.mkdirs();
                File file = new File(dir, filename);
                FileOutputStream fos = new FileOutputStream(file);
                OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8");
                writer.write(content);
                writer.close();
                final String path = file.getAbsolutePath();
                runOnUiThread(() -> Toast.makeText(
                    MainActivity.this,
                    "✓ Saved to Downloads: " + filename,
                    Toast.LENGTH_LONG
                ).show());
            } catch (IOException e) {
                final String err = e.getMessage();
                runOnUiThread(() -> Toast.makeText(
                    MainActivity.this,
                    "Save failed: " + err,
                    Toast.LENGTH_SHORT
                ).show());
            }
        }

        @JavascriptInterface
        public void showToast(String message) {
            runOnUiThread(() -> Toast.makeText(
                MainActivity.this, message, Toast.LENGTH_SHORT
            ).show());
        }

        @JavascriptInterface
        public String getAppVersion() { return "1.0.0"; }

        @JavascriptInterface
        public String getDeviceInfo() {
            return Build.MANUFACTURER + " " + Build.MODEL + " (Android " + Build.VERSION.RELEASE + ")";
        }

        @JavascriptInterface
        public String getCurrentDate() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        }
    }
}
