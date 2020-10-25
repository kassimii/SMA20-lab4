package com.example.services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class WebsearchActivity extends AppCompatActivity {

    private WebView webView;
    private Button bLoadBackground, bLoadForeground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_websearch);
        initialiseWebView();

        bLoadBackground = (Button)findViewById(R.id.bLoadBackground);
        bLoadForeground = (Button)findViewById(R.id.bLoadForeground);
    }

    public void initialiseWebView(){
        webView = (WebView)findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyCustomWebViewClient());

    }

    public void loadImage(View view){
        ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData abc = clipboard.getPrimaryClip();
        ClipData.Item item = abc.getItemAt(0);
        String url = item.getText().toString();

        if(!url.contains("https://goo.gl/images"))
            Toast.makeText(this, "URL not valid. Try another", Toast.LENGTH_SHORT).show();
        else{
            if(view.getId() == R.id.bLoadBackground){
                Intent intent = new Intent(this,ImageIntentService.class);
                intent.putExtra(EXTRA_URL,url);
                startService(intent);
            }
            else if(view.getId() == R.id.bLoadForeground){
                Intent startIntent =  new Intent(this, ForegroundImageService.class);
                startIntent.setAction(ForegroundImageService.STARTFOREGROUND_ACTION);
                startIntent.putExtra(EXTRA_URL,url);
                startService(startIntent);
            }
        }
        }
}