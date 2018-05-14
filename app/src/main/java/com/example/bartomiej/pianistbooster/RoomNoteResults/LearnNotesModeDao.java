package com.example.bartomiej.pianistbooster.RoomNoteResults;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface LearnNotesModeDao {
    @Query("SELECT * FROM HOFresults ORDER BY result DESC LIMIT 5")
    public List<LearnNotesModeResult> getAll();

    @Insert
    public void insertAll(LearnNotesModeResult... notes);

    @Delete
    public void delete(LearnNotesModeResult note);

    @Update
    public void updateAll(LearnNotesModeResult... note);
}
