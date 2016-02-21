package com.returnnull.rukhedarae;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class Legal_info_view extends AppCompatActivity {

    String name="";
    TextView tv;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal_info_view);

        tv=(TextView)findViewById(R.id.view_description);

        Intent in = getIntent();
        name = in.getStringExtra(("name"));//gets name from intent
        position=in.getIntExtra("loc",0);
        setActionBar();

        if(position==0){
            tv.setText(R.string.rape);
        }
        if(position==1){
            tv.setText(R.string.sexual_harresment);
        }
    }

    public void setActionBar(){
        getSupportActionBar().setTitle(name);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }catch (NullPointerException ex){
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent;
        myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        finish();
        return true;
    }
}
