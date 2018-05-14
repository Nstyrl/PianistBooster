package com.example.bartomiej.pianistbooster.statistics;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.bartomiej.pianistbooster.R;
import com.example.bartomiej.pianistbooster.RoomNoteResults.AppDatabase;
import com.example.bartomiej.pianistbooster.RoomNoteResults.LearnNotesModeResult;

import java.util.List;

public class HallOfFameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_of_fame);

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "hall_of_fame").build();

        final TextView hallOfFame = (TextView) findViewById(R.id.results);

        final Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                hallOfFame.setText((String)msg.obj);
            }
        };



        (new Thread (new Runnable() {
            @Override
            public void run() {
                List<LearnNotesModeResult> results = db.LNMDao().getAll();



                String text = "Hall Of Fame\n\n";

                if(results.isEmpty()) {
                    text += "No results";
                } else {
                    for(LearnNotesModeResult result: results) {
                        text += Double.toString(result.result)+ "\n";
                    }
                }

                Message msg = new Message();
                msg.obj = text;
                handler.sendMessage(msg);
            }
        })).start();
    }

    public void clickBackButton(View view) {
        finish();
    }

}
