package com.example.bartomiej.pianistbooster.RoomNoteResults;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Note.class, LearnNotesModeResult.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
    public abstract LearnNotesModeDao LNMDao();
}