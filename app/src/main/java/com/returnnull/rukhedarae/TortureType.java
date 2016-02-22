package com.returnnull.rukhedarae;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TortureType extends AppCompatActivity {

    TextView tv ;
    Button bta;
    Button btb;
    Button btc;
    Button btd;
    String[] Question1;
    String[] Question2;
    String[] Question3;
    String[] Question4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_torture_type);
        setActionBar();
        initializeViews();
        initializeQuestions();
        firstQuestion();
    }

    public void initializeViews(){
        tv = (TextView)findViewById(R.id.textviewQuestion);
        bta = (Button)findViewById(R.id.buttonA);
        btb = (Button)findViewById(R.id.buttonB);
        btc = (Button)findViewById(R.id.buttonC);
        btd = (Button)findViewById(R.id.buttonD);
        btd.setVisibility(View.GONE);
    }

    public void initializeQuestions(){
        Question1 = getResources().getStringArray(R.array.base_question);
        Question2 = getResources().getStringArray(R.array.questionPhysical);
        Question3 = getResources().getStringArray(R.array.questionEconomic);
        Question4 = getResources().getStringArray(R.array.questionOthers);
    }
    public void firstQuestion(){
        btd.setVisibility(View.GONE);
        tv.setText(Question1[0]);
        bta.setText(Question1[1]);
        btb.setText(Question1[2]);
        btc.setText(Question1[3]);
    }

    public void secondQuestion(){
        btd.setVisibility(View.VISIBLE);
        tv.setText(Question2[0]);
        bta.setText(Question2[1]);
        btb.setText(Question2[2]);
        btc.setText(Question2[3]);
        btd.setText(Question2[4]);
    }
    public void thirdQuestion(){
        btd.setVisibility(View.VISIBLE);
        tv.setText(Question3[0]);
        bta.setText(Question3[1]);
        btb.setText(Question3[2]);
        btc.setText(Question3[3]);
        btd.setText(Question3[4]);
    }
    public void fourthQuestion(){
        btd.setVisibility(View.VISIBLE);
        tv.setText(Question4[0]);
        bta.setText(Question4[1]);
        btb.setText(Question4[2]);
        btc.setText(Question4[3]);
        btd.setText(Question4[4]);
    }

    public void onClickA(View view){
        if(bta.getText().toString().equalsIgnoreCase("শারীরিক")){
            secondQuestion();
        }
        else if(bta.getText().toString().equalsIgnoreCase("ধর্ষণ")) {
            Toast.makeText(this,bta.getText().toString(),Toast.LENGTH_SHORT).show();
        }
        else if(bta.getText().toString().equalsIgnoreCase("যৌতুক")) {
            Toast.makeText(this,bta.getText().toString(),Toast.LENGTH_SHORT).show();
        }
        else if(bta.getText().toString().equalsIgnoreCase("আত্মহত্যা প্ররোচনা")) {
            Toast.makeText(this,bta.getText().toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void onClickB(View view){
        if(btb.getText().toString().equalsIgnoreCase("আর্থিক")) {
            thirdQuestion();
        }
        else if(btb.getText().toString().equalsIgnoreCase("যৌন নিপীড়ন")) {
            Toast.makeText(this,btb.getText().toString(),Toast.LENGTH_SHORT).show();
        }
        else if(btb.getText().toString().equalsIgnoreCase("মুক্তিপণ")) {
            Toast.makeText(this,btb.getText().toString(),Toast.LENGTH_SHORT).show();
        }
        else if(btb.getText().toString().equalsIgnoreCase("ইভটিজিং")) {
            Toast.makeText(this,btb.getText().toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void onClickC(View view){
        if(btc.getText().toString().equalsIgnoreCase("মানসিক / অন্যান্য")) {
            fourthQuestion();
        }
        else if(btc.getText().toString().equalsIgnoreCase("দহন জনিত")) {
            Toast.makeText(this,btc.getText().toString(),Toast.LENGTH_SHORT).show();
        }
        else if(btc.getText().toString().equalsIgnoreCase("সম্পত্তি অধিকার")) {
            Toast.makeText(this,btc.getText().toString(),Toast.LENGTH_SHORT).show();
        }
        else if(btc.getText().toString().equalsIgnoreCase("মিথ্যা মামলা")) {
            Toast.makeText(this,btc.getText().toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void onClickD(View view){
        if(btd.getText().toString().equalsIgnoreCase("অপহরণ")) {
            Toast.makeText(this,btd.getText().toString(),Toast.LENGTH_SHORT).show();
        }
        else if(btd.getText().toString().equalsIgnoreCase("অর্থদণ্ড ও ক্ষতিপূরণ")) {
            Toast.makeText(this,btd.getText().toString(),Toast.LENGTH_SHORT).show();
        }
        else if(btd.getText().toString().equalsIgnoreCase("ডিভোর্স")) {
            Toast.makeText(this,btd.getText().toString(),Toast.LENGTH_SHORT).show();
        }
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

        if(bta.getText().toString().equalsIgnoreCase("ধর্ষণ") ||bta.getText().toString().equalsIgnoreCase("যৌতুক")
                || bta.getText().toString().equalsIgnoreCase("আত্মহত্যা প্ররোচনা")) {
            firstQuestion();
        }
        else{
            Intent myIntent;
            myIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivityForResult(myIntent, 0);
            finish();
        }
        return true;
    }
}
