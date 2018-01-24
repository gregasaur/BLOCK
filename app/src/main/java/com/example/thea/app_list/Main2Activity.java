package com.example.thea.app_list;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import static android.widget.Toast.LENGTH_LONG;

public class Main2Activity extends AppCompatActivity {

    private Button btntest;
    private TextView timer;
    public SeekBar hseek, mseek;
    public int hours, mins, duration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btntest = (Button) findViewById(R.id.startbtn);
        hseek = (SeekBar)findViewById(R.id.HourSeek);
        mseek = (SeekBar)findViewById(R.id.MinSeek);
        hseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int shours;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                shours = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                hours = shours;
            }
        });

        mseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int smins;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                smins = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mins = smins;
            }
        });
        //button start
        btntest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hours = hours*3600000;
                mins = mins*60000;
                duration = hours+mins;
                Toast.makeText(Main2Activity.this,"duration: "+duration,LENGTH_LONG).show();
                startService(duration);
            }
        });
    }

    //saving data and passing intent to service
    public void startService(int duration)
    {
        int d = duration;
        SharedPreferences sharedPreferences = getSharedPreferences("Timer", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("duration", d);
        editor.apply();
        Intent intent = new Intent(this,MyService.class);
        startService(intent);
    }


}