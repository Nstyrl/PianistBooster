package com.example.bartomiej.pianistbooster;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.bartomiej.pianistbooster.RoomNoteResults.AppDatabase;
import com.example.bartomiej.pianistbooster.RoomNoteResults.Note;
import com.example.bartomiej.pianistbooster.statistics.HallOfFameActivity;
import com.example.bartomiej.pianistbooster.tests.TestingActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) !=
                PackageManager.PERMISSION_GRANTED) {
            String premissions[] = {Manifest.permission.RECORD_AUDIO};
            requestPermissions(premissions, 1);
        }
    }

    public void clickPlayButton(View view) {

        String permission = android.Manifest.permission.RECORD_AUDIO;
        int res = MainActivity.this.checkCallingOrSelfPermission(permission);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {
            Intent myIntent = new Intent(MainActivity.this, ModeChooseActivity.class);
            //myIntent.putExtra("key", value); //Optional parameters
            MainActivity.this.startActivity(myIntent);
        }
    }

    public void clickStatsButton(View view) {
        Intent myIntent = new Intent(MainActivity.this, HallOfFameActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    public void clickAboutButton(View view) {


    }

    public void clickTestsButton(View view) {
        Intent myIntent = new Intent(MainActivity.this, TestingActivity.class);
        MainActivity.this.startActivity(myIntent);
    }
}
