package com.example.bartomiej.pianistbooster.RoomNoteResults;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "HOFresults")
public class LearnNotesModeResult {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "result")
    public Double result;


    public LearnNotesModeResult(Double result) {
        this.result = result;
    }
}
