package com.example.bartomiej.pianistbooster.game_modes.learn_notes_mode;

import android.app.Activity;
import android.util.Log;

import com.example.bartomiej.pianistbooster.common.BasicNoteValues;
import com.example.bartomiej.pianistbooster.common.Note;
import com.example.bartomiej.pianistbooster.common.PianoFrequencies;

import java.util.Random;

import static com.example.bartomiej.pianistbooster.common.Constants.clef_ids.BASS;
import static com.example.bartomiej.pianistbooster.common.Constants.clef_ids.TREMBLE;

public class GameState {
    Random generator = new Random();

    private int noOfLevels = 30;

    private class Level {
        public int scoreToLevel;
        public int scoreOnLevel;

        public int timeOnLevel;
        public int rangeOnLevelTrembleLo;
        public int rangeOnLevelTrembleHi;

        public int rangeOnLevelBassLo;
        public int rangeOnLevelBassHi;

        public boolean semitonesOnLevel;

        public Level(
                int scoreToLevel,
                int scoreOnLevel,
                int timeOnLevel,
                int rangeOnLevelTrembleLo,
                int rangeOnLevelTrembleHi,
                int rangeOnLevelBassLo,
                int rangeOnLevelBassHi,
                boolean semitonesOnLevel
        ) {
            this.scoreToLevel = scoreToLevel;
            this.scoreOnLevel = scoreOnLevel;
            this.timeOnLevel = timeOnLevel;
            this.rangeOnLevelTrembleLo = rangeOnLevelTrembleLo;
            this.rangeOnLevelTrembleHi = rangeOnLevelTrembleHi;

            this.rangeOnLevelBassLo = rangeOnLevelBassLo;
            this.rangeOnLevelBassHi = rangeOnLevelBassHi;

            this.semitonesOnLevel = semitonesOnLevel;
        }
    }

    private Level levels[];

    private void prepareLevels() {
        levels = new Level[noOfLevels];
        int index = 0;
        levels[0] = new Level(
                0,
                1,
                5,
                39,
                51,
                27,
                39,
                false
        );
        levels[1] = new Level(
                15,
                2,
                4,
                39,
                51,
                27,
                39,
                false
        );
        levels[2] = new Level(
                50,
                3,
                3,
                39,
                51,
                27,
                39,
                false
        );
        levels[3] = new Level(
                100,
                5,
                2,
                39,
                51,
                27,
                39,
                false
        );


        levels[4] = new Level(
                200,
                6,
                5,
                39,
                51,
                27,
                39,
                true
        );
        levels[5] = new Level(
                300,
                7,
                4,
                39,
                51,
                27,
                39,
                true
        );
        levels[6] = new Level(
                450,
                8,
                3,
                39,
                51,
                27,
                39,
                true
        );
        levels[7] = new Level(
                700,
                10,
                2,
                39,
                51,
                27,
                39,
                true
        );


        levels[8] = new Level(
                1000,
                11,
                5,
                39,
                63,
                15,
                39,
                false
        );
        levels[9] = new Level(
                1200,
                12,
                4,
                39,
                63,
                15,
                39,
                false
        );
        levels[10] = new Level(
                1450,
                13,
                3,
                39,
                63,
                15,
                39,
                false
        );
        levels[11] = new Level(
                1700,
                15,
                2,
                39,
                63,
                15,
                39,
                false
        );


        levels[12] = new Level(
                2200,
                16,
                5,
                39,
                63,
                15,
                39,
                true
        );
        levels[13] = new Level(
                2500,
                17,
                4,
                39,
                63,
                15,
                39,
                true
        );
        levels[14] = new Level(
                2900,
                18,
                3,
                39,
                63,
                15,
                39,
                true
        );
        levels[15] = new Level(
                3400,
                20,
                2,
                39,
                63,
                15,
                39,
                true
        );


        levels[16] = new Level(
                3700,
                21,
                5,
                32,
                63,
                15,
                46,
                false
        );
        levels[17] = new Level(
                4000,
                22,
                4,
                32,
                63,
                15,
                46,
                false
        );
        levels[18] = new Level(
                4400,
                23,
                3,
                32,
                63,
                15,
                46,
                false
        );
        levels[19] = new Level(
                5000,
                25,
                2,
                32,
                63,
                15,
                46,
                false
        );

        levels[20] = new Level(
                6000,
                26,
                5,
                32,
                63,
                15,
                46,
                true
        );
        levels[21] = new Level(
                7000,
                27,
                4,
                32,
                63,
                15,
                46,
                true
        );
        levels[22] = new Level(
                8500,
                28,
                3,
                32,
                63,
                15,
                46,
                true
        );
        levels[23] = new Level(
                10000,
                29,
                2,
                32,
                63,
                15,
                46,
                true
        );


        levels[24] = new Level(
                50000,
                100,
                1,
                39,
                51,
                27,
                39,
                true
        );
        levels[25] = new Level(
                100000,
                200,
                1,
                39,
                51,
                27,
                39,
                true
        );

        levels[26] = new Level(
                200000,
                500,
                1,
                39,
                63,
                15,
                39,
                false
        );
        levels[27] = new Level(
                300000,
                1000,
                1,
                39,
                63,
                15,
                39,
                true
        );
        levels[28] = new Level(
                500000,
                3000,
                1,
                32,
                63,
                15,
                46,
                false
        );
        levels[29] = new Level(
                1000000,
                10000,
                1,
                32,
                63,
                15,
                46,
                true
        );

        /*
        levels[index++] = new Level(
                X,
                X,
                X,
                X,
                X,
                X,
                X,
                X
        );
        */
    }

    private int level;
    private double score;

    private int combo;

    private int lifesLeft;

    private BasicNoteValues currentNote;

    private Activity activity;

    public GameState(Activity activity) {
        prepareLevels();
        score = 0;
        level = 0;
        lifesLeft = 3;
        combo = 0;
    }

    public int getCombo() {
        return combo;
    }

    public int getNoteId() {
        return currentNote.getNoteId();
    }

    public int getLifes() {
        return lifesLeft;
    }

    public double getScore() {
        return score;
    }

    /**
     *
     * @param timeLeftInMillis is the time left at which player played right note.
     */
    public void success(int timeLeftInMillis) {

        double scoreGained = levels[level].scoreOnLevel * (1 + combo * 0.1);
        if(timeLeftInMillis > levels[level].timeOnLevel * 1000 / 2) scoreGained *= 2;
        score += scoreGained;

        combo++;
        if(combo % 5 == 0) {
            lifesLeft += 1;
        }

        if(level < noOfLevels - 1 && score >= levels[level + 1].scoreToLevel) level++;
    }

    /***
     *
     * @return true if game continues
     */
    public boolean failiure() {
        lifesLeft -= 1;
        combo = 0;
        return lifesLeft > 0;
    }

    /**
     *
     * @return current level in the game
     */
    public int getLevel() {
        return level;
    }

    /**
     *
     * @return time to be set on the current level
     */
    public int getTime() {
        return levels[level].timeOnLevel * 1000;
    }

    /**
     *
     * @return next note to be displayed in the game
     */
    public BasicNoteValues getNextNote() {
        int clef = generator.nextInt(2);
        int noteId;
        do {
            if (clef == TREMBLE) { // tremble
                noteId = generator.nextInt(levels[level].rangeOnLevelTrembleHi - levels[level].rangeOnLevelTrembleLo + 1) + levels[level].rangeOnLevelTrembleLo;
            } else if (clef == BASS) {
                noteId = generator.nextInt(levels[level].rangeOnLevelBassHi - levels[level].rangeOnLevelBassLo + 1) + levels[level].rangeOnLevelBassLo;
            } else {
                Log.v("getNextNote", "Error: generated clef is neither TREMBLE nor BASS");
                throw new IndexOutOfBoundsException("Error: generated clef is neither TREMBLE nor BASS");
            }
        } while(levels[level].semitonesOnLevel == false && PianoFrequencies.getTypeOfNote(noteId) == PianoFrequencies.BLACK_KEY);

        int typeOfSemitone = 0;
        if(PianoFrequencies.getTypeOfNote(noteId) == PianoFrequencies.BLACK_KEY) {
            typeOfSemitone = generator.nextInt(2);
            if(typeOfSemitone == 0) {
                typeOfSemitone = 1;
            } else {
                typeOfSemitone = -1;
            }
        }

        currentNote = new BasicNoteValues(typeOfSemitone, noteId, clef);

        return currentNote;
    }


}
