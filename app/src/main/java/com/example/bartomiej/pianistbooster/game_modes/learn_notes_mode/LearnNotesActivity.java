package com.example.bartomiej.pianistbooster.game_modes.learn_notes_mode;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bartomiej.pianistbooster.R;
import com.example.bartomiej.pianistbooster.StaffActivity;
import com.example.bartomiej.pianistbooster.common.PianoFrequencies;
import com.example.bartomiej.pianistbooster.sound_analysis.ListenerThread;

import static android.view.View.VISIBLE;
import static com.example.bartomiej.pianistbooster.common.Constants.clef_ids.BASS;
import static com.example.bartomiej.pianistbooster.common.Constants.clef_ids.TREMBLE;
import static com.example.bartomiej.pianistbooster.common.Constants.thread_ids.DECREASE_TIME_THREAD;
import static com.example.bartomiej.pianistbooster.common.Constants.thread_ids.LISTENER_THREAD;

public class LearnNotesActivity extends StaffActivity {
    private Thread listenerThread;
    private Thread waitingThread;

    private Handler handler;

    private GameState gameState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == LISTENER_THREAD) {
                    considerFrequency(Double.valueOf((String) msg.obj));
                } else if(msg.what == DECREASE_TIME_THREAD) {
                    decreaseTimeBy(10);
                } else {
                    super.handleMessage(msg);
                }
            }
        };

        startGame();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(listenerThread != null && listenerThread.isAlive()) listenerThread.interrupt();
        try {
            listenerThread.join();
        } catch(java.lang.NullPointerException | InterruptedException e) {
            Log.v("LearnNotesActivity", "Interrupted exception 1");
        }
    }


    private void startGame() {
        gameState = new GameState(this);
        prepareLayout();
        listenerThread = new Thread(new ListenerThread(LearnNotesActivity.this, handler));
        listenerThread.start();
        waitingThread = new Thread(new DecreaseTime(handler));
        waitingThread.start();

        showNote(gameState.getNextNote());
    }

    private void theEnd() {
        listenerThread.interrupt();
        waitingThread.interrupt();
        try {
            listenerThread.join();
            waitingThread.join();
        } catch(Exception e) {
            Log.v("LearnNotesActivity", "Seomthing went wrong at the End");
        }

        Intent myIntent = new Intent(LearnNotesActivity.this, FinishActivity.class);
        myIntent.putExtra("score", Double.toString(gameState.getScore()));
        LearnNotesActivity.this.startActivity(myIntent);
        finish();
    }


    private void decreaseTimeBy(int millis) {
        ProgressBar pb = (ProgressBar)findViewById(R.id.timeLeft);
        pb.setProgress(pb.getProgress() - millis);
        if(pb.getProgress() <= 0) {
            gameState.failiure();
            if(gameState.getLifes() == 0) {
                Log.v("LearnNotesActivity", "theEnd()");
                theEnd();
            } else {
                hideAll();
                showNote(gameState.getNextNote());
                ((TextView)findViewById(R.id.lifes_left)).setText("Lifes left: " + Integer.toString(gameState.getLifes()));
                ((TextView)findViewById(R.id.combo)).setText(String.format("Current combo: %1$d", gameState.getCombo()));
                Log.v("LearnNotesActivity", "Setting new time after failiure");
                pb.setMax(gameState.getTime());
                pb.setProgress(gameState.getTime());
            }
        }
        ((TextView)findViewById(R.id.progress)).setText(Integer.toString(pb.getProgress()) + "/" + Integer.toString(pb.getMax()));
    }


    private void considerFrequency(double frequency) {
        TextView f = (TextView) findViewById(R.id.frequency);
        f.setText(String.format("%1$,.2f", frequency));
        ProgressBar pb = (ProgressBar)findViewById(R.id.timeLeft);
        if(frequency > 0.1) {

            if(gameState.getNoteId() == PianoFrequencies.frequencyToNote(frequency)) {
                gameState.success(pb.getProgress());
                ((TextView)findViewById(R.id.level)).setText("Current level: " + Integer.toString(gameState.getLevel() + 1));
                ((TextView)findViewById(R.id.lifes_left)).setText("Lifes left: " + Integer.toString(gameState.getLifes()));
                ((TextView)findViewById(R.id.combo)).setText(String.format("Current combo: %1$d", gameState.getCombo()));
                hideAll();
                showNote(gameState.getNextNote());

                pb.setMax(gameState.getTime());
                pb.setProgress(gameState.getTime());
                ((TextView)findViewById(R.id.progress)).setText(Integer.toString(pb.getProgress()) + "/" + Integer.toString(pb.getMax()));
                ((TextView)findViewById(R.id.score)).setText("Score: " + String.format("%1$,.1f", gameState.getScore()));
            }
        }
    }

    private void prepareLayout() {
        findViewById(R.id.score).setVisibility(VISIBLE);

        findViewById(R.id.level).setVisibility(VISIBLE);

        findViewById(R.id.lifes_left).setVisibility(VISIBLE);

        findViewById(R.id.combo).setVisibility(VISIBLE);

        findViewById(R.id.progress).setVisibility(VISIBLE);

        ProgressBar pb = (ProgressBar)findViewById(R.id.timeLeft);
        pb.setVisibility(VISIBLE);
        pb.setMax(gameState.getTime());
        pb.setProgress(gameState.getTime());
    }




    private void finishThreads() {
        if(listenerThread != null && listenerThread.isAlive()) listenerThread.interrupt();
        try {
            listenerThread.join();
        } catch(InterruptedException e) {
            Log.v("LearnNotesActivity", "Interrupted exception 1");
        }
        if(waitingThread != null && waitingThread.isAlive()) waitingThread.interrupt();
        try {
            waitingThread.join();
        } catch(InterruptedException e) {
            Log.v("LearnNotesActivity", "Interrupted exception 2");
        }
    }

    @Override
    public void onBackPressed() {
        finishThreads();
        finish();
    }

}
