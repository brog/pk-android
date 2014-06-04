package com.prettykitty.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity {

    private final static String TAG = "PrettyKittyMainActivity";

    private Context context;
    private SharedPreferences sharedPref;
    private  WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_PROGRESS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(android.os.Build.VERSION.SDK_INT > 11){
            getActionBar().setIcon(R.drawable.ic_logo);
        }

        context = (Context) this;
        sharedPref = context.getSharedPreferences(
                getString(R.string.global_prefs), Context.MODE_PRIVATE);
        webview = (WebView) findViewById(R.id.webview);

        setupViewView();

        final String savedLocation = getSavedLocation();

        if(null == savedLocation){
            startLocationActivity();
        }else{
            loadWebView(savedLocation);
        }
    }

    private void startLocationActivity(){
        Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);
    }

    private void setupViewView(){
        final Activity activity = this;



        webview.getSettings().setJavaScriptEnabled(true);

        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
                activity.setProgress(progress * 1000);
            }
        });
        webview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadWebView(final String savedLocation){
        final String bookerCode = PrettyData.SPA_LOCATIONS.get(savedLocation);
        webview.loadUrl("https://app.secure-booker.com/mobile/"+bookerCode+"/Home");
        Log.d(TAG, webview.getUrl());
    }

    private String getSavedLocation(){
        return sharedPref.getString(getString(R.string.key_location), null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.location, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_location) {
            startLocationActivity();
            return true;
        }else if(id == R.id.action_about){
            String url = "http://prettykittywax.com/";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final String savedLocation = getSavedLocation();
        loadWebView(savedLocation);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
