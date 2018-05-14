package com.example.bartomiej.pianistbooster.game_modes.learn_notes_mode;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.bartomiej.pianistbooster.R;
import com.example.bartomiej.pianistbooster.RoomNoteResults.AppDatabase;
import com.example.bartomiej.pianistbooster.RoomNoteResults.LearnNotesModeResult;
import com.example.bartomiej.pianistbooster.RoomNoteResults.Note;

import java.util.List;

public class FinishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        Intent intent = getIntent();

        final String score = intent.getStringExtra("score");

        ((EditText) findViewById(R.id.score)).setText(String.format("Your final score: " + score));

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "hall_of_fame").build();

        (new Thread (new Runnable() {
            @Override
            public void run() {
                db.LNMDao().insertAll(new LearnNotesModeResult(Double.parseDouble(score)));
            }
        })).start();
    }

    public void clickGoBack(View view) {
        finish();
    }
}
