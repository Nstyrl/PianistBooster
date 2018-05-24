package com.example.bartomiej.pianistbooster.RoomNoteResults;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "correct")
    public int correctStaffTest;

    @ColumnInfo(name = "all")
    public int allStaffTest;

    public Note(int uid) {
        correctStaffTest = 0;
        allStaffTest = 0;
        this.uid = uid;
    }
}
