package com.example.bartomiej.pianistbooster.game_modes.freestyle_mode;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bartomiej.pianistbooster.R;
import com.example.bartomiej.pianistbooster.StaffActivity;
import com.example.bartomiej.pianistbooster.common.PianoFrequencies;
import com.example.bartomiej.pianistbooster.sound_analysis.ListenerThread;

import static android.view.View.VISIBLE;
import static com.example.bartomiej.pianistbooster.common.Constants.clef_ids.BASS;
import static com.example.bartomiej.pianistbooster.common.Constants.clef_ids.TREMBLE;
import static com.example.bartomiej.pianistbooster.common.Constants.thread_ids.LISTENER_THREAD;

public class FreestyleActivity extends StaffActivity {

    Thread listenerThread;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == LISTENER_THREAD) {
                    TextView frequency = (TextView) findViewById(R.id.frequency);
                    String s = (String)msg.obj;
                    frequency.setText((String) msg.obj);

                    Double f = Double.valueOf((String) msg.obj);

                    if(f > 0.1) {
                        ImageView note = (ImageView) findViewById(R.id.note);
                        int readNote = PianoFrequencies.frequencyToNote(f);

                        hideAll();
                        if(readNote < 44) {
                            showNote(readNote, PianoFrequencies.getSemitones(readNote), BASS);
                        }
                        if(readNote > 35) {
                            showNote(readNote, PianoFrequencies.getSemitones(readNote), TREMBLE);
                        }
                    }

                } else {
                    super.handleMessage(msg);
                }
            }
        };
        listenerThread = new Thread(new ListenerThread(FreestyleActivity.this, handler));
        listenerThread.start();
    }


    private void finishThreads() {
        if(listenerThread != null && listenerThread.isAlive()) listenerThread.interrupt();
        try {
            listenerThread.join();
        } catch(InterruptedException e) {
            Log.v("FreestyleActivity", "Interrupted exception 1");
        }
    }

    @Override
    public void onBackPressed() {
        Log.v("BACK", "BACK PRESSED");
        finishThreads();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(listenerThread != null && listenerThread.isAlive()) listenerThread.interrupt();
        try {
            listenerThread.join();
        } catch(InterruptedException e) {
            Log.v("FreestyleActivity", "Interrupted exception 1");
        }
    }
}