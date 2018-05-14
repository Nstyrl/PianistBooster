package com.example.bartomiej.pianistbooster.game_modes.staff_mode.old;

import android.widget.ImageView;

import com.example.bartomiej.pianistbooster.common.PianoFrequencies;

import java.util.Random;

public class StaffManager {
    Random generator = new Random();
    int nxt = 0;
    final ImageView staff;
    final ImageView note;

    public StaffManager(ImageView staff, ImageView note) {
        this.staff = staff;
        this.note = note;
    }

    private double getTerce() {
        return staff.getHeight() * (10.5 / 107.0);
    }

    private float getMiddle() {
        return (staff.getLeft() + staff.getRight()) / 2;
    }

    private float getPositionFromBelow(int n) {
        float Y = (float)(staff.getBottom() - note.getHeight() - (staff.getHeight() * 22.5/107.0) - n * getTerce() / 2);
        return Y;
    }

    public float[] spawnOn(int n) {
        final float X = getMiddle();
        final float Y = getPositionFromBelow(PianoFrequencies.getPositionOnClef(n, 0));

        float[] positions = new float[2];
        positions[0] = X;
        positions[1] = Y;
        return positions;
    }

}
