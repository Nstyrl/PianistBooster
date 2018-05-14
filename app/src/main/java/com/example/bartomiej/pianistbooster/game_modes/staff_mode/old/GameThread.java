package com.example.bartomiej.pianistbooster.game_modes.staff_mode.old;

import android.os.Handler;

public class GameThread implements Runnable {
    private StaffManager manager;
    private final Handler handler;
    public GameThread(StaffManager manager, Handler handler) {
        this.manager = manager;
        this.handler = handler;
    }

    @Override
    public void run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);



    }
}
