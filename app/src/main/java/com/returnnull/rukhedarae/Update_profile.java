package com.returnnull.rukhedarae;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Update_profile extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String Password = "passKey";
    public static final String Contact1 = "con1Key";
    public static final String Contact2 = "con2Key";
    public static final String Contact3 = "con3Key";

    String nm="",phn="";

    EditText et_name,et_user_contact,et_pass,et_cont1,et_cont2,et_cont3;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        setActionBar();

        et_name=(EditText)findViewById(R.id.input_name);
        et_user_contact=(EditText)findViewById(R.id.input_user_contact);
        et_pass=(EditText)findViewById(R.id.input_password);
        et_cont1=(EditText)findViewById(R.id.input_contact1);
        et_cont2=(EditText)findViewById(R.id.input_contact2);
        et_cont3=(EditText)findViewById(R.id.input_contact3);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        et_name.setText(sharedpreferences.getString(Name,null));
        et_user_contact.setText(sharedpreferences.getString(Phone,null));
        et_pass.setText(sharedpreferences.getString(Password,null));
        et_cont1.setText(sharedpreferences.getString(Contact1,null));
        et_cont2.setText(sharedpreferences.getString(Contact2,null));
        et_cont3.setText(sharedpreferences.getString(Contact3,null));
    }

    public void onClick_Save(View view) {
        String n = et_name.getText().toString();
        String ph = et_user_contact.getText().toString();
        String pass = et_pass.getText().toString();
        String cont1 = et_cont1.getText().toString();
        String cont2 = et_cont2.getText().toString();
        String cont3 = et_cont3.getText().toString();

        //SharedPreferences.Editor editor = sharedpreferences.edit();
        sharedpreferences.edit().putString(Name, n).apply();
        sharedpreferences.edit().putString(Phone, ph).apply();
        sharedpreferences.edit().putString(Password, pass).apply();
        sharedpreferences.edit().putString(Contact1, cont1).apply();
        sharedpreferences.edit().putString(Contact2, cont2).apply();
        sharedpreferences.edit().putString(Contact3, cont3).apply();


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String lanSettings = sharedpreferences.getString(Name, null) + "/nDone";
        Toast.makeText(this, lanSettings, Toast.LENGTH_LONG).show();
        finish();

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
