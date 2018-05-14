package com.example.bartomiej.pianistbooster.common;

import android.util.Log;

public class PianoFrequencies {
    private static String noteNames[];
    public static String[] getNoteNames() {
        if(noteNames != null && noteNames.length != 0) return noteNames;
        String basic[] = {
                "A0",
                "A♯0/B♭0",
                "B0",
                "C1",
                "C♯1/D♭1",
                "D1",
                "D♯1/E♭1",
                "E1",
                "F1",
                "F♯1/G♭1",
                "G1",
                "G♯1/A♭1",
        };
        noteNames = new String[108];
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 12; j++) {
                noteNames[i * 12 + j] = basic[j];
                for(int k = 8; k >= 0; k--) {
                    noteNames[i * 12 + j] = noteNames[i * 12 + j].replace(Integer.toString(k), Integer.toString(k+i));
                }
            }
        }
        return noteNames;
    }

    public static int getSemitones(int note) {
        if(getTypeOfNote(note) == BLACK_KEY) return 1;
        else return 0;
    }


    public static int getPositionOnClef(int note, int semitone) {
        final int E4 = 43;

        String[] notes = getNoteNames();
        int amount = -1;
        for(int i = Math.min(note, E4); i <= Math.max(note, E4); i++) {
            if(getTypeOfNote(i) == WHITE_KEY) amount++;
        }



        if(note < E4) {
            if(getTypeOfNote(note) == BLACK_KEY && semitone == 1) amount++;
            return -amount;
        } else {
            if(getTypeOfNote(note) == BLACK_KEY && semitone == -1) amount++;
            return +amount;
        }
    }

    final public static int BLACK_KEY = 0;
    final public static int WHITE_KEY = 1;

    public static int getTypeOfNote(int note) {
        String[] notes = getNoteNames();
        if(notes[note].length() != 2) {
            return BLACK_KEY;
        } else {
            return WHITE_KEY;
        }
    }



    public static double[] getFrequencies() {
        int N = 88;
        double frequencies[] = new double[N];
        for(int i = 1; i <= N; i++) {
            frequencies[i - 1] = 440 * Math.pow(2, (i - 49) / 12.0);
        }
        return frequencies;
    }
    public static int frequencyToNote(double frequency) {
        double[] frequencies = getFrequencies();
        int best_index = 0;
        for(int i = 0; i < frequencies.length; i++) {
            if(Math.abs(frequencies[best_index] - frequency) > Math.abs(frequencies[i] - frequency)) {
                best_index = i;
            }
        }
        Log.v("PianoFrequencies", "freq = " + Double.toString(frequency) + ", index = " + Integer.toString(best_index));
        return best_index;
    }
}
