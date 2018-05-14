package com.example.bartomiej.pianistbooster.common;

public class BasicNoteValues {

    private int clef;
    private int semitone;
    private int id;

    public int getNoteId() {
        return id;
    }

    public int getNoteClef() {
        return clef;
    }

    public int getNoteSemitone() {
        return semitone;
    }

    public BasicNoteValues(int semitone, int id, int clef) {
        this.id = id;
        this.semitone = semitone;
        this.clef = clef;
    }

}
