package com.example.bartomiej.pianistbooster.game_modes.staff_mode.old;

import android.os.Handler;
import android.os.Message;

import com.example.bartomiej.pianistbooster.common.Constants;

import static com.example.bartomiej.pianistbooster.common.Constants.thread_ids.DECREASE_TIME_THREAD;

public class DecreaseTime implements Runnable {
    final Handler handler;
    public DecreaseTime(Handler handler){
        this.handler = handler;
    }

    private void sendMessage(Integer s) {
        Message msg = new Message();
        msg.obj = s;
        msg.what = DECREASE_TIME_THREAD;
        handler.sendMessage(msg);
    }

    @Override
    public void run() {

        final int millis = 10;

        while(!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(millis);
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            sendMessage(millis);
        }
    }
}
