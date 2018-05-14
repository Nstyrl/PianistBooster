package com.example.bartomiej.pianistbooster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.bartomiej.pianistbooster.common.BasicNoteValues;
import com.example.bartomiej.pianistbooster.common.Note;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.example.bartomiej.pianistbooster.common.Constants.clef_ids.BASS;
import static com.example.bartomiej.pianistbooster.common.Constants.clef_ids.TREMBLE;

public abstract class StaffActivity extends AppCompatActivity {

    final Notes bassNotes = new BassNotes();
    final Notes trembleNotes = new TrembleNotes();
    public abstract class Notes{
        public Note normalNotes[] = new Note[88];
        public Note sharpNotes[] = new Note[88];
        public Note flatNotes[]= new Note[88];

        abstract void prepareNotes();
    }
    private class BassNotes extends Notes {
        public void prepareNotes() {
            for(int i = 0; i < 88; i++) {
                normalNotes[i] = new Note(StaffActivity.this, 0, i, BASS);
                sharpNotes[i] = new Note(StaffActivity.this, 1, i, BASS);
                flatNotes[i] = new Note(StaffActivity.this, -1, i, BASS);
            }
        }
    }

    private class TrembleNotes extends Notes {
        public void prepareNotes() {
            for(int i = 0; i < 88; i++) {
                normalNotes[i] = new Note(StaffActivity.this, 0, i, TREMBLE);
                sharpNotes[i] = new Note(StaffActivity.this, 1, i, TREMBLE);
                flatNotes[i] = new Note(StaffActivity.this, -1, i, TREMBLE);
            }
        }
    }

    /**
     *
     * @param note to be displayed
     */
    public void showNote(BasicNoteValues note) {

        int clef = note.getNoteClef();
        int semitones = note.getNoteSemitone();
        int Y = note.getNoteId();

        Notes notes;
        if(clef == TREMBLE) {
            notes = trembleNotes;
        } else {
            notes = bassNotes;
        }
        if(semitones == -1) {
            notes.flatNotes[Y].setVisibility(VISIBLE);
        } else if(semitones == 0) {
            notes.normalNotes[Y].setVisibility(VISIBLE);
        } else if(semitones == 1) {
            notes.sharpNotes[Y].setVisibility(VISIBLE);
        }
    }

    /**
     *
     * @param Y is id of note (no. from lowest to highest on piano keyboard).
     * @param semitones is semitone of the note (-1 is flat, 0 is normal, 1 is sharp).
     * @param clef is BASS or TREMBLE - place where we want to display the note.
     */

    public void showNote(int Y, int semitones, int clef) {
        Notes notes;
        if(clef == TREMBLE) {
            notes = trembleNotes;
        } else {
            notes = bassNotes;
        }
        if(semitones == -1) {
            notes.flatNotes[Y].setVisibility(VISIBLE);
        } else if(semitones == 0) {
            notes.normalNotes[Y].setVisibility(VISIBLE);
        } else if(semitones == 1) {
            notes.sharpNotes[Y].setVisibility(VISIBLE);
        }
    }

    public void hideAll() {
        Notes notes = trembleNotes;
        for(int j = 0; j < 2; j++) {
            for (int i = 0; i < 88; i++) {
                notes.flatNotes[i].setVisibility(INVISIBLE);
                notes.normalNotes[i].setVisibility(INVISIBLE);
                notes.sharpNotes[i].setVisibility(INVISIBLE);
            }
            notes = bassNotes;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        bassNotes.prepareNotes();
        trembleNotes.prepareNotes();
    }
}
