package com.example.bartomiej.pianistbooster.game_modes.staff_mode.old;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.bartomiej.pianistbooster.sound_analysis.ListenerThread;
import com.example.bartomiej.pianistbooster.R;

public class StaffModeActivity extends AppCompatActivity {

    Thread listenerThread;
    Thread decreaseTime;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_mode);final StaffManager staffManager = new StaffManager((ImageView) findViewById(R.id.tremble), (ImageView) findViewById(R.id.note));

        mHandler = StaffModeHandler.getStaffModeHandler(StaffModeActivity.this);

        listenerThread = new Thread(new ListenerThread(StaffModeActivity.this, mHandler));
        listenerThread.start();
        decreaseTime = new Thread(new DecreaseTime(mHandler));
        decreaseTime.start();


    }

    @Override
    protected void onStop() {
        super.onStop();
        if(listenerThread != null && listenerThread.isAlive()) listenerThread.interrupt();
        if(decreaseTime != null && decreaseTime.isAlive()) decreaseTime.interrupt();
    }
}
