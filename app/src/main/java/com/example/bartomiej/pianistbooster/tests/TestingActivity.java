package com.example.bartomiej.pianistbooster.tests;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bartomiej.pianistbooster.R;
import com.example.bartomiej.pianistbooster.sound_analysis.WaveAnalysis;

public class TestingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        WaveAnalysis WA = new WaveAnalysis(this);


    }
}
