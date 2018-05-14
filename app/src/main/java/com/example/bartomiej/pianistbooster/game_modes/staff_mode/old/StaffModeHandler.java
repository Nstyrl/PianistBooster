package com.example.bartomiej.pianistbooster.game_modes.staff_mode.old;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bartomiej.pianistbooster.common.PianoFrequencies;
import com.example.bartomiej.pianistbooster.R;

import java.util.Random;

import static android.view.View.VISIBLE;
import static com.example.bartomiej.pianistbooster.common.Constants.thread_ids.DECREASE_TIME_THREAD;
import static com.example.bartomiej.pianistbooster.common.Constants.thread_ids.LISTENER_THREAD;

public class StaffModeHandler {
    private static Random generator = new Random();
    private static int getNext() {
        int n = generator.nextInt(12) + 40;
        while(PianoFrequencies.getTypeOfNote(n) != PianoFrequencies.WHITE_KEY) {
            n = generator.nextInt(12) + 40;
        }
        return n;
    }

    public static Handler getStaffModeHandler(final Activity activity) {

        final StaffManager staffManager = new StaffManager((ImageView) activity.findViewById(R.id.tremble), (ImageView) activity.findViewById(R.id.note));

        return new Handler(Looper.getMainLooper()) {

            int noteId;

            private void resetProgress(ProgressBar pb) {
                pb.setProgress(pb.getMax());
            }

            private void putNote(int n) {
                noteId = n;
                ImageView note = (ImageView) activity.findViewById(R.id.note);
                float positions[] = staffManager.spawnOn(n);
                note.setX(positions[0]);
                note.setY(positions[1]);
                if(note.getVisibility() != VISIBLE) {
                    note.setVisibility(VISIBLE);
                }
            }

            @Override
            public void handleMessage(Message msg) {
                if(msg.what == LISTENER_THREAD) {
                    TextView frequency = (TextView) activity.findViewById(R.id.frequency);
                    String s = (String)msg.obj;
                    frequency.setText((String) msg.obj);

                    Double f = Double.valueOf((String) msg.obj);

                    if(f > 0.1) {
                        if(PianoFrequencies.frequencyToNote(f) == noteId) {
                            putNote(getNext());
                            resetProgress((ProgressBar) activity.findViewById(R.id.timeLeft));
                        }
                    }
                } else if(msg.what == DECREASE_TIME_THREAD) {
                    ProgressBar pb = (ProgressBar) activity.findViewById(R.id.timeLeft);

                    int newTime = pb.getProgress() - (int)msg.obj;
                    if(newTime <= 0) {
                        putNote(getNext());
                        resetProgress(pb);
                    } else {
                        pb.setProgress(newTime);
                    }

                } else {
                    super.handleMessage(msg);
                }
            }
        };
    }

}
