package cn.isif.reviewandroid.web;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import cn.isif.reviewandroid.R;

/**
 * 在写这个demo的时候遇到一个问题：
 *  运行时报错: ERR_ACCESS_DENIED
 *  这个问题首先需要在manifest文件中加入`android:usesCleartextTraffic="true"`，表示对http的支持
 *  然后需要重新卸载已经运行的app
 *
 */
public class WebActivity extends AppCompatActivity {
    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        mWebView = findViewById(R.id.web);
        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebSettings.setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
                //处理不被支持的证书
                final AlertDialog.Builder builder = new AlertDialog.Builder(WebActivity.this);
                String message = "SSL Certificate error.";
                switch (error.getPrimaryError()) {
                    case SslError.SSL_UNTRUSTED:
                        message = "The certificate authority is not trusted.";
                        break;
                    case SslError.SSL_EXPIRED:
                        message = "The certificate has expired.";
                        break;
                    case SslError.SSL_IDMISMATCH:
                        message = "The certificate Hostname mismatch.";
                        break;
                    case SslError.SSL_NOTYETVALID:
                        message = "The certificate is not yet valid.";
                        break;
                }
                message += " Do you want to continue anyway?";

                builder.setTitle("SSL Certificate Error");
                builder.setMessage(message);
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
        });
        mWebView.loadUrl("https://www.dytt8.net/");
    }

    public static void startActivtiy(Activity context) {
        context.startActivity(new Intent(context, WebActivity.class));
    }
}
