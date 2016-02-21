package com.returnnull.rukhedarae;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class Track_me extends Activity {

    Switch switchButton, switchButton2;
    TextView textView, textView2;
    String TswitchOn = "Tracking is ON";
    String TswitchOff = "Tracking is OFF";
    String AswitchOn = "Alert is ON";
    String AswitchOff = "Alert is OFF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_me);
        this.setFinishOnTouchOutside(false);

        switchButton = (Switch) findViewById(R.id.switchButton);
        textView = (TextView) findViewById(R.id.textView);

        switchButton.setChecked(false);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    textView.setText(TswitchOn);
                } else {
                    textView.setText(TswitchOff);
                }
            }
        });

        if (switchButton.isChecked()) {
            textView.setText(TswitchOn);
        } else {
            textView.setText(TswitchOff);
        }

        // for second switch button
        switchButton2 = (Switch) findViewById(R.id.switchButton2);
        textView2 = (TextView) findViewById(R.id.textView2);

        switchButton2.setChecked(false);
        switchButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    textView2.setText(AswitchOn);
                } else {
                    textView2.setText(AswitchOff);
                }
            }
        });

        if (switchButton2.isChecked()) {
            textView2.setText(AswitchOn);
        } else {
            textView2.setText(AswitchOff);
        }

    }
    public void onClick_Close(View view){
        finish();
    }

}
