package com.returnnull.rukhedarae;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Registration extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String Password = "passKey";
    public static final String Contact1 = "con1Key";
    public static final String Contact2 = "con2Key";
    public static final String Contact3 = "con3Key";

    EditText et_name,et_user_contact,et_pass,et_cont1,et_cont2,et_cont3;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setActionBar();

        et_name=(EditText)findViewById(R.id.input_name);
        et_user_contact=(EditText)findViewById(R.id.input_user_contact);
        et_pass=(EditText)findViewById(R.id.input_password);
        et_cont1=(EditText)findViewById(R.id.input_contact1);
        et_cont2=(EditText)findViewById(R.id.input_contact2);
        et_cont3=(EditText)findViewById(R.id.input_contact3);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    }

    public void onClick_Save(View view){
        String n  = et_name.getText().toString();
        String ph  = et_user_contact.getText().toString();
        String pass = et_pass.getText().toString();
        String cont1= et_cont1.getText().toString();
        String cont2= et_cont2.getText().toString();
        String cont3= et_cont3.getText().toString();

        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(Name, n);
        editor.putString(Phone, ph);
        editor.putString(Password, pass);
        editor.putString(Contact1, cont1);
        editor.putString(Contact2, cont2);
        editor.putString(Contact3, cont3);
        editor.commit();
        //sharedpreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String lanSettings = sharedpreferences.getString(Name, null);
        Toast.makeText(this, lanSettings, Toast.LENGTH_LONG).show();
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






    //Configuration
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.finishAffinity();
        }
        else {
            Registration.this.finish();
        }
    }

}
