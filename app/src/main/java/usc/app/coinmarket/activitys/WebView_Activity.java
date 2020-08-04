package usc.app.coinmarket.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import usc.app.coinmarket.R;

public class WebView_Activity extends AppCompatActivity {
    private WebView miWebView;
    ProgressBar progreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_);
        miWebView=findViewById(R.id.idwebView);
        progreso= findViewById(R.id.cargando6);
        miWebView.setWebViewClient(new myWebClient());
        miWebView.getSettings().setJavaScriptEnabled(true);
        miWebView.getSettings().setBuiltInZoomControls(true);
        miWebView.getSettings().setDisplayZoomControls(false);
        miWebView.loadUrl(getIntent().getStringExtra("url"));

    }

    public class myWebClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            progreso.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progreso.setVisibility(View.GONE);
        }
    }



}
