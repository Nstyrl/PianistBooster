package com.example.bartomiej.pianistbooster.old;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bartomiej.pianistbooster.R;
import com.example.bartomiej.pianistbooster.common.PianoFrequencies;
import com.example.bartomiej.pianistbooster.sound_analysis.ListenerThread;
import com.example.bartomiej.pianistbooster.game_modes.staff_mode.old.StaffManager;

import static android.view.View.VISIBLE;
import static com.example.bartomiej.pianistbooster.common.Constants.thread_ids.LISTENER_THREAD;

public class NotesShowActivity extends AppCompatActivity {

    Thread listenerThread;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_game);


        final StaffManager staffManager = new StaffManager((ImageView) findViewById(R.id.tremble), (ImageView) findViewById(R.id.note));


        listenerThread.start();
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == LISTENER_THREAD) {
                    TextView frequency = (TextView) findViewById(R.id.frequency);
                    String s = (String)msg.obj;
                    frequency.setText((String) msg.obj);

                    Double f = Double.valueOf((String) msg.obj);

                    if(f > 0.1) {
                        ImageView note = (ImageView) findViewById(R.id.note);
                        float positions[] = staffManager.spawnOn(PianoFrequencies.frequencyToNote(f));

                        note.setX(positions[0]);
                        note.setY(positions[1]);
                        if(note.getVisibility() != VISIBLE) {
                            note.setVisibility(VISIBLE);
                        }
                    }

                } else {
                    super.handleMessage(msg);
                }
            }
        };

        listenerThread = new Thread(new ListenerThread(NotesShowActivity.this, mHandler));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(listenerThread != null && listenerThread.isAlive()) listenerThread.interrupt();
    }
}
