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
            String[] str = getResources().getStringArray(R.array.rape);
            tv.setText(str[0]);
        }
        else if(position==1){
            String[] str = getResources().getStringArray(R.array.sexual_harresment);
            tv.setText(str[0]);
        }
        else if(position==2){
            String[] str = getResources().getStringArray(R.array.dohon);
            tv.setText(str[0]);
        }
        else if(position==3){
            String[] str = getResources().getStringArray(R.array.opohoron);
            tv.setText(str[0]);
        }
        else if(position==4){
            String[] str = getResources().getStringArray(R.array.joutuk);
            tv.setText(str[0]);
        }
        else if(position==5){
            String[] str = getResources().getStringArray(R.array.orthodondo);
            tv.setText(str[0]);
        }
        else if(position==6){
            String[] str = getResources().getStringArray(R.array.muktipon);
            tv.setText(str[0]);
        }
        else if(position==7){
            String[] str = getResources().getStringArray(R.array.shompotti);
            tv.setText(str[0]);
        }
        else if(position==8){
            String[] str = getResources().getStringArray(R.array.suicide);
            tv.setText(str[0]);
        }
        else if(position==9){
            String[] str = getResources().getStringArray(R.array.eveteasing);
            tv.setText(str[0]);
        }
        else if(position==10){
            String[] str = getResources().getStringArray(R.array.mitthamamla);
            tv.setText(str[0]);
        }
        else if(position==11){
            String[] str = getResources().getStringArray(R.array.divorce);
            tv.setText(str[0]);
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
        myIntent = new Intent(getApplicationContext(), Legal_service.class);
        startActivityForResult(myIntent, 0);
        finish();
        return true;
    }
}
