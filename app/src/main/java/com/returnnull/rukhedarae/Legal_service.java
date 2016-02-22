package com.returnnull.rukhedarae;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Legal_service extends AppCompatActivity {
    String[] legal_list = {"ধর্ষণ, ধর্ষণজনিত কারণে মৃত্যু, ইত্যাদির শাস্তি",
            "যৌন পীড়ন, ইত্যাদির দণ্ড ",
            "দহনকারী, ইত্যাদি পদার্থ দ্বারা সংঘটিত অপরাধের শাস্তি",
            "নারী অপহরণের শাস্তি",
            "যৌতুকের জন্য মৃত্যু ঘটানো, ইত্যাদির শাস্তি",
            "অর্থদণ্ড বা ক্ষতিপূরণ আদায়ের পদ্ধতি",
            "মুক্তিপণ আদায়ের শাস্তি",
            "ভবিষ্যত্ সম্পত্তি হইতে অর্থদণ্ড আদায়",
            "নারীর আত্মহত্যায় প্ররোচনা, ইত্যাদির শাস্তি",
            "ইভ টিজিং এবং এর শাস্তি",
            "মিথ্যা মামলা, অভিযোগ দায়ের ইত্যাদির শাস্তি",
            "ডিভোর্স জনিত আইন"};

    String selected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal_service);
        setActionBar();

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.listview_activity, legal_list);

        final ListView listView = (ListView) findViewById(R.id.legal_service_list);
        listView.setAdapter(adapter);

        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                selected = legal_list[position];
                final int pos= position;



                Intent i = new Intent(getApplicationContext(), Legal_info_view.class);
                i.putExtra("name", selected);
                i.putExtra("loc",pos);
                startActivity (i);
                finish();

            }
        });
    }

    public void setActionBar(){
        getSupportActionBar().setTitle("    রুখে দাঁড়াই");
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
