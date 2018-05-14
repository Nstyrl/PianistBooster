package com.example.bartomiej.pianistbooster.sound_analysis;

import android.app.Activity;
import android.util.Log;

import com.example.bartomiej.pianistbooster.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WaveAnalysis {

    private Activity activity;
    public void LoadText(int resourceId) {
        // The InputStream opens the resourceId and sends it to the buffer
        InputStream is = activity.getResources().openRawResource(resourceId);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String readLine = null;

        List<Short> frequencies = new ArrayList<>();

        try {
            int c;

            while ((c = br.read()) != -1) {

            }

            // Close the InputStream and BufferedReader
            is.close();
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WaveAnalysis(Activity activity) {
        this.activity = activity;
        LoadText(R.raw.piano_sound_001);
    }

}
