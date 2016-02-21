package com.returnnull.rukhedarae;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;



public class DangerousPlaces extends AppCompatActivity {

    TextView tv;
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangerous_places);

        tv = (TextView)findViewById(R.id.textViewLocation);
        bt = (Button)findViewById(R.id.buttonReport);
        tv.setVisibility(View.GONE);
        bt.setVisibility(View.GONE);

        getSupportActionBar().setTitle("অনুগ্রহ পূর্বক অপেক্ষা করুন");

        WebView webView = (WebView)findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("http://returnnull.austcse.org/Danger/DangerousPlaces.php");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                getSupportActionBar().setTitle("বিপদজনক এলাকা সমূহ");
                tv.setVisibility(View.VISIBLE);
                bt.setVisibility(View.VISIBLE);
            }
        });
    }




    public void onClickReport(View view){
        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(DangerousPlaces.this, bt);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(DangerousPlaces.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        popup.show();//showing popup menu
    }


}
